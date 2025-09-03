package backend;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {        
        
        File archivo = new File("C:\\Users\\agusn\\proyecto-test-bkd\\ejercicio-uno-colecciones\\viajes.csv");
        
        try (Scanner sc = new Scanner(archivo)){
            
            if (sc.hasNextLine()) {
                sc.nextLine(); // Saltear la primera línea (encabezados)
            }
            
            while (sc.hasNextLine()){
                String linea = sc.nextLine();
                // "VJ088;1087;20109.04;1;1711;UA;0;0;0;0.0;0.0;Empresa_21;30-27846706-2"
                String[] datos = linea.split(";");


                String codigo = datos[0];
                int nroReserva = Integer.parseInt(datos[1]);
                double precio = Double.parseDouble(datos[2]);
                int tipoViaje = Integer.parseInt(datos[3]);
                //AEREO
                int millasAcumuladas = Integer.parseInt(datos[4]);
                String codAereo = datos[5];
                //TERRESTRE
                int provinciasVisitadas = Integer.parseInt(datos[6]);
                int cantidadPasajeros = Integer.parseInt(datos[7]);
                //MARITIMO
                int cantidadContenedores = Integer.parseInt(datos[8]);
                double costoPorKilo = Double.parseDouble(datos[9]);
                double precioTransportado = Double.parseDouble(datos[10]);
                //CLIENTE
                String nombreEmpresa = datos[11];  
                String cuit = datos[12];
                
                Cliente cliente = new Cliente(nombreEmpresa, cuit);

                if (tipoViaje == 1){
                    // 1.AEREO
                    Aereo aereo = new Aereo(codigo, nroReserva, precio, tipoViaje, cliente, millasAcumuladas, codAereo);

                } else{
                    if (tipoViaje == 2){
                        // 2.TERRESTRE
                        Terrestre terrestre = Terrestre.builder()
                        .codigo(codigo)
                        .nroReserva(nroReserva)
                        .precio(precio)
                        .tipo(tipoViaje)
                        .cliente(cliente)
                        .provinciasVisitadas(provinciasVisitadas)
                        .cantidadPasajeros(cantidadPasajeros)
                        .build();
                    }else{
                        // 3.MARITIMO
                        Maritimo maritimo = Maritimo.builder()
                        .codigo(codigo)
                        .nroReserva(nroReserva)
                        .precio(precio)
                        .tipo(tipoViaje)
                        .cliente(cliente)
                        .cantidadContenedores(cantidadContenedores)
                        .costoPorKilo(costoPorKilo)
                        .precioTransportado(precioTransportado)
                        .build();
                    }
                }


            }

            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
