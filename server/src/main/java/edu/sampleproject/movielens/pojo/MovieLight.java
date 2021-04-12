package edu.sampleproject.movielens.pojo;

import lombok.*;
import org.apache.commons.codec.language.bm.Lang;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieLight {
    private String id;
    private String name;
    private List<Genre> genreList;
    private String genres;
    private String posterUri;
    private Double rating;
    private Certification certification;
    private List<Language> languageList;
    private String languages;
    private Integer runtime;
}

