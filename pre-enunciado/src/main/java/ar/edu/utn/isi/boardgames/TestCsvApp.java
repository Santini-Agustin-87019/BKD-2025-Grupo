package ar.edu.utn.isi.boardgames;

import ar.edu.utn.isi.boardgames.infrastructure.DataSourceProvider;
import ar.edu.utn.isi.boardgames.infrastructure.DbInitializer;
import ar.edu.utn.isi.boardgames.services.BoardGameService;
import ar.edu.utn.isi.boardgames.repositories.BoardGameRepository;

import javax.sql.DataSource;
import java.io.File;

public class TestCsvApp {
    public static void main(String[] args) {
        try {
            // 1) Inicializar BD
            DataSource ds = DataSourceProvider.get();
            DbInitializer.run(ds, "src/main/resources/sql/ddl_board_games.sql");
            System.out.println("[OK] DDL ejecutado");

            // 2) Crear service
            BoardGameService gameService = new BoardGameService();
            
            // 3) Cargar CSV formateado
            File csvFile = new File("src/main/resources/data/board_games.csv");
            System.out.println("Cargando CSV: " + csvFile.getAbsolutePath());
            
            gameService.bulkInsert(csvFile);
            System.out.println("[OK] CSV cargado");

            // 4) Verificar resultados
            var gameRepo = new BoardGameRepository();
            int totalGames = gameRepo.getAllList().size();
            System.out.println("Total de juegos cargados: " + totalGames);

            // Total de juegos NO CARGADOS
            int expect = 29 - totalGames; 
            System.out.println("Total de juegos NO cargados (errores): " + expect);
            // 5) Mostrar algunos ejemplos
            System.out.println("\n----- Primeros 5 juegos -----");
            gameRepo.getAllList().stream()
                .limit(5)
                .forEach(game -> 
                    System.out.println("- " + game.getName() + 
                        " (" + game.getYearPublished() + ")" +
                        " | Players: " + game.getMinPlayers() + 
                        "-" + (game.getMaxPlayers() == null ? "∞" : game.getMaxPlayers()) +
                        " | Age: " + game.getMinAge() +
                        " | Rating: " + game.getAverageRating())
                );

            System.out.println("\n[OK] Test completado exitosamente");

        } catch (Exception e) {
            System.err.println("[ERROR] Test falló: " + e.getMessage());
            e.printStackTrace();
        }
    }
}