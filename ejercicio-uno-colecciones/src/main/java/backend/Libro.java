package backend;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Libro {
    
    // Atributos
    private String titulo;
    private String autor;
    private int anioPublicacion;
    private String genero;

    
}
