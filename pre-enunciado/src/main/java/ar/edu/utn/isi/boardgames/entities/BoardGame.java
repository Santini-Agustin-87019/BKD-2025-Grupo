package ar.edu.utn.isi.boardgames.entities;

// Importaciones de JPA (Jakarta Persistence API) para el mapeo de entidades.
import jakarta.persistence.*;

import lombok.*;

/**
 * Entidad JPA que representa la tabla BOARD_GAMES en la base de datos H2.
 * 
 * Cada objeto BoardGame equivale a una fila en la tabla BOARD_GAMES.
 */
@Entity // Indica que esta clase es una entidad JPA (se mapea a una tabla)
@Table(
    name = "BOARD_GAMES", // Nombre real de la tabla en la base de datos
    indexes = { // Definición de los índices usados en la tabla (para acelerar consultas)
        @Index(name = "IX_BG_NAME", columnList = "NAME"),
        @Index(name = "IX_BG_CATEGORY", columnList = "ID_CATEGORY"),
        @Index(name = "IX_BG_PUBLISHER", columnList = "ID_PUBLISHER"),
        @Index(name = "IX_BG_DESIGNER", columnList = "ID_DESIGNER"),
        @Index(name = "IX_BG_RATING", columnList = "AVERAGE_RATING"),
        @Index(name = "IX_BG_YEAR", columnList = "YEAR_PUBLISHED")
    }
)
@Data 
@NoArgsConstructor
public class BoardGame {

    // ======================== CAMPOS/ATRIBUTOS ========================

    /**
     * Clave primaria de la tabla (ID_GAME en el DDL).
     */
    @Id // Marca este campo como la clave primaria.
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    // Usa la estrategia IDENTITY → el ID se genera automáticamente por la BD (autoincremental).
    @Column(name = "ID_GAME") 
    private Integer id;

    /**
     * Nombre del juego (columna NAME).
     * No puede ser nulo y tiene un límite de 200 caracteres.
     */
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    /**
     * Año de publicación (puede ser nulo si el dato no está en la fuente).
     */
    @Column(name = "YEAR_PUBLISHED")
    private Integer yearPublished;

    /**
     * Mínimo de jugadores (obligatorio).
     * Regla: debe ser > 0 según el DDL.
     */
    @Column(name = "MIN_PLAYERS", nullable = false)
    private int minPlayers;

    /**
     * Máximo de jugadores (puede ser NULL, lo que significa "sin tope").
     */
    @Column(name = "MAX_PLAYERS")
    private Integer maxPlayers; // null = sin límite superior

    /**
     * Edad mínima recomendada (puede ser NULL si no se conoce).
     */
    @Column(name = "MIN_AGE")
    private Integer minAge;

    /**
     * Promedio de calificación (valor decimal entre 0 y 10).
     * Para tipos Double, no se especifica precision/scale ya que son punto flotante IEEE 754
     */
    @Column(name = "AVERAGE_RATING")
    private Double averageRating;

    /**
     * Número de usuarios que votaron el juego (puede ser NULL).
     */
    @Column(name = "USERS_RATING")
    private Integer usersRating;

    // ======================== RELACIONES ========================

    /**
     * Relación con la categoría (género) del juego.
     * Muchos juegos pueden pertenecer a una categoría → @ManyToOne.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "ID_CATEGORY", // Columna FK en BOARD_GAMES
        nullable = false, // Obligatorio
        foreignKey = @ForeignKey(name = "FK_BG_CATEGORY") // Nombre explícito de la FK
    )
    private Category category;

    /**
     * Relación con la editorial (publisher).
     * Igual lógica que category.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "ID_PUBLISHER",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_BG_PUBLISHER")
    )
    private Publisher publisher;

    /**
     * Relación con el diseñador del juego.
     * Cada juego tiene un diseñador (obligatorio).
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "ID_DESIGNER",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_BG_DESIGNER")
    )
    private Designer designer;

    // ======================== CONSTRUCTORES ========================
    /**
     * Constructor sin ID (para crear nuevas instancias)
     */
    public BoardGame(String name, Integer yearPublished, int minPlayers, Integer maxPlayers, 
                     Integer minAge, Double averageRating, Integer usersRating,
                     Category category, Publisher publisher, Designer designer) {
        this.name = name;
        this.yearPublished = yearPublished;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.minAge = minAge;
        this.averageRating = averageRating;
        this.usersRating = usersRating;
        this.category = category;
        this.publisher = publisher;
        this.designer = designer;
    }

    // ======================== MÉTODOS ========================

    /**
     * Sobrescribe el método toString() para mostrar información legible del juego.
     * Lombok ya genera uno, pero acá se personaliza para mostrar solo campos básicos
     * y evitar cargar relaciones LAZY (Category, Publisher, Designer).
     */
    @Override
    public String toString() {
        return "BoardGame{id=" + id +
                ", name='" + name + '\'' +
                ", yearPublished=" + yearPublished +
                ", minPlayers=" + minPlayers +
                ", maxPlayers=" + maxPlayers +
                ", minAge=" + minAge +
                ", averageRating=" + averageRating +
                ", usersRating=" + usersRating +
                '}';
    }
}
