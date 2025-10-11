package ar.edu.utn.isi.boardgames.services;

import java.util.HashMap;
import ar.edu.utn.isi.boardgames.entities.Publisher;
import ar.edu.utn.isi.boardgames.repositories.PublisherRepository;
import ar.edu.utn.isi.boardgames.services.util.NameNormalizer;

public class PublisherService {
    private final HashMap<String, Publisher> cache = new HashMap<>();
    private final PublisherRepository repo = new PublisherRepository();

    public Publisher getOrCreate(String name) {
        String key = NameNormalizer.normalize(name);
        if (cache.containsKey(key)) return cache.get(key);

        Publisher found = repo.getByName(key);
        if (found != null) {
            cache.put(key, found);
            return found;
        }

        Publisher created = new Publisher(key);
        repo.create(created);
        cache.put(key, created);
        return created;
    }
}
