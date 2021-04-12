package edu.sampleproject.movielens.dao;

import edu.sampleproject.movielens.pojo.Actor;
import edu.sampleproject.movielens.pojo.Movie;
import edu.sampleproject.movielens.pojo.MovieLight;
import edu.sampleproject.movielens.pojo.Review;

import java.io.IOException;
import java.util.List;

public interface MovieDao {
    Movie getMovie(String movieId) throws IOException;
    List<MovieLight> getNRecentMovies(int n) throws IOException;
    List<Review> getNReviewsForMovie(String movieId, int start, int offset) throws IOException;
    Actor getActor(String actorId) throws IOException;

}
