package backend;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        System.out.println("HELO WORDL!");

        Libro libro1 = new Libro("El Hobbit 2", "Tolkien", 1999, "Fantasia");
        Libro libro2 = new Libro("El Hobbit 1", "Tolkien", 1995, "Fantasia");
        Libro libro3 = new Libro("Terminator", "Spilberg", 2004, "Accion");
        Libro libro4 = new Libro("Seindfield", "NN", 1980, "Comedia");
        Libro libro5 = new Libro("Los Simsons", "Matt Greoning", 1998, "Comedia");

        Biblioteca biblioteca = new Biblioteca();

        biblioteca.agregarLibro(libro1);
        biblioteca.agregarLibro(libro2);
        biblioteca.agregarLibro(libro3);
        biblioteca.agregarLibro(libro4);
        biblioteca.agregarLibro(libro5);

        biblioteca.listarLibros();
        biblioteca.buscarLibro("El Hobbit 2");
        biblioteca.eliminarLibro("El Hobbit 1");
        biblioteca.listarLibros();
        biblioteca.promedioAntiguedadLibros();
    }
}
