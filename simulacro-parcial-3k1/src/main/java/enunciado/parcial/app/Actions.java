package enunciado.parcial.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import enunciado.parcial.services.EmpleadosServices;


public class Actions {

    /* 
     * Método de ejemplo (del profesor) que permite importar empleados desde un archivo CSV.
     * Básicamente busca en un directorio archivos CSV que contengan la palabra "empleado" 
     * y los carga en el sistema usando el servicio EmpleadoService.
     */
    public void importarEmpleados(AppContext context) {
        // Obtiene del contexto (AppContext) la URL donde están los archivos a importar
        var pathToImport = (URL) context.get("path");

        // Bloque try-with-resources: recorre todos los archivos dentro del directorio indicado
        try (var paths = Files.walk(Paths.get(pathToImport.toURI()))) {
            
            // Se filtran los archivos encontrados:
            // 1. Solo se toman archivos regulares (no directorios)
            // 2. Que terminen en ".csv"
            // 3. Luego se convierten a objetos File y se guardan en una lista
            var csvFiles = paths
                    .filter(Files::isRegularFile)                            // solo archivos, no carpetas
                    .filter(path -> path.toString().endsWith(".csv")) // que terminen en ".csv"
                    .map(path -> path.toFile())                              // convertir Path → File
                    .toList();                                               // recolectar en lista

            // Se procesa la lista de archivos CSV:
            // 1. Busca el primer archivo cuyo nombre contenga la palabra "empleado"
            // 2. Si lo encuentra → lo pasa al servicio para cargar empleados
            // 3. Si no lo encuentra → lanza una excepción
            csvFiles.stream()
                    .filter(f -> f.getName().contains("empleado"))  // buscar archivo con "empleado" en el nombre
                    .findFirst()                                      // quedarse con el primero
                    .ifPresentOrElse(f -> {                           // si existe:
                        // Obtener el servicio de empleados desde el contexto
                        var service = context.getService(EmpleadosServices.class);
                        try {
                            // Insertar en bloque todos los empleados del archivo CSV
                            service.bulkInsert(f);
                        } catch (IOException e) {
                            e.printStackTrace(); // manejar error de lectura del archivo
                        }
                    },
                    () -> {
                        // Si no se encontró ningún archivo válido, lanzar excepción
                        throw new IllegalArgumentException("Archivo inexistente");
                    });

        } catch (IOException | URISyntaxException e) {
            // Manejo de errores: problemas de acceso al archivo o conversión de URI
            e.printStackTrace();
        }
    }

    public void listarEmpleados(AppContext context) {
        var service = context.getService(EmpleadosServices.class);

        // Recuperar todas las obras desde la BD
        var empleados = service.getAll();

        if (empleados.isEmpty()) {
            System.out.println("⚠ No hay obras registradas en la base de datos.");
        } else {
            System.out.println("📋 Lista de obras artísticas:");
            empleados.forEach(emp -> {
                System.out.printf(
                    "ID: %d | Nombre: %s | Año: %s | Puesto: %s | Departamento: %s | Salario: %.2f | Empleado Fijo: %s%n",
                    emp.getId(),
                    emp.getNombre(),
                    emp.getFechaIngreso().toString(),
                    emp.getPuesto() != null ? emp.getPuesto().getNombre() : "Desconocido",
                    emp.getDepartamento() != null ? emp.getDepartamento().getNombre() : "Desconocido",
                    emp.getSalario(),
                    emp.isEmpleadoFijo() ? "Sí" : "No"
                );
            });
        }
    }

}