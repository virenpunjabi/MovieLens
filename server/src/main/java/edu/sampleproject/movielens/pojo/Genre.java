package edu.sampleproject.movielens.pojo;

public enum Genre {
    ACTION("Action"),
    COMEDY("Comedy"),
    ROMANTIC("Romantic"),
    ADVENTURE("Adventure"),
    DRAMA("Drama"),
    THRILLER("Thriller"),
    HORROR("Horror");

    String name;
    Genre(String name){
        this.name = name;
    }
    public String getGenreName(){
        return name;
    }
}
