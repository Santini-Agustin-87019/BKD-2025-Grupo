package backend;

import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class App extends ProductoDigital{

    // Atributos
    private Plataforma plataforma;

    // Métodos
    @Override
    public double impuesto(){
        return 0.15;
    };

    @Override
    public String getTipo(){
        return "APP";
    }

    // Constructor
    public App(String sku, String tipo, String nombre, double precioBase, String extra, List<Etiqueta> etiquetas) {
        super(sku, tipo, nombre, precioBase, etiquetas);
        
        // Convierte el string extra a un valor de la enum Plataforma.
        // El metodo valueOf() busca en el enum el valor que coincida con el string.
        this.plataforma = Plataforma.valueOf(extra.toUpperCase());
    }
}
