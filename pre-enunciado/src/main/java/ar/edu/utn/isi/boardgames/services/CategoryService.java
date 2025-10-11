package ar.edu.utn.isi.boardgames.services;

import java.util.HashMap;
import ar.edu.utn.isi.boardgames.entities.Category;
import ar.edu.utn.isi.boardgames.repositories.CategoryRepository;
import ar.edu.utn.isi.boardgames.services.util.NameNormalizer;

public class CategoryService {
    private final HashMap<String, Category> cache = new HashMap<>();
    private final CategoryRepository repo = new CategoryRepository();

    public Category getOrCreate(String name) {
        String key = NameNormalizer.normalize(name);
        if (cache.containsKey(key)) return cache.get(key);

        // Buscar exacto por nombre normalizado
        Category found = repo.getByName(key);
        if (found != null) {
            cache.put(key, found);
            return found;
        }

        Category created = new Category(key);
        repo.create(created);
        cache.put(key, created);
        return created;
    }
}
