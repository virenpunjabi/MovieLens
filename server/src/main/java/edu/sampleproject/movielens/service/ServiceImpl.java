package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.dao.MovieDaoFactory;
import edu.sampleproject.movielens.dao.MovieWriterDao;
import edu.sampleproject.movielens.pojo.Filter;
import edu.sampleproject.movielens.pojo.Movie;
import edu.sampleproject.movielens.pojo.MovieLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ServiceImpl implements IService {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceImpl.class);

    @Autowired
    private MovieDaoFactory movieDaoFactory;

    @Autowired
    private MovieWriterDao movieWriterDao;

    @Override
    public List<MovieLight> getNLightMovies(int n) {
        try {
            return movieDaoFactory.getMovieDao().getNRecentMovies(n);
        } catch (IOException e) {
            LOG.warn("", e);
            return null;
        }
    }

    @Override
    public Movie getMovieWithNReviews(String movieId, int n) {
        try {
            return movieDaoFactory.getMovieDao().getMovie(movieId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MovieLight> getLightMoviesBasedOnFilter(Filter filter, int n) {
        return null;
    }

    @Override
    public void addMovie(Movie movie) {
        try {
            movieWriterDao.writeMovie(movie);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
