package backend;

import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class CursoOnline extends ProductoDigital {
    
    // Atributos
    private int hora;


    // Metodo abstracto que debe ser implementado en las clases hijas
    @Override
    public double impuesto(){
        return this.hora > 20 ? 0.18 : 0.12;
    };

    @Override
    public String getTipo(){
        return "CURSO";
    }

    // Constructor
    public CursoOnline(String sku, String tipo, String nombre, double precioBase, int hora, List<Etiqueta> etiquetas) {
        super(sku, tipo, nombre, precioBase, etiquetas);
        this.hora = hora;
    }
}
