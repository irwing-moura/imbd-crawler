package model;


import lombok.Data;

@Data
public class Movie {

    private String title;
    private String rating;
    private String[] directors;
    private String link;
    private String actor;
    private String positiveComment;
}
