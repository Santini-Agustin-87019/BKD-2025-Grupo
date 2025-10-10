// Paquete del sistema de menús
package enunciado.parcial.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import enunciado.parcial.app.AppContext;

/**
 * Implementación genérica de IMenu<T>.
 *
 * - Guarda las opciones en un Map<Integer, ItemMenu<T>> donde la clave es el número de opción.
 * - Muestra el menú en consola y ejecuta la acción (OptionMenu<T>) del ItemMenu seleccionado.
 * - Recibe un "contexto" de tipo T que se entrega a cada acción cuando se ejecuta.
 */
public class Menu<T> implements IMenu<T> {

    // Estructura que mapea número de opción -> ítem del menú (texto + acción)
    // HashMap no garantiza orden de iteración; si querés mantener el orden de agregado,
    // podés cambiarlo a LinkedHashMap.
    private final Map<Integer, ItemMenu<T>> opciones = new HashMap<>();

    /**
     * Registra una opción en el menú.
     *
     * @param opcion número de opción que elegirá el usuario (ej: 1, 2, 3...)
     * @param accion ItemMenu<T> que contiene el texto visible y la acción a ejecutar
     */
    @Override
    public void agregarOpcion(int opcion, ItemMenu<T> accion) {
        this.opciones.put(opcion, accion);                       // guarda/actualiza la opción
        System.out.println("Opción " + opcion + ": " + accion    // usa toString() del ItemMenu -> texto
                           + " registrada.");
    }

    /**
     * Ciclo principal del menú. Muestra las opciones y ejecuta la seleccionada
     * hasta que el usuario elige "0 - Salir".
     *
     * @param contexto objeto de tipo T que se pasa a cada acción al ejecutarse
     */
    @Override
    public void runMenu(T contexto) {
        while (true) {
            int opcion = this.mostrarMenu(contexto); // pinta el menú y lee la opción

            if (opcion == 0) {                       // 0 es la salida
                System.out.println("Saliendo del menú...");
                break;                                // termina el bucle
            }

            if (this.opciones.containsKey(opcion)) {  // valida que exista la opción
                this.opciones.get(opcion).ejecutar(contexto); // ejecuta la acción asociada (OptionMenu<T>)
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

    /**
     * Imprime el menú y lee la opción elegida por el usuario desde un Scanner
     * que se obtiene del contexto (AppContext).
     *
     * @param contexto debe ser un AppContext (se valida con pattern matching)
     * @return número de opción elegida
     */
    public int mostrarMenu(T contexto) {
        // Pattern matching for instanceof (Java 16+):
        // valida que el contexto sea AppContext y, si lo es, lo "desestructura" en la variable appCtx
        if (contexto instanceof AppContext appCtx) {

            // Obtiene el Scanner previamente guardado en el AppContext
            Scanner sc = appCtx.get("scanner", Scanner.class);

            // Muestra encabezado y cada opción registrada en el Map
            System.out.println("--- Menú ---");
            for (Map.Entry<Integer, ItemMenu<T>> entry : this.opciones.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue().toString());
            }

            System.out.println("0 - Salir.");
            System.out.print("Seleccione una opción: ");

            // ⚠️ Validación de entrada:
            while (!sc.hasNextInt()) {
                System.out.println("Entrada inválida. Por favor, ingrese un número de opción: ");
                sc.next(); // descarta el token inválido
            }

            int eleccion = sc.nextInt(); // lee el entero
            sc.nextLine();               // consume el salto de línea pendiente (higiene del Scanner)
            return eleccion;
        } else {
            // Si llaman al menú con un contexto que no es AppContext, lanzamos error controlado
            throw new IllegalArgumentException("El contexto proporcionado no es válido.");
        }
    }
}
