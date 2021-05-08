package pl.kuba.quiz.frontend;


import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class GameOptions {
    private int numberOfQuestions;
    private Difficulty difficulty;
    private int categoryId;
}
