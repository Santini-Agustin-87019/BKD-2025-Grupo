package backend;

import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class Ebook extends ProductoDigital{

    // Atributos
    private int paginas;

    // Metodos
    @Override
    public double impuesto() {
        return 0.5; 
    }

    @Override
    public String getTipo(){
        return "EBOOK";
    }

    // constructor
    public Ebook(String sku, String tipo, String nombre, double precioBase, int paginas, List<Etiqueta> etiquetas) {
        super(sku, tipo, nombre, precioBase, etiquetas);
        this.paginas = paginas;
    }
}
