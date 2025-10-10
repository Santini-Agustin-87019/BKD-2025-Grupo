package backend;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cafetera {
    
    // Atributos
    private String marca;
    private String modelo;
    private int capacidadMaxima;
    private int contenidoActual;
    private boolean encendida;
    private int cafeServidos;
    private int temperatura; //20 a 100

    // Métodos
    public void encender(){
        // cambia el estado a encendido, setea temperatura a 20 y contenido actual a 0.
        if (this.encendida == false){
            this.encendida = true;
            this.temperatura = 20;
            this.contenidoActual = 0;
            System.out.println("\n>>> ENCENDIDO");
        } else {
            System.out.println("\n>>> YA ESTA ENCENDIDA");
        }
    }
    
    public void apagar(){
        // cambia el estado a apagado y reinicia la cantidad de cafés servidos.
        if (this.encendida == true){
            this.encendida = false;
            this.cafeServidos = 0;
            System.out.println("\n>>> APAGADO");
        } else {
            System.out.println("\n>>> YA ESTA APAGADO");
        }
    }

    public void cargarAgua(int ml){
        // agrega agua a la cafetera. Si intenta cargar más de la capacidad máxima,
        // se carga solo hasta el límite. Solo puede hacerse si está encendida.
        if (this.encendida){
            if ((this.contenidoActual + ml) > this.capacidadMaxima){
                this.contenidoActual = this.capacidadMaxima;
                System.out.println("\n>>> Agua al tope: " + this.contenidoActual);
            } else {
                this.contenidoActual += ml;
                System.out.println("\n>>> Agua actual: " + this.contenidoActual);
            }
        } else {
            System.out.println("\n### La cafetera esta apagada ###\n");
        }
    }

    public void calentar(){
        // incrementa la temperatura en 40 unidades por llamada. 
        // Si supera 100, se fija en 100. Solo puede hacerse si está encendida.
        if (this.encendida){
            if((this.temperatura + 40) > 100){
                this.temperatura = 100;
                System.out.println("\n>>> Temperatura al maximo");
            } else {
                this.temperatura += 40;
                System.out.println("\n>>> Temperatura actual: " + this.temperatura);
            }
        } else {
            System.out.println("\n### La cafetera esta apagada ###\n");
        }
    }

    public boolean servirCafe(){
        /*
         * 
    servirCafe(): solo se puede servir café si:
        1. la cafetera está encendida,
        2. hay al menos 100 ml de agua,
        3. la temperatura es igual o mayor a 90°C.
    Si se cumple, se descuenta 100 ml del contenido actual y se incrementa cafesServidos en 1.
    Si no se cumple alguna condición, el método retorna false.
         */
        if (this.encendida){
            if ((this.contenidoActual >= 100) && (this.temperatura >= 90)){
                this.contenidoActual -= 100;
                this.cafeServidos++;
                return true;
            } else{
                System.out.println("\n### No cumple las condiciones ###");
                return false;
            }
        } else {
            System.out.println("\n### La cafetera esta apagada ###\n");
            return false;
        }
    }
}
