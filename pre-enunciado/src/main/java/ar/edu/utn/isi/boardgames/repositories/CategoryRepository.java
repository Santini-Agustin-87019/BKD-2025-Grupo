package ar.edu.utn.isi.boardgames.repositories;

import ar.edu.utn.isi.boardgames.entities.Category;

public class CategoryRepository extends Repository<Category, Integer> {

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }
}
