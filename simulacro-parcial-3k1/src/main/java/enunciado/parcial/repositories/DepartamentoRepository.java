package enunciado.parcial.repositories;

import enunciado.parcial.entities.Departamento;

public class DepartamentoRepository extends Repository<Departamento, Integer> {
    
    public DepartamentoRepository(){
        super();
    }
    
    @Override
    protected Class<Departamento> getEntityClass() {
        return Departamento.class;
    }
}
