package backend;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autor {

    // Atributos
    private String id;
    private String nombre;
    private String apellido;
    private int aniosCarrera;
}
