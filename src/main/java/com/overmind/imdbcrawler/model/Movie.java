package com.overmind.imdbcrawler.model;


import lombok.Data;

import java.util.List;

@Data
public class Movie {

    private String title;
    private String rating;
    private String[] directors;
    private String link;
    private List<String> actors;
    private String positiveComment;
}
