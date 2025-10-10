// Paquete donde se encuentra el repositorio
package enunciado.parcial.repositories;

// Importa la entidad Puesto (la clase que este repositorio va a manejar)
import enunciado.parcial.entities.Puesto;

/**
 * Clase de repositorio específica para la entidad Puesto.
 * 
 * Hereda de una clase genérica Repository<Puesto, Integer>,
 * lo que significa que maneja objetos de tipo Puesto
 * y su clave primaria es de tipo Integer.
 * 
 * Esta clase permite realizar operaciones CRUD (crear, leer, actualizar, eliminar)
 * sobre la tabla de "puestos" en la base de datos.
 */
public class PuestoRepository extends Repository<Puesto, Integer> {

    public PuestoRepository(){
        super();
    }

    /**
     * Método obligatorio que especifica qué tipo de entidad maneja este repositorio.
     * 
     * El framework genérico Repository necesita saber con qué clase de entidad
     * está trabajando para poder crear las consultas JPA adecuadas.
     *
     * @return La clase de la entidad administrada por este repositorio (Puesto.class)
     */
    @Override
    protected Class<Puesto> getEntityClass() {
        return Puesto.class;
    }

    /*
     * 💡 Nota:
     * Si los métodos heredados del repositorio genérico (Repository)
     * ya funcionan correctamente (como getAll, save, delete, getById, etc.),
     * no hace falta volver a escribirlos aquí.
     * 
     * Solo se redefine getEntityClass(), ya que es necesario para indicar
     * qué entidad maneja esta clase hija.
     */
}
