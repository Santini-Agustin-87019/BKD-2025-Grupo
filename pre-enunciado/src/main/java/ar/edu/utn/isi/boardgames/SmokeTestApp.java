package ar.edu.utn.isi.boardgames;

import ar.edu.utn.isi.boardgames.infrastructure.DataSourceProvider;
import ar.edu.utn.isi.boardgames.infrastructure.DbInitializer;
import ar.edu.utn.isi.boardgames.repositories.BoardGameRepository;
import ar.edu.utn.isi.boardgames.repositories.CategoryRepository;
import ar.edu.utn.isi.boardgames.repositories.DesignerRepository;
import ar.edu.utn.isi.boardgames.repositories.PublisherRepository;
import ar.edu.utn.isi.boardgames.services.BoardGameService;
import ar.edu.utn.isi.boardgames.services.CategoryService;
import ar.edu.utn.isi.boardgames.services.PublisherService;
import ar.edu.utn.isi.boardgames.services.DesignerService;
import ar.edu.utn.isi.boardgames.entities.BoardGame;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class SmokeTestApp {
    public static void main(String[] args) {
        try {
            // 1) MISMA BD para todo
            DataSource ds = DataSourceProvider.get();

            // 2) Crear esquema desde DDL
            DbInitializer.run(ds, "src/main/resources/sql/ddl_board_games.sql");
            System.out.println("[OK] DDL ejecutado");

            // 3) Instanciar services
            CategoryService categoryService = new CategoryService();
            PublisherService publisherService = new PublisherService();
            DesignerService designerService = new DesignerService();
            BoardGameService gameService = new BoardGameService();

            // 4) Semilla mínima “a mano”
            var cat = categoryService.getOrCreate("Estrategia");
            var pub = publisherService.getOrCreate("Kosmos");
            var des = designerService.getOrCreate("Klaus Teuber");

            gameService.create(
                    "Catan", 1995, 3, 4, 10, 8.70, 5000,
                    cat.getName(), pub.getName(), des.getName()
            );
            System.out.println("[OK] Juego base creado");

            // 5) CSV temporal + bulk insert
            File tmpCsv = Files.createTempFile("boardgames-", ".csv").toFile();
            String csv = String.join("\n",
                    "name,yearPublished,minPlayers,maxPlayers,minAge,averageRating,usersRating,category,publisher,designer",
                    "Tigris & Euphrates,1997,2,4,12,8.75,3500,Estrategia,Fantasy Flight,Reiner Knizia",
                    "Azul,2017,2,4,8,8.00,4200,Abstracto,Plan B Games,Michael Kiesling"
            );
            Files.writeString(tmpCsv.toPath(), csv);
            gameService.bulkInsert(tmpCsv);
            System.out.println("[OK] Bulk insert desde CSV (" + tmpCsv.getAbsolutePath() + ")");

            // 6) Consultas simples (repos)
            var catRepo = new CategoryRepository();
            var pubRepo = new PublisherRepository();
            var desRepo = new DesignerRepository();
            var gameRepo = new BoardGameRepository();

            int totalGames = gameRepo.getAllList().size();
            int totalCats  = catRepo.getAllList().size();
            int totalPubs  = pubRepo.getAllList().size();
            int totalDes   = desRepo.getAllList().size();

            System.out.println("========== RESUMEN ==========");
            System.out.println("Juegos:      " + totalGames);
            System.out.println("Categorías:  " + totalCats);
            System.out.println("Publishers:  " + totalPubs);
            System.out.println("Designers:   " + totalDes);

            // 7) Muestra de datos
            List<BoardGame> sample = gameRepo.getAllList();
            System.out.println("----- Muestra de juegos -----");
            sample.forEach(g -> System.out.println(
                    "- " + g.getName()
                    + " (" + g.getYearPublished() + ")"
                    + " | Min:" + g.getMinPlayers()
                    + " Max:" + (g.getMaxPlayers() == null ? "∞" : g.getMaxPlayers())
                    + " | Edad:" + (g.getMinAge() == null ? "-" : g.getMinAge())
                    + " | Rating:" + g.getAverageRating()
            ));
            System.out.println("[OK] Smoke test completado");

            // 8) Pruebas de métodos extra (sugeridos)
            System.out.println("----- Pruebas de métodos extra -----");
            BoardGame catan = gameRepo.getAllList().get(0);
            System.out.println("supportsPlayerCount(3): " + catan.supportsPlayerCount(3));
            System.out.println("supportsPlayerCount(6): " + catan.supportsPlayerCount(6));
            System.out.println("isSuitableForAges(new int[]{8,12,15}): " + catan.isSuitableForAges(new int[]{8,12,15}));
            System.out.println("isSuitableForAges(new int[]{6,9}): " + catan.isSuitableForAges(new int[]{6,9}));
            System.out.println("[OK] Pruebas de métodos extra completadas");

        } catch (Exception e) {
            System.err.println("[ERROR] Smoke test falló: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
