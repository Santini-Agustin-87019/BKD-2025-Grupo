package enunciado.parcial.services.interfaces;

import java.util.List;
import java.util.stream.Stream;

public interface IService<T, K> {
    
    /*
     * Busca una entidad por su ID y la devuelve. Si no existe, devuelve null.
     */
    T getById(K id);
    
    /*
     * Busca una entidad por su ID y la devuelve, si no existe la crea
     */
    T getOrCreateById(K id);

    /*
     * Busca una entidad por su nombre y la devuelve, si no existe la crea
     */
    T getOrCreateByName(String name);

    /*
     * Listar todos los objetos de tipo T
     */
    List<T> getAll();

    /*
     * Listar todos los objetos de tipo T como Stream
     */
    Stream<T> getAllStream();
    

}
