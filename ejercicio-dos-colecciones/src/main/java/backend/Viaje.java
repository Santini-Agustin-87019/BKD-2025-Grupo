package backend;

import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Viaje implements Comparable{
    
    // Atribtuso
    protected String codigo;
    protected int nroReserva;
    protected double precio;
    protected int tipo;
    protected Cliente cliente;

    public abstract double importeVentaPrecioLista();



}
