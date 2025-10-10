// Paquete donde se encuentra la clase
package enunciado.parcial.menu;

/**
 * Clase genérica que representa un ítem (una opción) dentro de un menú.
 * 
 * @param <T> El tipo de contexto con el que trabaja el menú (por ejemplo, Empleado, Departamento, etc.)
 * 
 * Cada ItemMenu tiene un texto que se mostrará al usuario y una acción (OptionMenu)
 * que se ejecutará cuando el usuario seleccione esa opción.
 */
public class ItemMenu<T> {

    // Texto visible de la opción (por ejemplo, "1 - Crear empleado")
    private final String text;

    // Acción asociada a esta opción (una función que se ejecuta cuando el usuario elige este ítem)
    private final OptionMenu<T> accion;

    /**
     * Constructor del ítem de menú.
     *
     * @param text Texto descriptivo de la opción del menú.
     * @param accion Acción a ejecutar cuando el ítem es seleccionado.
     */
    public ItemMenu(String text, OptionMenu<T> accion) {
        this.text = text;
        this.accion = accion;
    }

    /**
     * Sobrescribe el método toString() para devolver el texto del ítem.
     * Esto permite mostrar el ítem directamente al imprimirlo (por ejemplo, en consola).
     */
    @Override
    public String toString() {
        return this.text;
    }

    /**
     * Ejecuta la acción asociada a este ítem.
     * 
     * @param contexto Objeto de tipo T que se pasa como contexto a la acción.
     *                 Esto permite que las acciones del menú trabajen con diferentes tipos de datos.
     */
    public void ejecutar(T contexto) {
        this.accion.ejecutar(contexto); // Llama al método ejecutar() de la acción
    }
}
