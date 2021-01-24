package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.pojo.Filter;
import edu.sampleproject.movielens.pojo.Movie;
import edu.sampleproject.movielens.pojo.MovieLight;
import edu.sampleproject.movielens.pojo.Review;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceImpl implements IService {

    @Override
    public Movie getMovieWithNReviews(String movieId, int n) {
        return null;
    }

    @Override
    public List<MovieLight> getLightMoviesBasedOnFilter(Filter filter, int start, int offset) {
        List<MovieLight> al= new ArrayList<>();
        al.add(new MovieLight());
        return al;
    }

    @Override
    public List<Review> getReviews(String movieId, int start, int offset) {
        return null;
    }


}
