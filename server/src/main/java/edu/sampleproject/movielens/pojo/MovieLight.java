package edu.sampleproject.movielens.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.language.bm.Lang;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieLight {
    private String id;
    private String name;
    private List<Genre> genreList;
    private String posterUri;
    private Double rating;
    private Certification certification;
    private List<Language> languageList;

}

