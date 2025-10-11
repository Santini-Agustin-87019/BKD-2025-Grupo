package ar.edu.utn.isi.boardgames.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import ar.edu.utn.isi.boardgames.entities.BoardGame;
import ar.edu.utn.isi.boardgames.entities.Category;
import ar.edu.utn.isi.boardgames.entities.Publisher;
import ar.edu.utn.isi.boardgames.entities.Designer;
import ar.edu.utn.isi.boardgames.repositories.BoardGameRepository;
import ar.edu.utn.isi.boardgames.services.util.NameNormalizer;

public class BoardGameService {

    private final BoardGameRepository gameRepo = new BoardGameRepository();
    private final CategoryService categoryService = new CategoryService();
    private final PublisherService publisherService = new PublisherService();
    private final DesignerService designerService = new DesignerService();

    // ======================== CRUD BÁSICOS ========================
    
    public void create(BoardGame game) { 
        gameRepo.create(game); 
    }
    
    public void update(BoardGame game) { 
        gameRepo.update(game); 
    }
    
    public BoardGame delete(Integer id) { 
        return gameRepo.delete(id); 
    }
    
    public List<BoardGame> getAll() { 
        return gameRepo.getAllList(); 
    }
    
    public BoardGame getById(Integer id) { 
        return gameRepo.getById(id); 
    }

    // ======================== MÉTODO DE ALTO NIVEL ========================
    
    /**
     * Helper de alto nivel para crear un juego a partir de datos simples
     */
    public BoardGame create(String name,
                            Integer yearPublished,
                            int minPlayers,
                            Integer maxPlayers,
                            Integer minAge,
                            Double averageRating,
                            Integer usersRating,
                            String categoryName,
                            String publisherName,
                            String designerName) {

        // Normalizamos name (para coherencia visual)
        String normalizedName = NameNormalizer.normalize(name);

        Category category = categoryService.getOrCreate(categoryName);
        Publisher publisher = publisherService.getOrCreate(publisherName);
        Designer designer = designerService.getOrCreate(designerName);

        BoardGame game = new BoardGame(
                normalizedName,         // NAME
                yearPublished,          // YEAR_PUBLISHED
                minPlayers,             // MIN_PLAYERS (obligatorio)
                maxPlayers,             // MAX_PLAYERS (NULL = sin tope)
                minAge,                 // MIN_AGE (puede ser NULL)
                averageRating,          // AVERAGE_RATING (Double)
                usersRating,            // USERS_RATING (puede ser NULL)
                category, publisher, designer
        );

        gameRepo.create(game);
        return game;
    }

    // ======================== BULK INSERT DESDE CSV ========================
    
    /**
     * Bulk insert desde CSV (estilo tu EmpleadoService).
     * Se asume CSV limpio y con encabezado. Orden sugerido de columnas:
     * name,yearPublished,minPlayers,maxPlayers,minAge,averageRating,usersRating,category,publisher,designer
     */
    public void bulkInsert(File csvFile) throws IOException {
        
        final int EXPECTED_COLS = 10;
        final String HEADER_PREFIX = "name;year_published;min_players;max_players;min_age;average_rating;users_rating;category;publisher;designer";

        final var lines = Files.readAllLines(csvFile.toPath());

        if (lines.isEmpty()) {
            System.err.println("[WARN] CSV vacío: " + csvFile.getAbsolutePath());
            return;
        }

        int lineNo = 0;
        for (String raw : lines) {
            lineNo++;

            // saltear encabezado si está
            if (lineNo == 1 && raw.toLowerCase().startsWith(HEADER_PREFIX)) {
                continue;
            }

            // split conservando vacíos (usando punto y coma como separador)
            String[] c = raw.split(";", -1);
            if (c.length != EXPECTED_COLS) {
                System.err.println("[ERROR] Línea " + lineNo + ": columnas esperadas=" + EXPECTED_COLS + ", recibidas=" + c.length);
                continue; // o throw si preferís fail-fast
            }

            // Validar fila (completo)
            String error = validateRow(c);
            if (error != null) {
                System.err.println("[ERROR] Línea " + lineNo + ": " + error + " | raw=" + raw);
                continue; // skip fila inválida
            }

            // Parseo (ya validado)
            String name = c[0].trim();
            Integer year = parseIntOrNull(c[1]);
            int minPlayers = Integer.parseInt(c[2].trim());
            Integer maxPlayers = parseIntOrNull(c[3]);
            Integer minAge = parseIntOrNull(c[4]);
            Double avg = parseDoubleOrNull(c[5]);
            Integer users = parseIntOrNull(c[6]);
            String category = c[7].trim();
            String publisher = c[8].trim();
            String designer = c[9].trim();

            // Crear
            this.create(name, year, minPlayers, maxPlayers, minAge, avg, users,
                        category, publisher, designer);
        }
        
        System.out.println("[OK] Importación finalizada: " + (lineNo - 1) + " líneas procesadas (con descartes si hubo errores).");
    }

