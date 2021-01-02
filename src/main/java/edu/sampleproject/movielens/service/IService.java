package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.pojo.*;

import java.util.List;

public interface IService {
    Movie getMovieWithNReviews(String movieId, int n);

    List<MovieLight> getLightMoviesBasedOnFilter(Filter filter, int start, int offset);

    List<Review> getReviews(String movieId, int start, int offset);


}
