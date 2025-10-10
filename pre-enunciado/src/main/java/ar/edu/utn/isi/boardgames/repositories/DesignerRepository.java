package ar.edu.utn.isi.boardgames.repositories;

import ar.edu.utn.isi.boardgames.entities.Designer;

public class DesignerRepository extends Repository<Designer, Integer> {

    @Override
    protected Class<Designer> getEntityClass() {
        return Designer.class;
    }
}
