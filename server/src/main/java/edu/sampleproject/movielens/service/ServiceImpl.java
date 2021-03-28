package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.dao.MovieDao;
import edu.sampleproject.movielens.dao.MovieWriterDao;
import edu.sampleproject.movielens.pojo.Filter;
import edu.sampleproject.movielens.pojo.Movie;
import edu.sampleproject.movielens.pojo.MovieLight;
import edu.sampleproject.movielens.pojo.Review;
import lombok.SneakyThrows;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
    public List<MovieLight> getLightMoviesBasedOnFilter(String filterRequest, int n) {
        Filter filter = generateFilter(filterRequest);
        BoolQueryBuilder queryBuilder = filter.getQuery();
        SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(queryBuilder);
        builder.from(0);
        builder.size(n);
        return movieDao.getLightMoviesUsingBuilder(builder);
    }

    @SneakyThrows
    @Override
    public void addMovie(Movie movie) {
        movieWriterDao.writeMovie(movie);
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
