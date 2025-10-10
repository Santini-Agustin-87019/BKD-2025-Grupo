package enunciado.parcial.services;

import enunciado.parcial.services.interfaces.IService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import enunciado.parcial.entities.Empleado;
import enunciado.parcial.repositories.EmpleadoRepository;

public class EmpleadosServices implements IService<Empleado, Integer> {
    
    private final EmpleadoRepository empleadoRepo;
    private final DepartamentoService departamentoService;
    private final PuestoService puestoService;

    public EmpleadosServices() {
        empleadoRepo = new EmpleadoRepository();
        departamentoService = new DepartamentoService();
        puestoService = new PuestoService();
    }


    public void bulkInsert(File fileToImport) throws IOException {
        Files.lines(Paths.get(fileToImport.toURI()))
                .skip(1) // saltear cabecera
                .forEach(linea -> {
                    Empleado emp = this.procesarLinea(linea);
                    this.empleadoRepo.create(emp);
                });
    }

    private Empleado procesarLinea(String linea) {
        // nombre,edad,fecha_ingreso,salario,empleado_fijo,departamento,puesto
        //  1       2         3        4        6                6         7
        String[] tokens = linea.split(",");

        Empleado empleado = new Empleado();
        empleado.setNombre(tokens[1]);

        // para convetir valores enteros
        empleado.setEdad(Integer.parseInt(tokens[2]));

        // para convertir fechas
        LocalDate fecha = LocalDate.parse(tokens[3]);
        empleado.setFechaIngreso(fecha);

        // para convertir a double
        empleado.setSalario(Double.parseDouble(tokens[4]));

        // para convertir a booleano es
        empleado.setEmpleadoFijo(tokens[5].equalsIgnoreCase("1"));

        String nombre = tokens[6];
        var depa = departamentoService.getOrCreateByName(nombre);
        empleado.setDepartamento(depa);

        nombre = tokens[7];
        var puesto = puestoService.getOrCreateByName(nombre);
        empleado.setPuesto(puesto);

        return empleado;
    }

    @Override
    public Empleado getById(Integer id) {
        return this.empleadoRepo.getById(id);
    }

    @Override
    public Empleado getOrCreateById(Integer id) {
        Empleado emp = this.getById(id);
        if (emp == null) {
            emp = new Empleado();
            emp.setId(id);
            this.empleadoRepo.create(emp);
        }
        return emp;
    }

    @Override
    public Empleado getOrCreateByName(String name) {
        Empleado emp = this.empleadoRepo.getByName(name);
        if (emp == null) {
            emp = new Empleado();
            emp.setNombre(name);
            this.empleadoRepo.create(emp);
        }
        return emp;
    }

    @Override
    public List<Empleado> getAll() {
        return this.empleadoRepo.getAllList();
    }

    @Override
    public Stream<Empleado> getAllStream() {
        return this.empleadoRepo.getAllStream();
    }

    
    
}
