package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.dao.MovieDao;
import edu.sampleproject.movielens.dao.MovieWriterDao;
import edu.sampleproject.movielens.pojo.Filter;
import edu.sampleproject.movielens.pojo.Movie;
import edu.sampleproject.movielens.pojo.MovieLight;
import edu.sampleproject.movielens.pojo.Review;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ServiceImpl implements IService {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceImpl.class);

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private MovieWriterDao movieWriterDao;

    @Override
    public List<MovieLight> getNLightMovies(int n) {
        try {
            return movieDao.getNRecentMovies(n);
        } catch (IOException e) {
            LOG.warn("", e);
            return null;
        }
    }

    @Override
    public Movie getMovieWithNReviews(String movieId, int n) {
        try {
            return movieDao.getMovie(movieId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MovieLight> getLightMoviesBasedOnFilter(String filterRequest, int n) {
        Filter filter = generateFilter(filterRequest);
        BoolQueryBuilder queryBuilder = filter.getQuery();
        SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(queryBuilder);
        builder.from(0);
        builder.size(n);
        try {
            return movieDao.getLightMoviesUsingBuilder(builder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void addMovie(Movie movie) {
        try {
            movieWriterDao.writeMovie(movie);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Filter generateFilter(String filterRequest){
        //Language=Hindi|English&Genre=Action|Comedy
        Filter filter = new Filter();
        String[] filterOptions = filterRequest.split("&");
        for(String filterOption: filterOptions){

            int partition = filterOption.indexOf("=");
            String filterKey = filterOption.substring(0,partition);
            String[] filterValues = filterOption.substring(partition+1).split("\\|");

            filter.addQuery(filterKey, Arrays.asList(filterValues));
        }

        return filter;
    }

    public static void main(String[] args) {
        ServiceImpl service = new ServiceImpl();
        List<MovieLight> movieLights = service.getLightMoviesBasedOnFilter("Name=Dil Bechara",3);
    }

}
