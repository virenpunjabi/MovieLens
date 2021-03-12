package edu.sampleproject.movielens.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private String movieId;
    private String userName;
    private String comment;
    private Date reviewDate;
    private ReviewType reviewType;
    private Double rating;
}
