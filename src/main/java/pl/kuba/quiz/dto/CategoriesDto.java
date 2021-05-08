package pl.kuba.quiz.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.java.Log;

import java.util.List;


@ToString
@NoArgsConstructor
@Getter
public class CategoriesDto {


    @JsonProperty("trivia_categories")
    private List<CategoryDto> categories;

    @NoArgsConstructor
    @Getter
    @ToString
    public static class CategoryDto {
        private int id;
        private String name;
    }




}
