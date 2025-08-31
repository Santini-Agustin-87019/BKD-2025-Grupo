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

    // Declare a el vector clientes para asi poder utilizarlo en todos los métodos y ahorrarme
    // tener que pasarlo como un paramtero en cada método, asi que colocandolo fuera de cualquier
    // método, puede ser accedido por cualqueir método.
    private static Vector<Cliente> vectorCliente = new Vector<>();
    
    
    public static void main(String[] args) {
        System.out.println("HOLA MUNDO!!");
        
        
        try {
            File file = new File("C:\\Users\\agusn\\proyecto-bkd-2025\\ejercicio-cinco\\clientes.csv");
            Scanner scanner = new Scanner(file);

    // Acá lo que hacemos es saltarnos la primera linea del archivo csv ya que tiene el titulo de los valores
            if (scanner.hasNextLine()){
                scanner.nextLine();
            }
            
            
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

            Scanner scan = new Scanner(System.in);

            // Mostrar los 10 primeros clientes cargados con exito
            for (int i=0; i < 10; i++){
                Cliente c = vectorCliente.get(i);
                System.out.println("\n-----------------------------------------------");
                System.out.println("Nombre: " + c.getNombre());
                System.out.println("DNI: " + c.getDni());
                System.out.println("Edad: " + c.getEdad() + "Años");
                System.out.println("Ocupacion: " + c.getOcupacion());
                System.out.println("Cantidad de Posteos: " + c.getCantidadPosteos());
                System.out.println("Horas en la Plataforma: " + c.getHorasEnPlataforma());
                System.out.println("-----------------------------------------------\n");

            }
            
            // Llamada a los métodos descriptos mas abajo en el codigo
            System.out.println("\n\n >>> Cantidad de clientes mayores a");
            System.out.print("Ingrese la edad: ");
            int age = scan.nextInt();
            contarMayorDe(age);

            System.out.println("\n\n >>> Cantidad total de posteos");
            totalPosts();

            System.out.println("\n\n >>> Puntuacion Total de los clientes");
            puntuacionTotalClientes();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // Métodos
    // Este método hace uso de clacularPuntuacion para asi recorrer el vector de clientes y sumar
    // todas las puntuaciones y albergarlas en la variable 'suma'.
    public static void puntuacionTotalClientes(){
        float totalPts = 0;
        for (Cliente c :vectorCliente){
            totalPts += c.calcularPuntuacion();
        }
        System.out.println("El puntaje total de todos los Clientes es: " + totalPts);
    }

    // Este método recorre todo el vector clientes y asi puede sumar la cantidad de posteos
    public static void totalPosts(){
        int suma = 0;
        for (Cliente c : vectorCliente){
            suma += c.getCantidadPosteos();
        }
        System.out.println("La cantidad total de posteos es de: " + suma);
    }

    // Acá utilize otro modo para recorrer el vector, algo mas complejo y menos intuitivo que los
    // anteriores, pero basicamnte hace lo mimso.
    public static void contarMayorDe(int edad){
        int count = 0;
        for (int i=0; i<vectorCliente.size(); i++){
            Cliente c = vectorCliente.get(i);
            if (c.getEdad() > edad)
                count += 1;
        }

        System.out.println("La cantidad de Clientes mayores de " + edad + " es de: " + count);
    }
}
