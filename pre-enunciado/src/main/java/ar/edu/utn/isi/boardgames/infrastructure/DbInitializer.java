package ar.edu.utn.isi.boardgames.infrastructure;

/**
 * Clase utilitaria para inicializar una base de datos H2 en memoria
 * ejecutando un script SQL (por ejemplo, ddl_board_games.sql).
 *
 * Cómo usar:
 *    DbInit.run();  // Ejecuta el script y deja la BD lista en memoria.
 *
 * Reutilización:
 *    - Cambiar el package según tu proyecto.
 *    - Cambiar la URL, usuario, password y path del script.
 *
 * Ejemplo típico en un proyecto con JPA:
 *    - Se ejecuta DbInit.run() antes de crear el EntityManagerFactory.
 *    - El archivo SQL se ubica en src/main/resources/sql/.
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DbInitializer {

  /**
   * URL de conexión H2 en memoria.
   * Cambiar "boardgamesPU" por el nombre lógico de tu proyecto.
   * El flag DB_CLOSE_DELAY=-1 mantiene la BD viva mientras la app siga corriendo.
   */
  private static final String URL = "jdbc:h2:mem:boardgamesPU;DB_CLOSE_DELAY=-1";

  /**
   * Usuario y contraseña de H2.
   * Cambiar si usás una BD con credenciales distintas.
   */
  private static final String USER = "sa";
  private static final String PASS = "";

  // Constructor privado para evitar instanciación
  private DbInitializer() {}

  /**
   * Método principal de inicialización.
   *    Cambiar la ruta del archivo SQL si el tuyo está en otra ubicación.
   *
   * @throws SQLException si ocurre un error en la ejecución SQL.
   * @throws IOException si hay un problema al leer el archivo SQL.
   */
  public static void run() throws SQLException, IOException {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
      exec(conn, "src/main/resources/sql/ddl_board_games.sql");
    }
  }

  /**
   * Ejecuta el contenido de un archivo SQL en una conexión dada.
   *
   * @param conn conexión JDBC abierta.
   * @param file ruta al archivo SQL a ejecutar.
   * @throws IOException si el archivo no puede leerse.
   * @throws SQLException si ocurre un error al ejecutar el SQL.
   */
  private static void exec(Connection conn, String file) throws IOException, SQLException {
    // Lee el contenido completo del archivo SQL (UTF-8)
    String sql = Files.readString(Path.of(file), StandardCharsets.UTF_8);

    // Ejecuta el script en un Statement simple
    try (Statement st = conn.createStatement()) {
      st.execute(sql);
    }
  }
}
