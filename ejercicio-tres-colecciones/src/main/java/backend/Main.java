package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main{

    public static void main(String[] args){
        File file = new File("C:\\Users\\agusn\\proyecto-test-bkd\\ejercicio-tres-colecciones\\productos.csv");

        try (Scanner sc = new Scanner(file)){
            if (!sc.hasNextLine()) {
                throw new ItemNotFoundException("No se encontraron productos en el archivo."); 
            }

            if (sc.hasNext()) {
                sc.nextLine(); // Saltar la primera línea (encabezados)
            }

            List<ProductoDigital> productos = new ArrayList<>();

            while (sc.hasNextLine()){
                String linea = sc.nextLine();
                String[] campos = linea.split(";");
                String sku = campos[0];
                String tipo = campos[1];
                String nombre = campos[2];
                double precioBase = Double.parseDouble(campos[3]);
            

                List<Etiqueta> etiquetas = new ArrayList<>();
                String[] etiquetasStr = campos[5].split("\\|");
                
                for (String nombreEtiqueta : etiquetasStr) {
                    etiquetas.add(new Etiqueta(nombreEtiqueta.trim()));
                }
                etiquetasStr = null; // Liberar referencia

                if (tipo.equals("EBOOK")){
                    int extra = Integer.parseInt(campos[4]);
                    productos.add(new Ebook(sku, tipo, nombre, precioBase, extra, etiquetas));
                } else if (tipo.equals("APP")){
                    String extraStr = campos[4];
                    productos.add(new App(sku, tipo, nombre, precioBase, extraStr, etiquetas));
                } else if (tipo.equals("CURSO")){
                    int extra = Integer.parseInt(campos[4]);
                    productos.add(new CursoOnline(sku, tipo, nombre, precioBase, extra, etiquetas));
                } else {
                    System.err.println("Tipo de producto desconocido en la línea: " + linea);
                }
            }
            

            // Tranformamos en un HasSet
            // Un HashSet es una coleccion que no permite elementos duplicados
            // Entonces, si hay productos repetidos en el archivo CSV, solo se guardara uno de ellos.
            HashSet<ProductoDigital> productosUnicos = new HashSet<>(productos);          

            // Agrupamos por tipo
            // Creamos un nuevo Map<clave, valor> para agrupar por tipo
            Map<String, List<ProductoDigital>> productosPorTipo = new HashMap<>();
            
            // Cargamos el mapa con los productos unicos leidos del archivo CSV
            // La clave es el tipo de producto (EBOOK, APP y CURSO)
            // Esto es una forma de hacerlo con un metodo que se llama computeIfAbsent, no lo entiendo mucho.
            for (ProductoDigital producto : productosUnicos){
                // esta sera la Key, si hay 3 tipos, tendra 3 keys y 3 listas con esos productos
                // si tiene 40 tipos, lo mismo.
                String tipo = producto.getTipo();
                
                // Busca en productosPorTipo si ya existe una lista para ese tipo de producto (por ejemplo: Existe una lista par 'EBOOK'?).
                // Si no existe, crea una nueva lista vacia y la asocia a ese tipo de producto.
                // Luego, agrega el producto actual a la lista correspondiente a su tipo.
                // Si ya existe, simplemente agrega el producto a la lista existente.
                productosPorTipo.computeIfAbsent(tipo, k -> new ArrayList<>()).add(producto);
            }


            // ########## Imprimimos los resultados por tipo
            /*  
            Map no es una lista, sino una coleccion de pares clave-valor.
            Entonces para recorrer un mapa, no podemos usar un for-each normal.

            Map.Entry es una interfaz que representa un par clave-valor en un mapa.
            Lo usamos como una forma de guardar cada par clave-valor que hay en el mapa productosPorTipo.
            productosPorTipo.entrySet() devuelve un conjunto de pares clave-valor (Map.Entry) del mapa productosPorTipo.
            entonces Map.Entry guarda un par clave-valor, y entrySet() devuelve un conjunto de esos pares.
            */
            // ############# Esta comentado porque devuelve muchoas cosas.
/* 
            for (Map.Entry<String, List<ProductoDigital>> entry : productosPorTipo.entrySet()) {
                // entry es cada par clave-valor del mapa productosPorTipo
                // con getKey() obtenemos la clave (el tipo de producto)  
                // Ej.: EBBOK, APP, CURSO              
                String tipo = entry.getKey();

                // con getValue() obtenemos el valor (la lista de productos de ese tipo)
                // Ej.: [Producto1, Producto2, ...]
                List<ProductoDigital> listaProductos = entry.getValue();
                
                
                System.out.println("Tipo: " + tipo);
                // Recorremos la lista de productos de ese tipo de key
                // cada producto tiene un nombre, un sku, un precio base, un precio final y una lista de etiquetas
                // y todos tienen en comun la misma key (son del mismo tipo).
                for (ProductoDigital producto : listaProductos) {
                    System.out.println("  SKU: " + producto.getSku());
                    System.out.println("  Nombre: " + producto.getNombre());
                    System.out.println("  Precio Base: " + producto.getPrecioBase());
                    System.out.println("  Precio Final: " + producto.precioFinal());
                    System.out.print("  Etiquetas: ");
                    
                    // Dentro de cada producto, hay una lista con x etiquetas
                    // Recorremos esa lista de etiquetas
                    for (Etiqueta etiqueta : producto.getEtiquetas()) {
                        System.out.print(etiqueta.getNombre() + " ");
                    }
                    System.out.println("\n");

                    // Damo por finalizado este tipo de producto y pasamos al siguiente 
                }

                
            }
*/


            // Agrupamos por Etiqueta
            // Creamos un nuevo Map<clave, valor> para agrupar por etiqueta
            Map<String, List<ProductoDigital>> productosPorEtiqueta = new HashMap<>();


            // Aca para rellenera el Map productosPorEtiqueta hacemos lo mismo que en productosPorTipo
            // con la diferencia que aca las etiquetas estan almacenadas en una Lista, por eso hay 2 for
            // recorremos cada lista de productosUnicos y luego, dentro de prodcutosUnicos, la lista de etiquetas
            // haciendo que cada etiqueta, sea una key.
            
            
            for (ProductoDigital producto : productosUnicos) {
                for (Etiqueta etiqueta : producto.getEtiquetas()) {
                    String nombreEtiqueta = etiqueta.getNombre();

                    // Recordemos que computeIfAbsent si existe una lista con esa key la crea, si ya existe, agrega el producto al ArrayList.
                    productosPorEtiqueta.computeIfAbsent(nombreEtiqueta, k -> new ArrayList<>()).add(producto);
                }
            }
            
            // ############# Esta comentado porque devuelve muchoas cosas.
            // Imprimimos los resultados agrupados por etiqueta
/* 
            for (Map.Entry<String, List<ProductoDigital>> entry : productosPorEtiqueta.entrySet()) {
                String etiqueta = entry.getKey();
                List<ProductoDigital> listaProductos = entry.getValue();
                
                System.out.println("Etiqueta: " + etiqueta);
                for (ProductoDigital producto : listaProductos) {
                    System.out.println("  SKU: " + producto.getSku());
                    System.out.println("  Nombre: " + producto.getNombre());
                    System.out.println("  Precio Base: " + producto.getPrecioBase());
                    System.out.println("  Precio Final: " + producto.precioFinal());
                    System.out.print("  Etiquetas: ");
                    for (Etiqueta etq : producto.getEtiquetas()) {
                        System.out.print(etq.getNombre() + " ");
                    }
                    System.out.println("\n");
                }
            }
*/
            


            // Metodos
            // Mostrar cantidad de registros cargados y cantidad de productos únicos.
            System.out.println("Cantidad total de productos leidos (incluyendo duplicados): " + productos.size());
            System.out.println("Cantidad de productos unicos: " + productosUnicos.size());

            // Mostrar cantidad por tipo (EBOOK, APP, CURSOONLINE).
            for (Map.Entry<String, List<ProductoDigital>>  entry : productosPorTipo.entrySet()) {
                String tipo = entry.getKey();
                List<ProductoDigital> listaProductos = entry.getValue();
                System.out.println("Cantidad de productos del tipo " + tipo + ": " + listaProductos.size());
            }
 
            // Listar Top 3 por precio final (descendente).
            // creamos una nueva lista para no modificar la original
            List<ProductoDigital> listaOrdenadaDecendente = new ArrayList<>(productos);

            // ordenamos la lista de mayor a menor por precio final, si queremos de menor a mayor, cambiamos producto2 por producto1 y viceversa.
            Collections.sort(listaOrdenadaDecendente, (producto1, producto2) -> Double.compare(producto2.precioFinal(), producto1.precioFinal()));
            System.out.println("Top 3 productos por precio final (descendente):");
            for (int i = 0; i < 3; i++){
                ProductoDigital producto = listaOrdenadaDecendente.get(i);
                System.out.println("\t" + (i + 1) + ". " + producto.getNombre() + " - Precio Final: " + producto.precioFinal());
            }

            // Calcular facturación simulada por tipo (suma de precioFinalExacto).
            for (Map.Entry<String, List<ProductoDigital>> entry : productosPorTipo.entrySet()) {
                String tipo = entry.getKey();
                List<ProductoDigital> listaProductos = entry.getValue();
                double facturacionTotal = 0.0;
                for (ProductoDigital producto : listaProductos){
                    facturacionTotal += producto.precioFinalExacto();
                }
                System.out.println("Facturación simulada para el tipo " + tipo + ": " + Math.round(facturacionTotal * 100.0) / 100.0);
            }


            // Implementar búsqueda por nombre (substring, case-insensitive).
            String busqueda = "App Azure Pro";
            for (ProductoDigital producto : productosUnicos) {
                if (producto.getNombre().toLowerCase().contains(busqueda.toLowerCase())){
                    System.out.println("Producto encontrado: " + producto.getNombre() + " - SKU: " + producto.getSku() + " - Precio Final: " + producto.precioFinal());
                }
            }

            // Agrupar por Etiqueta y, dado una Etiqueta, listar los SKUs asociados.
            String etiquetaEjemplo = "microservices";

            
            // Calcular el total de un carrito, con redondeo a dos decimales (lista de SKUs). Si falta alguno, lanzar ItemNotFoundException.
            List<String> carritoSKUSimulacion = List.of("EB001", "A053", "A075", "EB999");
            double totalCarrito = 0.0;
            double precio = 0.0;
            for (String sku : carritoSKUSimulacion) {
                boolean encontrado = false;
                for (ProductoDigital producto : productosUnicos){
                    if (producto.getSku().contains(sku)){
                        encontrado = true;
                        precio = producto.precioFinal();
                    }
                }
                if (encontrado){
                    totalCarrito += precio;
                } 
                // else {
                //     throw new ItemNotFoundException("El SKU " + sku + " no se encontro en el carrito.");
                // }
            }

            
            System.out.println("Total del carrito: " + Math.round(totalCarrito * 100.0) / 100.0);

        } catch (ItemNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        
    }
}
