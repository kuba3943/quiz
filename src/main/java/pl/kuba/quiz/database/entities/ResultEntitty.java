package pl.kuba.quiz.database.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.kuba.quiz.frontend.Difficulty;

import javax.naming.ldap.PagedResultsControl;
import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity(name = "RESULT")
@ToString
@Setter
public class ResultEntitty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PlayerEntity player;

    private Double result;

    private Difficulty difficulty;

    private String category;

    public ResultEntitty(PlayerEntity player, Double result, Difficulty difficulty, String category){
        this.player = player;
        this.result= result;
        this.difficulty=difficulty;
        this.category=category;
    }
}
