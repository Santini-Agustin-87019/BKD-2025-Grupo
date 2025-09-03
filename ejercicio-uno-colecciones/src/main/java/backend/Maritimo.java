package backend;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Maritimo extends Viaje {

    // Atributos
    private int cantidadContenedores;
    private double costoPorKilo;
    private double precioTransportado;
   
   
    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
    @Override
    public double importeVentaPrecioLista() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'importeVentaPrecioLista'");
    }


}
