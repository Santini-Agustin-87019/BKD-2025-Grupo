package backend;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

public class Aereo extends Viaje{

    // Atributos
    private int millasAcumuladas;
    private String codAerolinea;

    public Aereo(String cod, int res, double prec, int tip, Cliente cl, int mill, String codAer){
        super(cod, res, prec, tip, cl);
        this.millasAcumuladas = mill;
        this.codAerolinea = codAer;
    }


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
