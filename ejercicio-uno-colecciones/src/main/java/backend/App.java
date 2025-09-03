package backend;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.File;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {        
        
        File archivo = new File("C:\\Users\\agusn\\proyecto-test-bkd\\ejercicio-uno-colecciones\\viajes.csv");
        int terr = 0; int aer = 0; int marit = 0;

        try (Scanner sc = new Scanner(archivo)){
            
            if (sc.hasNextLine()) {
                sc.nextLine(); // Saltear la primera línea (encabezados)
            }
            
            List<Viaje> listViaje = new ArrayList<>();
            HashMap<String,Cliente> clientesMap = new HashMap<>();
            
            while (sc.hasNextLine()){
                String linea = sc.nextLine();
                String[] datos = linea.split(";");

                //VIAJES
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
                
                clientesMap.put(cuit, cliente);

                if (tipoViaje == 1){
                    // 1.AEREO
                    Aereo aereo = new Aereo(codigo, nroReserva, precio, tipoViaje, cliente, millasAcumuladas, codAereo);
                    listViaje.add(aereo);
                    aer++;
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
                        listViaje.add(terrestre);
                        terr++;
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
                        listViaje.add(maritimo);
                        marit++;
                    }
                }
                
            }

            System.out.print("\n\n>>> Clientes cargados: ");
            System.out.println(clientesMap.size());

            System.out.println("\n\n>>> Cantidad de viajes Terrestres: " + terr);
            System.out.println(">>> Cantidad de viajes Aereos: " + aer);
            System.out.println(">>> Cantidad de viajes Maritimo: " + marit);
            System.out.println();

            
            int totalMillas = 0; // Aereo
            int totalPasajeros = 0; // Terrestre
            int totalContenedor = 0; // Maritimo

            for (Viaje viaje : listViaje){
                if (viaje instanceof Aereo){
                    Aereo aereo = (Aereo) viaje;
                    totalMillas += aereo.getMillasAcumuladas();
                } else {
                    if (viaje instanceof Terrestre){
                        Terrestre terrestre = (Terrestre) viaje;
                        totalPasajeros += terrestre.getCantidadPasajeros();
                    } else {
                        Maritimo maritimo = (Maritimo) viaje;
                        totalContenedor += maritimo.getCantidadContenedores();
                        if (maritimo.getCantidadContenedores() >= 5){
                            System.out.println(">>> Maritimo: " + maritimo.costoTotalDeUnViaje());
                        }
                    }
                }
            }

            System.out.println("\n\n>>> Cantidad total de pasajeros transportados: " + totalPasajeros);
            System.out.println(">>> Cantidad total de millas acumuladas: " + totalMillas);
            System.out.println(">>> Cantidad total de contenedores transportados: " + totalContenedor);

            

            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
