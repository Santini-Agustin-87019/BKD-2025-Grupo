package ar.edu.utn.isi.boardgames.entities;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "CATEGORIES", uniqueConstraints = {
        @UniqueConstraint(name = "UK_CATEGORIES_NAME", columnNames = "NAME")
})
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_category")
    @SequenceGenerator(name = "seq_category", sequenceName = "SEQ_CATEGORY_ID", allocationSize = 1)
    @Column(name = "ID_CATEGORY")
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 160)
    private String name;

    // Constructor sin ID (para crear nuevas instancias)
    public Category(String name) {
        this.name = name;
    }

}
