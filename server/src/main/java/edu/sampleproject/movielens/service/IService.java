package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.pojo.*;

import java.util.List;

public interface IService {

    List<MovieLight> getNLightMovies(int n);

    Movie getMovieWithNReviews(String movieId, int n);

    List<MovieLight> getLightMoviesBasedOnFilter(String filter, int n);

    void addMovie(Movie movie);


}
