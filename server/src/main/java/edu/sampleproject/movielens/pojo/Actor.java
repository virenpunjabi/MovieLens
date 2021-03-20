package edu.sampleproject.movielens.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Actor {
    private String id;
    private String name;
    private String actorImageUri;
    private String description;
    private LocalDate dob;
    private String birthPlace;
    private List<MovieLight> movies;
}
