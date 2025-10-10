// Paquete del sistema de menús
package enunciado.parcial.menu;

/**
 * Interfaz genérica que define el comportamiento general de un menú.
 * 
 * @param <T> El tipo de contexto con el que el menú trabajará.
 * 
 * Esta interfaz marca las operaciones básicas que debe cumplir cualquier clase
 * que implemente un menú: agregar opciones y ejecutarlas.
 */
public interface IMenu<T> {

    /**
     * Agrega una nueva opción al menú.
     * 
     * @param opcion Número o índice que representa la opción en el menú.
     * @param accion Objeto ItemMenu que contiene el texto y la acción a ejecutar.
     */
    void agregarOpcion(int opcion, ItemMenu<T> accion);

    /**
     * Ejecuta el menú, recibiendo un contexto de tipo T.
     * 
     * El contexto puede ser, por ejemplo, un objeto Departamento, Empleado, o incluso null
     * dependiendo de qué datos necesite el menú para funcionar.
     *
     * @param contexto El objeto de contexto sobre el cual se ejecutan las acciones del menú.
     */
    void runMenu(T contexto);
}
