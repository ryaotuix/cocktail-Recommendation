package cocktailrecommender.backend.domain;

import jakarta.persistence.*;

@Entity
public class UserTaste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tasteId")
    private Taste taste;
}
