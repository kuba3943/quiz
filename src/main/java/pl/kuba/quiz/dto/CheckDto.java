package pl.kuba.quiz.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CheckDto {
    private boolean status;
    private String message;


}
