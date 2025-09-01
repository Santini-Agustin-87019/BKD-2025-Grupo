package backend;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libros {

    // Atributos
    private String isbn;
    private String titulo;
    private int nroEstante;
    private int pagina;
    private double precioPorDia;
    private Autor autor;

    @Override
    public String toString() {
        return  "Titulo: " + this.titulo + "\n" +
                "Autor - Nombre: " + this.autor.getNombre() + "\n" +
                "Autor - Apellido: " + this.autor.getApellido() + "\n" +
                "Estante: " + this.nroEstante;

    }
}
