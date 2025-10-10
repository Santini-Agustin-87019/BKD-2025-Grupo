// Paquete del sistema de menús
package enunciado.parcial.menu;

/**
 * Interfaz funcional que representa una acción ejecutable dentro del menú.
 * 
 * @param <T> El tipo de contexto con el que trabajará la acción (por ejemplo, un Empleado o Departamento).
 * 
 * Al ser una interfaz funcional, puede implementarse usando expresiones lambda o referencias a métodos.
 */
@FunctionalInterface // Indica que solo tiene un método abstracto
public interface OptionMenu<T> {

    /**
     * Método que define la acción que debe ejecutarse cuando el ítem del menú es seleccionado.
     * 
     * @param contexto Objeto de tipo T que puede aportar información o estado al ejecutar la acción.
     */
    void ejecutar(T contexto);
}
