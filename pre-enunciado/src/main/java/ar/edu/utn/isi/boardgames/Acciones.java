package ar.edu.utn.isi.boardgames;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ar.edu.utn.isi.boardgames.entities.BoardGame;
import ar.edu.utn.isi.boardgames.repositories.BoardGameRepository;
import ar.edu.utn.isi.boardgames.repositories.CategoryRepository;
import ar.edu.utn.isi.boardgames.repositories.DesignerRepository;
import ar.edu.utn.isi.boardgames.repositories.PublisherRepository;
import ar.edu.utn.isi.boardgames.services.BoardGameService;

/**
 * Clase de acciones invocables desde App.java
 *
 * Requisitos de ApplicationContext esperado:
 *  - context.get("path") -> URL de una carpeta con CSVs
 *  - context.getService(Clase.class) -> instancia del service solicitado
 *
 * CSV esperado por BoardGameService.bulkInsert():
 *   Encabezados en este orden:
 *   name,yearPublished,minPlayers,maxPlayers,minAge,averageRating,usersRating,category,publisher,designer
 */
public class Acciones {

    /**
     * Recorre la carpeta indicada en context.get("path") (URL) y busca el primer .csv.
     * Si hay varios, intenta priorizar uno que contenga "board" y "game" en el nombre;
     * si no, toma el primero que encuentre.
     */
    public void importarCsv(AppContext context) {
        URL pathToImport = (URL) context.get("path");

        try (var paths = Files.walk(Paths.get(pathToImport.toURI()))) {
            var csvFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().toLowerCase().endsWith(".csv"))
                    .map(p -> p.toFile())
                    .toList();

            var maybeCsv = csvFiles.stream()
                    .filter(f -> {
                        String n = f.getName().toLowerCase();
                        return n.contains("board") && n.contains("game");
                    })
                    .findFirst()
                    .or(() -> csvFiles.stream().findFirst()); // si no hay “boardgame”, toma el primero

            maybeCsv.ifPresentOrElse(f -> {
                var service = context.getService(BoardGameService.class);
                try {
                    service.bulkInsert(f);
                    System.out.println("[OK] Importación CSV: " + f.getName());
                } catch (IOException e) {
                    System.err.println("[ERROR] Al leer CSV: " + e.getMessage());
                    e.printStackTrace();
                }
            }, () -> {
                throw new IllegalArgumentException("No se encontró ningún archivo .csv en " + pathToImport);
            });

        } catch (IOException | URISyntaxException e) {
            System.err.println("[ERROR] Al recorrer carpeta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Muestra por consola un resumen básico:
     * - Cantidad de juegos, categorías, publishers y designers
     * - Una muestra de juegos ordenada por rating (desc)
     */
    public void mostrarResumenBasico(AppContext context) {
        var gameRepo = new BoardGameRepository();
        var catRepo  = new CategoryRepository();
        var pubRepo  = new PublisherRepository();
        var desRepo  = new DesignerRepository();

        int totalGames = gameRepo.getAllList().size();
        int totalCats  = catRepo.getAllList().size();
        int totalPubs  = pubRepo.getAllList().size();
        int totalDes   = desRepo.getAllList().size();

        System.out.println("========== RESUMEN ==========");
        System.out.println("Juegos:      " + totalGames);
        System.out.println("Categorías:  " + totalCats);
        System.out.println("Publishers:  " + totalPubs);
        System.out.println("Designers:   " + totalDes);

        // // Muestra ordenada por rating desc (nulls al final)
        // List<BoardGame> sample = gameRepo.getAllStream()
        //         .sorted(Comparator.comparing(BoardGame::getAverageRating,
        //                 Comparator.nullsLast(Comparator.naturalOrder())).reversed())
        //         .limit(10)
        //         .toList();

        // System.out.println("----- Top (hasta 10) por rating -----");
        // sample.forEach(g -> System.out.println(
        //         "- " + g.getName()
        //         + " (" + g.getYearPublished() + ")"
        //         + " | Rating: " + g.getAverageRating()
        //         + " | Jugadores: " + g.getMinPlayers()
        //         + "–" + (g.getMaxPlayers() == null ? "∞" : g.getMaxPlayers())
        // ));
    }

    /**
     * Muestra las 5 categorías con menor promedio de rating,
     * considerando únicamente juegos con más de 500 usuarios calificados.
     * Agrupa por categoría, calcula promedio de averageRating y total de usersRated.
     * Ordena por promedio ascendente (menor a mayor rating).
     */
    public void mostrarCategoriasConMenorRating(AppContext context) {
        var gameRepo = new BoardGameRepository();
        
        // Obtener todos los juegos
        List<BoardGame> allGames = gameRepo.getAllList();
        
        // Filtrar juegos con más de 500 usuarios calificados y que tengan categoría
        var juegosFiltrados = allGames.stream()
                .filter(game -> game.getUsersRating() != null && game.getUsersRating() > 500)
                .filter(game -> game.getCategory() != null)
                .filter(game -> game.getAverageRating() != null)
                .toList();
        
        System.out.println("Total de juegos con más de 500 usuarios: " + juegosFiltrados.size());
        
        // Agrupar por categoría y calcular estadísticas
        Map<String, Map<String, Double>> estadisticasPorCategoria = juegosFiltrados.stream()
                .collect(Collectors.groupingBy(
                    game -> game.getCategory().getName(),
                    Collectors.teeing(
                        Collectors.averagingDouble(BoardGame::getAverageRating),
                        Collectors.summingDouble(game -> game.getUsersRating().doubleValue()),
                        (promedio, totalUsuarios) -> Map.of(
                            "promedio", promedio,
                            "totalUsuarios", totalUsuarios
                        )
                    )
                ));
        
        // Ordenar por promedio ascendente y tomar las primeras 5
        var top5Categorias = estadisticasPorCategoria.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getValue().get("promedio")))
                .limit(5)
                .toList();
        
        System.out.println("\n========== TOP 5 CATEGORÍAS CON MENOR RATING ==========");
        System.out.println("(Considerando solo juegos con más de 500 usuarios calificados)");
        System.out.println();
        System.out.printf("%-25s %-12s %-15s%n", "CATEGORÍA", "PROMEDIO", "TOTAL USUARIOS");
        System.out.println("-".repeat(55));
        
        for (var entry : top5Categorias) {
            String categoria = entry.getKey();
            double promedio = entry.getValue().get("promedio");
            double totalUsuarios = entry.getValue().get("totalUsuarios");
            
            System.out.printf("%-25s %-12.2f %-15.0f%n", 
                categoria, 
                promedio, 
                totalUsuarios
            );
        }
        
        System.out.println("\n[INFO] Se analizaron " + estadisticasPorCategoria.size() + 
                         " categorías en total");
    }

