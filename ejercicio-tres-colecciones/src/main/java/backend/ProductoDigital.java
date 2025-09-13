package backend;

import java.util.List;

import lombok.*;
import lombok.experimental.SuperBuilder;


@SuppressWarnings("rawtypes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class ProductoDigital implements Comparable{
    
    // Atributos
    protected String sku;
    protected String tipo;
    protected String nombre;
    protected double precioBase;
    protected List<Etiqueta> etiquetas;

    public abstract double impuesto();

    public double precioFinalExacto(){
        return this.precioBase * (1 + this.impuesto());
    }

    public double precioFinal(){
        return Math.round(this.precioFinalExacto() * 100.0) / 100.0;
    }

    // No entiendo que tiene que hacer este metodo.
    // quiza una clase abstracta implementada en las clases hijas que devuelvam
    // el tipo que son. pero el enunciado no dice que tiene que ser abstracta.
    public abstract String getTipo();

    @Override
    public int compareTo(Object o) {
        if (o instanceof ProductoDigital) {
            ProductoDigital otro = (ProductoDigital) o;
            return this.sku.compareToIgnoreCase(otro.sku);
        }
        throw new IllegalArgumentException("No se puede comparar con un objeto que no es ProductoDigital");
    }

    // MÉTODOS equals y hashCode usando sku (case-insensitive)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductoDigital)) return false;
        ProductoDigital that = (ProductoDigital) o;
        return this.sku.equalsIgnoreCase(that.sku);
    }

    @Override
    public int hashCode() {
        return sku.toLowerCase().hashCode();
    }
}
