package backend;

import java.util.ArrayList;
import java.util.Iterator;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Biblioteca {

    // Atributos
    private ArrayList<Libro> listaLibro = new ArrayList<>();

    public void agregarLibro(Libro libro){
        this.listaLibro.add(libro);
    }

    public void listarLibros(){
        for (Libro libro : this.listaLibro){
            System.out.println(libro.toString());
        }
    }

    public void buscarLibro(String titulo){
        for (Libro libro : listaLibro) {
            if (libro.getTitulo() == titulo){
                System.out.println(libro.toString());
            } 
        }
    }

    // No entiendo como es que funciona esto, lo hizo ChatGPT.
    public void eliminarLibro(String titulo){   
        
        // Creo un iterador para recorrer la lista de libros
        Iterator<Libro> it = listaLibro.iterator();

        // Recorro la lista de libros 
        while (it.hasNext()) {

            // Obtengo el libro actual
            Libro libro = it.next();

            // Si el título del libro actual es igual al título que quiero eliminar, lo elimino
            if (libro.getTitulo().equals(titulo)) {
                it.remove();
            }
        }
    }

    public void promedioAntiguedadLibros(){
        int sumAnti=0;
        for (Libro libro : listaLibro) {
            sumAnti += 2025 - libro.getAnioPublicacion();
        }

        int prom = sumAnti / listaLibro.size();
        System.out.println("Promedio: " + prom + " anios");
    }

}

