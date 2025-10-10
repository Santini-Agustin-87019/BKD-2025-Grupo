package enunciado.parcial.services;

import enunciado.parcial.services.interfaces.IService;
import enunciado.parcial.entities.Puesto;
import enunciado.parcial.repositories.PuestoRepository;

public class PuestoService implements IService<Puesto, Integer> {
    
    private PuestoRepository puestoRepo;

    public PuestoService() {
        this.puestoRepo = new PuestoRepository();
    }

    @Override
    public Puesto getById(Integer id) {
        return this.puestoRepo.getById(id);
    }

    @Override
    public Puesto getOrCreateById(Integer id) {
        Puesto puesto = this.getById(id);
        if (puesto == null) {
            puesto = new Puesto();
            puesto.setId(id);
            this.puestoRepo.create(puesto);
        }
        return puesto;
    }

    @Override
    public Puesto getOrCreateByName(String name) {
        Puesto puesto = this.puestoRepo.getByName(name);
        if (puesto == null) {
            puesto = new Puesto();
            puesto.setNombre(name);
            this.puestoRepo.create(puesto);
        }
        return puesto;
    }

    @Override
    public java.util.List<Puesto> getAll() {
        return this.puestoRepo.getAllList();
    }

    @Override
    public java.util.stream.Stream<Puesto> getAllStream() {
        return this.puestoRepo.getAllStream();
    }
    



}
