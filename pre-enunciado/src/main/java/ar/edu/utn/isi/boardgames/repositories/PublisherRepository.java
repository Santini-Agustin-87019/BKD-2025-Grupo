package ar.edu.utn.isi.boardgames.repositories;

import ar.edu.utn.isi.boardgames.entities.Publisher;

public class PublisherRepository extends Repository<Publisher, Integer> {

    @Override
    protected Class<Publisher> getEntityClass() {
        return Publisher.class;
    }
}