    // ======================== MÉTODOS DE VALIDACIÓN ========================
    
    /**
     * Valida una fila del CSV antes de procesarla
     */
    private String validateRow(String[] c) {

        // 0 name
        if (isBlank(c[0])) return "NAME es obligatorio";

        // 1 yearPublished
        if (!isBlank(c[1])) {
            Integer y = tryParseInt(c[1]);
            if (y == null) return "YEAR_PUBLISHED debe ser entero";
            if (y < 1800 || y > 2100) return "YEAR_PUBLISHED fuera de rango [1800..2100]";
        }

        // 2 minPlayers (obligatorio)
        Integer minP = tryParseInt(c[2]);
        if (minP == null) return "MIN_PLAYERS es obligatorio (entero)";
        if (minP < 1) return "MIN_PLAYERS debe ser >= 1";

        // 3 maxPlayers (opcional)
        if (!isBlank(c[3])) {
            Integer maxP = tryParseInt(c[3]);
            if (maxP == null) return "MAX_PLAYERS debe ser entero o vacío";
            if (maxP < minP) return "MAX_PLAYERS no puede ser menor que MIN_PLAYERS";
        }

        // 4 minAge (opcional)
        if (!isBlank(c[4])) {
            Integer ma = tryParseInt(c[4]);
            if (ma == null) return "MIN_AGE debe ser entero o vacío";
            if (ma < 0) return "MIN_AGE debe ser >= 0";
        }

        // 5 averageRating (opcional)
        if (!isBlank(c[5])) {
            Double ar = tryParseDouble(c[5]);
            if (ar == null) return "AVERAGE_RATING debe ser número o vacío";
            if (ar < 0.0 || ar > 9.99) return "AVERAGE_RATING fuera de rango [0..9.99] (DDL DECIMAL(3,2))";
        }

        // 6 usersRating (opcional)
        if (!isBlank(c[6])) {
            Integer ur = tryParseInt(c[6]);
            if (ur == null) return "USERS_RATING debe ser entero o vacío";
            if (ur < 0) return "USERS_RATING debe ser >= 0";
        }

        // 7 category, 8 publisher, 9 designer (obligatorios)
        if (isBlank(c[7])) return "CATEGORY es obligatoria";
        if (isBlank(c[8])) return "PUBLISHER es obligatorio";
        if (isBlank(c[9])) return "DESIGNER es obligatorio";

        return null; // OK
    }

    
    // ======================== MÉTODOS HELPER ========================
    
    /**
     * Verifica si una cadena está vacía o solo contiene espacios
     */
    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
    
    /**
     * Intenta parsear un string a Integer, retorna null si falla
     */
    private static Integer tryParseInt(String s) {
        try { 
            return Integer.valueOf(s.trim()); 
        } catch (Exception e) { 
            return null; 
        }
    }
    
    /**
     * Intenta parsear un string a Double, retorna null si falla
     */
    private static Double tryParseDouble(String s) {
        try { 
            return Double.valueOf(s.trim()); 
        } catch (Exception e) { 
            return null; 
        }
    }

    /**
     * Parsea un string a Integer, retorna null si está vacío
     */
    private static Integer parseIntOrNull(String s) {
        if (isBlank(s)) return null;
        return Integer.valueOf(s.trim());
    }

    /**
     * Parsea un string a Double, retorna null si está vacío
     */
    private static Double parseDoubleOrNull(String s) {
        if (isBlank(s)) return null;
        return Double.valueOf(s.trim());
    }
}
