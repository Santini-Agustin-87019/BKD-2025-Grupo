package backend;

import lombok.*;

// Estos @ son parte de lombok. Lo que hacen estas etiquetas es agregar los métodos getters y
// setters y ademas todos los constructores posibles.
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

    // Método
    // Este método calcula la puntuacion de un cliente. Esto nos sera de utilidad a la hora de
    // calcular el total de pts en un método implementado en App.java (puntuacionTotalClientes())
    public float calcularPuntuacion(){
        float pts;
        if (this.edad > 25){
            pts = this.horasEnPlataforma * 2;
        } else{
            pts = this.horasEnPlataforma * 3;
        }
        
        if (this.esVerificado)
            pts += 20.0;
        
        return pts;
    }
}
