package ar.edu.utn.isi.boardgames.infrastructure;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public final class LocalEntityManagerProvider {

  private static volatile EntityManagerFactory EMF;

  private LocalEntityManagerProvider() {}

  public static EntityManagerFactory get(DataSource ds) {
    if (EMF == null) {
      synchronized (LocalEntityManagerProvider.class) {
        if (EMF == null) {
          Map<String, Object> props = new HashMap<>();

          // Muy importante: usar el MISMO DataSource
          props.put("jakarta.persistence.nonJtaDataSource", ds);

          // Hibernate config pedida en el pre-enunciado
          props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
          props.put("hibernate.hbm2ddl.auto", "none");     // NO tocar esquema
          props.put("hibernate.show_sql", "false");        
          props.put("hibernate.format_sql", "true");

          // Si vos vas a manejar el persistence.xml, esto no pisa nada crítico.
          EMF = Persistence.createEntityManagerFactory("boardgamesPU", props);
        }
      }
    }
    return EMF;
  }
}
