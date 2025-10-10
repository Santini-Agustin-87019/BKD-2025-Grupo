// Paquete donde se encuentra esta clase
package enunciado.parcial.repositories;

// Importa la entidad Empleado (la clase que este repositorio va a gestionar)
import enunciado.parcial.entities.Empleado;

/**
 * Clase repositorio específica para la entidad Empleado.
 * 
 * Hereda de la clase genérica Repository<Empleado, Integer>,
 * lo que le da acceso a todos los métodos CRUD (crear, leer, actualizar, eliminar)
 * implementados en la clase base.
 * 
 * Esta clase sirve como capa de acceso a datos para la tabla "empleados" en la base de datos.
 */
public class EmpleadoRepository extends Repository<Empleado, Integer> {

    public EmpleadoRepository(){
        super();
    }

    /**
     * Método obligatorio que especifica el tipo de entidad manejada por este repositorio.
     * 
     * El repositorio genérico (Repository<T, K>) necesita saber qué clase de entidad
     * está manejando (en este caso, Empleado.class) para construir dinámicamente las consultas JPQL.
     * 
     * @return La clase de la entidad administrada por este repositorio (Empleado.class)
     */
    @Override
    protected Class<Empleado> getEntityClass() {
        return Empleado.class;
    }
}
