package backend;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    // Atributos
    private String nombre;
    private String dni;
    private short edad;
    private String ocupacion;
    private int cantidadPosteos;
    private float horasEnPlataforma;
    private boolean esVerificado;

}
