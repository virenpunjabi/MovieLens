package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.dao.MovieDao;
import edu.sampleproject.movielens.dao.MovieWriterDao;
import edu.sampleproject.movielens.pojo.Filter;
import edu.sampleproject.movielens.pojo.Movie;
import edu.sampleproject.movielens.pojo.MovieLight;
import edu.sampleproject.movielens.pojo.Review;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceImpl implements IService {

    //@Autowired
    private MovieDao movieDao = new MovieDao();

    //@Autowired
    private MovieWriterDao movieWriterDao = new MovieWriterDao();

    @SneakyThrows
    @Override
    public List<MovieLight> getNLightMovies(int n) {
        return movieDao.getNRecentMovies(n);
    }

    @SneakyThrows
    @Override
    public Movie getMovieWithNReviews(String movieId, int n) {
        return movieDao.getMovie(movieId);
    }

    @SneakyThrows
    @Override
    public List<MovieLight> getLightMoviesBasedOnFilter(Filter filter, int n) {
        return null;
    }

    @SneakyThrows
    @Override
    public void addMovie(Movie movie) {
        movieWriterDao.writeMovie(movie);
    }


}
