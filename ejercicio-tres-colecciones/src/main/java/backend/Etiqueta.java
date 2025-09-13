package backend;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Etiqueta{

    // Atributos
    private String nombre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etiqueta etiqueta = (Etiqueta) o;
        return nombre != null ? nombre.equals(etiqueta.nombre) : etiqueta.nombre == null;
    }

    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }

}   
