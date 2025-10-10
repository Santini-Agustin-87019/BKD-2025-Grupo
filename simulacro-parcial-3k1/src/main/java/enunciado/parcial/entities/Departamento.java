package enunciado.parcial.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidad Departamento que representa un departamento organizacional en el sistema.
 * Cada departamento puede tener múltiples empleados asociados.
 * Utiliza JPA para el mapeo objeto-relacional y Lombok para reducir el código boilerplate.
 */
@Entity
@Table(name = "departamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Departamento {
    
    /**
     * Identificador único del departamento.
     * Se genera automáticamente usando estrategia IDENTITY.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    /**
     * Nombre del departamento.
     * Campo obligatorio con longitud máxima de 100 caracteres.
     */
    @Column(name = "nombre", nullable = false, length = 100, unique = true)
    private String nombre;
    
    /**
     * Relación One-to-Many con Empleado.
     * Un departamento puede tener múltiples empleados.
     * Se utiliza mappedBy para indicar que la relación es bidireccional.
     */
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Empleado> empleados;
    
    /**
     * Constructor personalizado sin id ni lista de empleados (para crear nuevos departamentos).
     * 
     * @param nombre El nombre del departamento
     */
    public Departamento(String nombre) {
        this.nombre = nombre;
    }
}