package edu.sampleproject.movielens.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private String id;
    private String name;
    private String shortDesc;
    private List<Genre> Genres;
    private String posterUri;
    private Double rating;
    private List<LightActor> lightActors;
    private List<Review> reviews;
    private Certification certification;
    private Integer runtime;
    private List<MovieFormat> movieFormats;
    private List<Language> languages;
    private Date releaseDate;
    private String trailerLink;
}
