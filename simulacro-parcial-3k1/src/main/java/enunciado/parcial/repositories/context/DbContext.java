package enunciado.parcial.repositories.context;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase de contexto de base de datos.
 *
 * - Encapsula la creación y manejo del EntityManager.
 * - Implementa el patrón Singleton: solo hay una instancia en toda la app.
 * - Usa la unidad de persistencia definida en META-INF/persistence.xml (nombre: "museo").
 *
 * En JPA/Hibernate:
 * - EntityManagerFactory = fábrica de EntityManager, inicializa la conexión a la base.
 * - EntityManager = interfaz principal para trabajar con entidades (CRUD, queries, transacciones).
 */
public class DbContext {

    // EntityManager único que se usará en toda la aplicación.
    private final EntityManager manager;

    // Instancia estática (única) del Singleton
    public static DbContext instance = null;

    /**
     * Constructor privado (propio del patrón Singleton).
     * - Crea un EntityManagerFactory a partir de la unidad de persistencia "museo".
     * - Obtiene un EntityManager desde esa fábrica.
     */
    private DbContext() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("empleados");
        manager = emf.createEntityManager();
    }

    /**
     * Método de acceso al Singleton.
     * - Si la instancia es null, la crea.
     * - Si ya existe, devuelve la misma.
     */
    public static DbContext getInstance() {
        if (instance == null) {
            instance = new DbContext();
        }
        return instance;
    }

    /**
     * Devuelve el EntityManager que se usará para persistencia.
     * - Con este objeto se realizan operaciones como persist(), merge(), remove(), find(), queries, etc.
     */
    public EntityManager getManager() {
        return this.manager;
    }
}