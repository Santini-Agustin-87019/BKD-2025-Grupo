package ar.edu.utn.isi.boardgames.entities;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "CATEGORIES", uniqueConstraints = {
        @UniqueConstraint(name = "UK_CATEGORIES_NAME", columnNames = "NAME")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CATEGORY")
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 160)
    private String name;

}
