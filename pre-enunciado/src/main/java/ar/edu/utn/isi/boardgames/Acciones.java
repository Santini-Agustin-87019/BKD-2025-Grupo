package ar.edu.utn.isi.boardgames;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

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

        // Muestra ordenada por rating desc (nulls al final)
        List<BoardGame> sample = gameRepo.getAllStream()
                .sorted(Comparator.comparing(BoardGame::getAverageRating,
                        Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .limit(10)
                .toList();

        System.out.println("----- Top (hasta 10) por rating -----");
        sample.forEach(g -> System.out.println(
                "- " + g.getName()
                + " (" + g.getYearPublished() + ")"
                + " | Rating: " + g.getAverageRating()
                + " | Jugadores: " + g.getMinPlayers()
                + "–" + (g.getMaxPlayers() == null ? "∞" : g.getMaxPlayers())
        ));
    }
}
