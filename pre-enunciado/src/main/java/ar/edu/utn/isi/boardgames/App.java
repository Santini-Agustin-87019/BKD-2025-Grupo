package ar.edu.utn.isi.boardgames;

import ar.edu.utn.isi.boardgames.infrastructure.DataSourceProvider;
import ar.edu.utn.isi.boardgames.infrastructure.DbInitializer;
import ar.edu.utn.isi.boardgames.infrastructure.LocalEntityManagerProvider;
import ar.edu.utn.isi.boardgames.repositories.*;
import ar.edu.utn.isi.boardgames.entities.*;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

public class App {
    public static void main(String[] args) {
        try {
            // 1️⃣ Inicializar DataSource (una sola fuente para JDBC y JPA)
            DataSource ds = DataSourceProvider.get();

            // 2️⃣ Crear esquema en la BD en memoria
            DbInitializer.run(ds, "src/main/resources/sql/ddl_board_games.sql");
            System.out.println("[OK] DDL ejecutado correctamente.");

            // 3️⃣ Crear EntityManagerFactory con el mismo DataSource
            EntityManagerFactory emf = LocalEntityManagerProvider.get(ds);
            System.out.println("[OK] EntityManagerFactory inicializado.");

            // 4️⃣ Probar repositorios
            CategoryRepository categoryRepo = new CategoryRepository();
            PublisherRepository publisherRepo = new PublisherRepository();
            DesignerRepository designerRepo = new DesignerRepository();
            BoardGameRepository boardGameRepo = new BoardGameRepository();

            // Crear datos mínimos
            Category category = new Category("Estrategia");
            Publisher publisher = new Publisher("Fantasy Flight");
            Designer designer = new Designer("Reiner Knizia");
            categoryRepo.create(category);
            publisherRepo.create(publisher);
            designerRepo.create(designer);

            BoardGame game = new BoardGame(
                    "Tigris & Euphrates",
                    1997,
                    2,
                    4,
                    12,
                    8.75,
                    3500,
                    category,
                    publisher,
                    designer
            );
            boardGameRepo.create(game);

            System.out.println("[OK] Juego creado: " + game);
            System.out.println("[OK] Total juegos en BD: " + boardGameRepo.getAllList().size());

            emf.close();
            System.out.println("[OK] App finalizada correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERROR] Falló la inicialización: " + e.getMessage());
        }
    }
}
