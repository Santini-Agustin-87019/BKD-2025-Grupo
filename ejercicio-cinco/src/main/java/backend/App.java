package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import lombok.val;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("HOLA MUNDO!!");

        
        try {
            File file = new File("C:\\Users\\agusn\\proyecto-bkd-2025\\ejercicio-cinco\\clientes.csv");
            Scanner scanner = new Scanner(file);

    // Acá lo que hacemos es saltarnos la primera linea del archivo csv ya que tiene el titulo de los valores
            if (scanner.hasNextLine()){
                scanner.nextLine();
            }
            
            Vector<Cliente> vectorCliente = new Vector<>();
            
    // Acá dice "Mientras haya una lina, segui. Cuando no haya mas, pará"
            while (scanner.hasNextLine()) {
                
    //  Leo hasta que haya un salto de linea. ej: linea = "JuanPerez,12345678,25,Estudiante,57,12.5,true"
                String linea = scanner.nextLine();
    /* 
    Acá divido esa tira de strings en celdas de un arreglo/vector. ej:
        >>> valores = ["JuanPerez", "12345678", "25", "Estudiante", "57", "12.5", "true"] 
    No es lo mismo que el anterior ya que el 'linea' era una tira de string, en este caso cada valor puede 
    ser accedido con un indice.
        >>> valores[0] = "Andres"; valores[2]="30"; valores[5]="true";
    */
                String[] valores = linea.split(",");

    /*
     * Lo que hacemos acá es asignarle una variable a cada valor de la variable 'valores'.
     * Accedemos primero al indice 0 que contiene el nombre y lo guardamos en la variable nombre para 
     * luego cargarlo a el objeto Cliente, y asi con todos los otros datos.
     * Esto va internado, y el nombre de la primera iteracion no va a ser el de la ultima, van iterando
     * hasta que el while no tenga mas.
     */
                String nombre = valores[0];
                String dni = valores[1];
                short edad = Short.parseShort(valores[2]);
                String ocupacion = valores[3];
                int cantidadPosteos = Integer.parseInt(valores[4]);
                float horasEnPlataforma = Float.parseFloat(valores[5]);
                boolean esVerificado = Boolean.parseBoolean(valores[6]);


                Cliente cliente = new Cliente(nombre, dni, edad, ocupacion, cantidadPosteos, horasEnPlataforma, esVerificado);
                vectorCliente.add(cliente);
            }
            
            scanner.close();

            for (int i=0; i<10; i++){
                
            }
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        
        // Cliente cliente = new Cliente("Juan Perez", "12345678A", (short) 30, 5, 12.5f, true);
        // System.out.println(cliente);

    }
}
