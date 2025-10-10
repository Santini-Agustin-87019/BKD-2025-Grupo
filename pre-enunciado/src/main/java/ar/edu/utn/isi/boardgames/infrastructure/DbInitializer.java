package ar.edu.utn.isi.boardgames.infrastructure;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DbInitializer {

  private DbInitializer() {}

  // NUEVO: corre el DDL usando el mismo DataSource
  public static void run(DataSource ds, String ddlPath) throws SQLException, IOException {
    try (Connection conn = ds.getConnection()) {
      String sql = Files.readString(Path.of(ddlPath), StandardCharsets.UTF_8);
      try (Statement st = conn.createStatement()) {
        st.execute(sql);
      }
    }
  }

  // (Opcional) Dejar tu método viejo si lo querés para pruebas rápidas:
  // public static void run() { ... DriverManager ... }
}
