package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
            HashSet<ProductoDigital> productosUnicos = new HashSet<>(productos);          

            // Agrupamos por tipo
            Map<String, List<ProductoDigital>> productosPorTipo = new HashMap<>();
            
            // Esto es una forma de hacerlo con un metodo que se llama computeIfAbsent, no lo entiendo mucho.
            for (ProductoDigital producto : productosUnicos){
                String tipo = producto.getTipo();
                productosPorTipo.computeIfAbsent(tipo, k -> new ArrayList<>()).add(producto);
            }

            
            // De aca para abajo, no enteido nada. Estoy muy perdido. Lo de arriba, bueno safa, no digo que lo entendi
            // al 100%, pero mas o menos. Tengo una idea
            // Obviamente no podria hacerlo de nuevo sin ChatGPT, eso esta claro.
            // pero medio como que me quedo algo.
            // Pero lo de abajo, ni idea. No entiendo que es un Map.Entry, ni que es entrySet, ni nada.
            // No entiendo nada de lo que hay aca abajo.


            // Imprimimos los resultados
            // No tengo ni puta idea como se hizo esto, pero no entiendo absolutamente nada de lo que hay aca abajo.
            for (Map.Entry<String, List<ProductoDigital>> entry : productosPorTipo.entrySet()) {
                String tipo = entry.getKey();
                List<ProductoDigital> listaProductos = entry.getValue();
                
                System.out.println("Tipo: " + tipo);
                for (ProductoDigital producto : listaProductos) {
                    System.out.println("  SKU: " + producto.getSku());
                    System.out.println("  Nombre: " + producto.getNombre());
                    System.out.println("  Precio Base: " + producto.getPrecioBase());
                    System.out.println("  Precio Final: " + producto.precioFinal());
                    System.out.print("  Etiquetas: ");
                    for (Etiqueta etiqueta : producto.getEtiquetas()) {
                        System.out.print(etiqueta.getNombre() + " ");
                    }
                    System.out.println("\n");
                }
            }



            // Agrupamos por Etiqueta
            Map<String, List<ProductoDigital>> productosPorEtiqueta = new HashMap<>();

            for (ProductoDigital producto : productosUnicos) {
                for (Etiqueta etiqueta : producto.getEtiquetas()) {
                    String nombreEtiqueta = etiqueta.getNombre();
                    productosPorEtiqueta.computeIfAbsent(nombreEtiqueta, k -> new ArrayList<>()).add(producto);
                }
            }

            // Imprimimos los resultados agrupados por etiqueta
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



        } catch (ItemNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        
    }
}