    /**
     * Determina si un juego es apto para una cantidad específica de jugadores y edad dada.
     * Evalúa si el número de jugadores está en el rango [minPlayers, maxPlayers],
     * si la edad es >= minAge y si tiene al menos 100 usuarios de rating.
     * Lista los juegos que cumplan los criterios ordenados por rating descendente.
     * 
     * @param numJugadores Número de jugadores
     * @param edadJugador Edad del jugador
     */
    public void mostrarJuegosAptos(AppContext context, Integer numJugadores, Integer edadJugador) {
        var gameRepo = new BoardGameRepository();
        
        // Obtener todos los juegos
        List<BoardGame> allGames = gameRepo.getAllList();
        
        // Filtrar juegos que cumplan todos los criterios
        var juegosAptos = allGames.stream()
                .filter(game -> esJuegoApto(game, numJugadores, edadJugador))
                .sorted(Comparator.comparing(BoardGame::getAverageRating, 
                        Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .toList();
        
        System.out.println("\n========== JUEGOS APTOS PARA " + numJugadores + " JUGADORES Y EDAD " + edadJugador + "+ ==========");
        System.out.println("(Considerando solo juegos con al menos 100 usuarios de rating)");
        System.out.println();
        
        if (juegosAptos.isEmpty()) {
            System.out.println("❌ No se encontraron juegos que cumplan todos los criterios.");
            return;
        }
        
        System.out.printf("%-35s %-12s %-12s %-8s %-8s %-8s%n", 
            "NOMBRE", "RATING", "USUARIOS", "MIN_EDAD", "MIN_JUG", "MAX_JUG");
        System.out.println("-".repeat(85));
        
        for (BoardGame game : juegosAptos) {
            System.out.printf("%-35s %-12.2f %-12d %-8d %-8d %-8s%n",
                truncateText(game.getName(), 34),
                game.getAverageRating() != null ? game.getAverageRating() : 0.0,
                game.getUsersRating() != null ? game.getUsersRating() : 0,
                game.getMinAge() != null ? game.getMinAge() : 0,
                game.getMinPlayers(),
                game.getMaxPlayers() != null ? game.getMaxPlayers().toString() : "∞"
            );
        }
        
        System.out.println("\n✅ Se encontraron " + juegosAptos.size() + " juegos aptos");
        
        // Mostrar estadísticas adicionales
        long totalJuegosConDatos = allGames.stream()
                .filter(game -> game.getMinAge() != null && 
                               game.getUsersRating() != null)
                .count();
        
        System.out.println("[INFO] Se analizaron " + totalJuegosConDatos + " juegos con datos completos de " + allGames.size() + " juegos totales");
    }
    
    /**
     * Evalúa si un juego es apto para los criterios dados.
     * 
     * @param game El juego a evaluar
     * @param numJugadores Número de jugadores
     * @param edadJugador Edad del jugador
     * @return true si el juego cumple todos los criterios
     */
    private boolean esJuegoApto(BoardGame game, Integer numJugadores, Integer edadJugador) {
        // Verificar que el juego tenga los datos necesarios
        // minPlayers es primitivo (int), nunca puede ser null
        if (game.getMinAge() == null || 
            game.getUsersRating() == null || game.getAverageRating() == null) {
            return false;
        }
        
        // Criterio 1: Al menos 100 usuarios de rating
        if (game.getUsersRating() < 100) {
            return false;
        }
        
        // Criterio 2: Edad del jugador >= minAge del juego
        if (edadJugador < game.getMinAge()) {
            return false;
        }
        
        // Criterio 3: Número de jugadores en el rango [minPlayers, maxPlayers]
        if (numJugadores < game.getMinPlayers()) {
            return false;
        }
        
        // Si maxPlayers es null, asumimos que no hay límite superior
        if (game.getMaxPlayers() != null && numJugadores > game.getMaxPlayers()) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Trunca un texto a la longitud especificada agregando "..." si es necesario.
     */
    private String truncateText(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}
