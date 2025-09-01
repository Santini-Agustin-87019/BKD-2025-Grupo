package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {

    
    public static void main(String[] args) {
        System.out.println("\n\nEjercicio: Sistema de Biblioteca Editorial");

        Scanner sc = new Scanner(System.in);
        System.out.print("Cantidad de libros a cargar: ");
        int MAX_LIBROS = sc.nextInt();
        Libros[] librosVector = new Libros[MAX_LIBROS];
        int cantidadCargada= 0;
    
        try {

            File archivo = new File("C:\\Users\\agusn\\proyecto-test-bkd\\ejercicio-cuatro\\libros.csv");
            Scanner scanner = new Scanner(archivo);
            
            while (scanner.hasNextLine() && cantidadCargada < MAX_LIBROS) {
                String linea = scanner.nextLine();
                String[] datos = linea.split(",");

                String isbn = datos[0];
                String titulo = datos[1];
                int nroEstante = Integer.parseInt(datos[2]);
                int pagina = Integer.parseInt(datos[3]);
                double precioPorDia = Double.parseDouble(datos[4]);
                String id = datos[5];
                String nombre = datos[6];
                String apellido = datos[7];
                int aniosCarrera = Integer.parseInt(datos[8]);
                
                Autor author = new Autor(id, nombre, apellido, aniosCarrera);
                Libros libro = new Libros(isbn, titulo, nroEstante, pagina, precioPorDia, author);
                // es medio raro. cantidadCargada es 0, osea en la posicion 0 en el libroVector
                // se guarda el libro que acabo de crear arriba. Lugo sumo 1 a cantidadCargada.
                // Esto hasta que sea igual a 100. cuando eso pasa, el while se corta por el 
                // cantidadCargada < MAX_LIBROS. (y ademas porque son 100 los libros)
                librosVector[cantidadCargada] = libro;
                // System.out.println(libro.getAutor().getId());
                cantidadCargada++;
            }
            
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        recaudacionEstimada(librosVector);
        authorTrayec(librosVector);
        promedioPaginas(librosVector);
    }
    
    public static void recaudacionEstimada(Libros[] librosVector){
        // Recaudación estimada: asumiendo que el tiempo promedio de alquiler de un
        // libro son 15 días, calcular cuánto recaudaría la editorial si se alquilaran 
        // todos los libros cargados.
        // isbn,titulo,nroEstante,paginas,precioPorDia,autorId,autorNombre,autorApellido,autorAniosCarrera
        // precioPorDia * 15 
        
        float sumaPrecio = 0;

        for (Libros lib : librosVector){
            sumaPrecio += (lib.getPrecioPorDia() * 15);
        }

        System.out.println("\n>>> Recaudacion total: " + sumaPrecio + "$");

    }

    public static void authorTrayec(Libros[] librosVector){
        // Autores con trayectoria: informar la cantidad de autores que tienen más 
        // de 18 años de carrera.

        int count = 0;
        for (Libros lib : librosVector){
            if (lib.getAutor().getAniosCarrera() > 18){
                count++;
            } 
        }
        System.out.println(">>> Autores con mas de 18 de Trayectoria: " + count);
    }

    public static void promedioPaginas(Libros[] librosVector){
        // Promedio de páginas: determinar el promedio de páginas de todos los libros 
        // ubicados en posiciones de estante pares.
        int sumPagPar = 0;
        int sumPag = 0;
        float prom = 0;

        for (Libros lib : librosVector) {
            if (lib.getNroEstante()%2 == 0) {
                sumPagPar += lib.getPagina();
            }     
            sumPag += lib.getPagina();
        }

        prom = (sumPagPar*100) / sumPag;
        System.out.println(">>> Promedio de Paginas pares: " + prom + "%");

    }
}
