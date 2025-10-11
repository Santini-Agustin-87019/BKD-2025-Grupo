package ar.edu.utn.isi.boardgames.services.util;

/**
 * Clase de utilidad estática (utility class) diseñada para la estandarización 
 * de cadenas de texto (nombres, títulos, etc.) mediante un proceso de normalización.
 * El objetivo es asegurar una representación consistente para facilitar la búsqueda
 * y comparación, independientemente de variaciones en espaciado o capitalización.
 */
public final class NameNormalizer {
     // Constructor privado: Evita que se creen instancias de esta clase. 
     // Es una clase de utilidad y su uso debe ser estrictamente estático.
     private NameNormalizer() {}

    /**
     * Normaliza una cadena de texto aplicando una secuencia de limpieza.
     *
     * Pasos de normalización:
     * 1. Manejo seguro de nulos.
     * 2. Eliminación de espacios en blanco iniciales y finales (trim).
     * 3. Colapso de múltiples espacios internos, tabulaciones o saltos de línea a un solo espacio.
     * 4. Conversión a minúsculas.
     *
     * @param raw La cadena de texto "en bruto" a normalizar.
     * @return La cadena normalizada, o {@code null} si la entrada fue nula.
     */
    public static String normalize(String raw) {
         if (raw == null) return null; // Retorna null si la entrada es nula (manejo de caso nulo).
    
        // Paso 1: Eliminar espacios en blanco alrededor (trim) 
        // Paso 2: Reemplazar cualquier secuencia de uno o más espacios (\\s+) por un solo espacio (" ").
        String trimmed = raw.trim().replaceAll("\\s+", " "); 
    
        // Paso 3: Convertir toda la cadena resultante a minúsculas.
        return trimmed.toLowerCase();
    }
}