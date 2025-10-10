// Paquete donde se encuentra la clase
package enunciado.parcial.entities;

// Importaciones necesarias para trabajar con JPA (anotaciones de persistencia)
import jakarta.persistence.*;

// Importaciones de Lombok para generar código automáticamente (constructores, getters, setters, etc.)
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Importación para manejar listas (usada en la relación con empleados)
import java.util.List;

/**
 * Clase entidad que representa un "Puesto" dentro del sistema.
 * Un puesto puede estar ocupado por varios empleados.
 * Usa anotaciones de JPA para el mapeo con la base de datos
 * y Lombok para generar código repetitivo automáticamente.
 */
@Entity // Indica que esta clase es una entidad JPA (se mapea a una tabla en la BD)
@Table(name = "puestos") // Especifica el nombre de la tabla en la base de datos ("puestos")
@Data // Lombok genera automáticamente getters, setters, toString, equals y hashCode
@NoArgsConstructor // Lombok genera un constructor vacío (sin parámetros)
@AllArgsConstructor // Lombok genera un constructor con todos los parámetros


public class Puesto {

    /* 
     * Campo que representa el identificador único del puesto.
     * Se mapea a la columna "id" y se genera automáticamente (autoincremental).
     */
    @Id // Indica que este campo es la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID (autoincrement)
    @Column(name = "id") // Especifica el nombre de la columna en la tabla
    private int id;

    /**
     * Campo que representa el nombre del puesto de trabajo.
     * Es obligatorio (nullable = false), tiene un máximo de 100 caracteres y debe ser único.
     */
    @Column(name = "nombre", nullable = false, length = 100, unique = true)
    private String nombre;

    /**
     * Relación One-to-Many (uno a muchos) con la entidad Empleado.
     * Un puesto puede tener varios empleados asociados.
     * 
     * - mappedBy = "puesto": indica que el lado propietario de la relación es la clase Empleado.
     * - cascade = CascadeType.ALL: las operaciones sobre Puesto se propagan a sus Empleados (persist, remove, etc.)
     * - fetch = FetchType.LAZY: los empleados se cargan solo cuando se los necesita (carga diferida).
     */
    @OneToMany(mappedBy = "puesto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Empleado> empleados;

    /**
     * Constructor adicional para crear un Puesto solo con su nombre.
     * Útil cuando se crea un nuevo puesto sin asignar empleados todavía.
     *
     * @param nombre Nombre del puesto
     */
    public Puesto(String nombre) {
        this.nombre = nombre;
    }
}
