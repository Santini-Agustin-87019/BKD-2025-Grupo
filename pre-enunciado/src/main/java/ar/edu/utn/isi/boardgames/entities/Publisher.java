package ar.edu.utn.isi.boardgames.entities;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "PUBLISHERS", uniqueConstraints = {
        @UniqueConstraint(name = "UK_PUBLISHERS_NAME", columnNames = "NAME")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PUBLISHER")
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 160)
    private String name;
}
