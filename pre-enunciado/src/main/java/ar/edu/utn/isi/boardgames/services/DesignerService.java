package ar.edu.utn.isi.boardgames.services;

import java.util.HashMap;
import ar.edu.utn.isi.boardgames.entities.Designer;
import ar.edu.utn.isi.boardgames.repositories.DesignerRepository;
import ar.edu.utn.isi.boardgames.services.util.NameNormalizer;

public class DesignerService {
    private final HashMap<String, Designer> cache = new HashMap<>();
    private final DesignerRepository repo = new DesignerRepository();

    public Designer getOrCreate(String name) {
        String key = NameNormalizer.normalize(name);
        if (cache.containsKey(key)) return cache.get(key);

        Designer found = repo.getByName(key);
        if (found != null) {
            cache.put(key, found);
            return found;
        }

        Designer created = new Designer(key);
        repo.create(created);
        cache.put(key, created);
        return created;
    }
}
