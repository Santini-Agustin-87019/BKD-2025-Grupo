package ar.edu.utn.isi.boardgames.infrastructure;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

public final class DataSourceProvider {

  private static volatile DataSource INSTANCE;

  private DataSourceProvider() {}

  public static DataSource get() {
    if (INSTANCE == null) {
      synchronized (DataSourceProvider.class) {
        if (INSTANCE == null) {
          JdbcDataSource ds = new JdbcDataSource();
          // MISMA URL / USER / PASS en todos lados
          ds.setURL("jdbc:h2:mem:boardgamesPU;DB_CLOSE_DELAY=-1");
          ds.setUser("sa");
          ds.setPassword("");
          INSTANCE = ds;
        }
      }
    }
    return INSTANCE;
  }
}
