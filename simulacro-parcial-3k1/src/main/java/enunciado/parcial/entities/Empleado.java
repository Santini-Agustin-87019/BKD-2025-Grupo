package enunciado.parcial.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Hay que endender que el nombre y la disposicion de las tablas y columnas debe concordar con la BD `db-schema.sql`
 * 
 * Entidad Empleado que representa un empleado en el sistema.
 * Utiliza JPA para el mapeo objeto-relacional y Lombok para reducir el código boilerplate.
 */
@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "edad", nullable = false)
    private int edad;
    
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;
    
    @Column(name = "salario", nullable = false, precision = 10, scale = 2)
    private double salario;
    
    @Column(name = "empleado_fijo", nullable = false)
    private boolean empleadoFijo;
    
    /**
     * ============ RELACIÓN MANY-TO-ONE CON DEPARTAMENTO ============
     * 
     * Relación Many-to-One con Departamento.
     * Significa: MUCHOS empleados pueden pertenecer a UN departamento.
     * 
     * @ManyToOne: Anotación que define la cardinalidad de la relación
     *   - Many (Muchos): Se refiere a esta entidad (Empleado)
     *   - One (Uno): Se refiere a la entidad relacionada (Departamento)
     * 
     * fetch = FetchType.EAGER: Estrategia de carga
     *   - EAGER: Carga inmediata - cuando se obtiene un Empleado, automáticamente
     *     también se carga su Departamento en la misma consulta SQL
     *   - Alternativa: LAZY = carga perezosa (solo cuando se accede al departamento)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    /**
     * @JoinColumn: Define la columna de clave foránea en la tabla empleados
     *   - name = "departamento_id": Nombre de la columna FK en la tabla empleados
     *   - nullable = false: Esta columna NO puede ser NULL (empleado DEBE tener departamento)
     *   - Esta columna hace referencia al ID de la tabla departamentos
     */
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento; // Objeto Java que representa la entidad relacionada
    
    /**
     * ============ RELACIÓN MANY-TO-ONE CON PUESTO ============
     * 
     * Relación Many-to-One con Puesto.
     * Significa: MUCHOS empleados pueden tener el mismo puesto.
     * Ejemplo: Varios empleados pueden ser "Desarrollador Senior"
     * 
     * @ManyToOne: Misma lógica que la relación con Departamento
     *   - Many empleados → One puesto
     * 
     * fetch = FetchType.EAGER: Carga inmediata del puesto junto con el empleado
     *   - Útil cuando siempre necesitas saber el puesto del empleado
     *   - Genera un JOIN en la consulta SQL automáticamente
     */
    @ManyToOne(fetch = FetchType.EAGER)
    /**
     * @JoinColumn para la relación con Puesto:
     *   - name = "puesto_id": Columna FK en tabla empleados que apunta a puestos.id
     *   - nullable = false: Empleado DEBE tener un puesto asignado obligatoriamente
     *   
     * Resultado en BD: La tabla empleados tendrá estas columnas FK:
     *   - departamento_id (apunta a departamentos.id)
     *   - puesto_id (apunta a puestos.id)
     */
    @JoinColumn(name = "puesto_id", nullable = false)
    private Puesto puesto; // Referencia al objeto Puesto asociado
    
    /**
     * Constructor personalizado sin id (para crear nuevos empleados).
     */
    public Empleado(String nombre, int edad, LocalDate fechaIngreso, double salario, 
                   boolean empleadoFijo, Departamento departamento, Puesto puesto) {
        this.nombre = nombre;
        this.edad = edad;
        this.fechaIngreso = fechaIngreso;
        this.salario = salario;
        this.empleadoFijo = empleadoFijo;
        this.departamento = departamento;
        this.puesto = puesto;
    }
    
    /**
     * ============ MÉTODO DE CÁLCULO DE SALARIO FINAL ============
     * 
     * Calcula el salario final del empleado basado en su condición laboral.
     * 
     * Lógica de negocio:
     * - Si el empleado es FIJO (empleadoFijo = true): aplica un 8% de aumento adicional
     * - Si el empleado NO es fijo (empleadoFijo = false): mantiene el salario original
     * 
     * @return double El salario final calculado
     *   - Empleado fijo: salario original + 8% = salario * 1.08
     *   - Empleado temporal: salario original sin modificaciones
     * 
     * Ejemplo:
     * - Empleado fijo con salario $1000 → resultado: $1080.00
     * - Empleado temporal con salario $1000 → resultado: $1000.00
     */
    public double calcularSalarioFinal() {
        // Verificamos si el empleado tiene condición de empleado fijo
        if (this.empleadoFijo) {
            // Empleado fijo: aplicamos 8% adicional (multiplicamos por 1.08)
            return this.salario * 1.08;
        } else {
            // Empleado temporal: devolvemos el salario original sin modificaciones
            return this.salario;
        }

        /* Alternativa: 
         * Se puede realizar en una sola línea usando operador ternario:
         * 
         *  return this.empleadoFijo ? this.salario * 1.08 : this.salario;
         * */
    }
}
