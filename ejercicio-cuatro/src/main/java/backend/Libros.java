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
    private int paguina;
    private double precioPorDia;
    private String autor;
}
