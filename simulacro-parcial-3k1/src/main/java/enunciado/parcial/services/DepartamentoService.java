// Paquete donde se encuentra esta clase de servicio
package enunciado.parcial.services;

// Importa la interfaz genérica que define los métodos comunes de todos los servicios
import enunciado.parcial.services.interfaces.IService;

// Importaciones necesarias para colecciones y flujos de datos
import java.util.List;
import java.util.stream.Stream;

// Importa la entidad que este servicio gestiona
import enunciado.parcial.entities.Departamento;
// Importa el repositorio específico que maneja la persistencia de Departamento
import enunciado.parcial.repositories.DepartamentoRepository;

/**
 * Servicio de la entidad Departamento.
 * 
 * Esta clase representa la capa de negocio para los departamentos.
 * 
 * - Implementa la interfaz genérica IService<Departamento, Integer>,
 *   por lo que debe definir los métodos CRUD básicos (get, create, etc.).
 * - Utiliza un DepartamentoRepository para acceder a la base de datos.
 * - Contiene la lógica de negocio específica (como crear si no existe).
 */
public class DepartamentoService implements IService<Departamento, Integer> {
    
    // Atributo privado que gestiona el acceso a datos de Departamento
    private DepartamentoRepository departamentoRepo;

    /**
     * Constructor del servicio.
     * 
     * Inicializa el repositorio de Departamento.
     * Esto permite que el servicio delegue en el repositorio
     * las operaciones de persistencia (guardar, buscar, eliminar, etc.).
     */
    public DepartamentoService() {
        this.departamentoRepo = new DepartamentoRepository();
    }

    // ============================================================
    // Métodos de IService<Departamento, Integer>
    // ============================================================

    /**
     * Recupera un Departamento a partir de su ID.
     * 
     * @param id Identificador único del departamento.
     * @return El departamento correspondiente o null si no existe.
     */
    @Override
    public Departamento getById(Integer id) {
        return this.departamentoRepo.getById(id);
    }

    /**
     * Recupera un Departamento por su ID o lo crea si no existe.
     * 
     * Este método aplica una pequeña regla de negocio:
     * si el departamento no se encuentra en la base de datos,
     * se crea uno nuevo con el ID indicado y se guarda.
     * 
     * @param id Identificador del departamento.
     * @return El departamento existente o uno nuevo creado.
     */
    @Override
    public Departamento getOrCreateById(Integer id) {
        Departamento dept = this.getById(id);   // busca el departamento
        if (dept == null) {                     // si no existe...
            dept = new Departamento();          // crea un nuevo objeto
            dept.setId(id);                     // asigna el ID
            this.departamentoRepo.create(dept); // lo guarda en la BD
        }
        return dept; // devuelve el existente o el nuevo
    }

    /**
     * Recupera un Departamento por su nombre o lo crea si no existe.
     * 
     * Este método usa el repositorio para buscar por nombre.
     * Si no encuentra uno, crea un nuevo departamento con ese nombre
     * y lo persiste en la base de datos.
     * 
     * @param name Nombre del departamento.
     * @return El departamento encontrado o el recién creado.
     */
    @Override
    public Departamento getOrCreateByName(String name) {
        Departamento depto = this.departamentoRepo.getByName(name); // busca por nombre
        if (depto == null) {                                        // si no lo encuentra
            depto = new Departamento();                             // crea uno nuevo
            depto.setNombre(name);                                  // asigna el nombre
            this.departamentoRepo.create(depto);                    // guarda en la BD
        } 
        return depto;
    }

    /**
     * Obtiene una lista con todos los departamentos existentes.
     * 
     * @return Lista de objetos Departamento.
     */
    @Override
    public List<Departamento> getAll() {
        return this.departamentoRepo.getAllList();
    }

    /**
     * Devuelve un Stream con todos los departamentos.
     * 
     * Útil cuando se quieren aplicar operaciones con programación funcional
     * (como filtros, mapeos o contadores).
     * 
     * @return Stream<Departamento> con todos los registros.
     */
    @Override
    public Stream<Departamento> getAllStream() {
        return this.departamentoRepo.getAllStream();
    }
}
