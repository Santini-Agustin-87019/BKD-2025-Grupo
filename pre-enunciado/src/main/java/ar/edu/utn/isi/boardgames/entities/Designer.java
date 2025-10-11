package ar.edu.utn.isi.boardgames.entities;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "DESIGNERS", uniqueConstraints = {
        @UniqueConstraint(name = "UK_DESIGNERS_NAME", columnNames = "NAME")
})
@Data
@NoArgsConstructor
public class Designer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_designer")
    @SequenceGenerator(name = "seq_designer", sequenceName = "SEQ_DESIGNER_ID", allocationSize = 1)
    @Column(name = "ID_DESIGNER")
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 160)
    private String name;

    // Constructor sin ID (para crear nuevas instancias)
    public Designer(String name) {
        this.name = name;
    }
}
