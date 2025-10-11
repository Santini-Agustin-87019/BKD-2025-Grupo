package ar.edu.utn.isi.boardgames;

import ar.edu.utn.isi.boardgames.infrastructure.*;
import ar.edu.utn.isi.boardgames.services.*;
import ar.edu.utn.isi.boardgames.repositories.*;
import ar.edu.utn.isi.boardgames.entities.*;



import java.io.File;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

public class App {
    public static void main(String[] args) {
        try {

            // 1) Inicializar BD
            DataSource ds = DataSourceProvider.get();
            DbInitializer.run(ds, "src/main/resources/sql/ddl_board_games.sql");
            System.out.println("[OK] DDL ejecutado");

            // 2) Crear service
            BoardGameService gameService = new BoardGameService();
            
            // 3) Cargar CSV formateado

            //============================= NO TE OLVIDES DE CARGAR EL CSV CORRECTO!!
            File csvFile = new File("src/main/resources/data/board_games.csv");


            System.out.println("Cargando CSV: " + csvFile.getAbsolutePath());
            
            gameService.bulkInsert(csvFile);
            System.out.println("[OK] CSV cargado");

            // 4) Verificar resultados
            var gameRepo = new BoardGameRepository();
            int totalGames = gameRepo.getAllList().size();
            System.out.println("Total de juegos cargados: " + totalGames);

            // 5) Mostrar las 5 categorías con menor promedio de rating
            System.out.println("\n" + "=".repeat(60));
            AppContext context = AppContext.getInstance();
            Acciones acciones = new Acciones();
            acciones.mostrarCategoriasConMenorRating(context);
            
            // 6) Mostrar juegos aptos para criterios específicos
            System.out.println("\n" + "=".repeat(60));
            
            // Ejemplo 1: Juegos para 4 jugadores, edad 12+
            acciones.mostrarJuegosAptos(context, 4, 12);
            
            // Ejemplo 2: Juegos para 2 jugadores, edad 8+
            acciones.mostrarJuegosAptos(context, 2, 8);
            
            // Ejemplo 3: Juegos para 6 jugadores, edad 14+
            acciones.mostrarJuegosAptos(context, 6, 14);
            
        } catch (Exception e) {
            System.err.println("[ERROR] Test falló: " + e.getMessage());
            e.printStackTrace();        }


    }
}
