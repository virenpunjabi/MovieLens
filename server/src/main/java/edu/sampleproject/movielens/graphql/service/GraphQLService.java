package edu.sampleproject.movielens.graphql.service;

import edu.sampleproject.movielens.dao.MovieDaoFactory;
import edu.sampleproject.movielens.pojo.*;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GraphQLService {

    @Autowired
    private MovieDaoFactory movieDaoFactory;

    public DataFetcher<Movie> getMovie() {
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            Movie movie = new Movie();
            movie.setId("1");
            movie.setName("3 idiots");
            //movie.setRating(2.0);
            Actor actor = new Actor();
            actor.setName("ABC");
            Actor actor1 = new Actor();
            actor1.setName("XYZ");
            List<Actor> actorList = new ArrayList<>();
            actorList.add(actor);
            actorList.add(actor1);
            //movie.setActors(actorList);
            movie.setCertification(Certification.ADULTS);
            movie.setTrailerLink("new ");
            LocalDate date = LocalDate.of(2020, 1, 8);
            movie.setReleaseDate(date);
            return movie;

        };
    }

    public DataFetcher<List<MovieLight>> getNRecentMovies() {
        return dataFetchingEnvironment -> {
            int n = dataFetchingEnvironment.getArgument("n");
            MovieLight movieLight = new MovieLight();
            movieLight.setId("1");
            movieLight.setName("3 idiots");
            movieLight.setRuntime(123);
            movieLight.setRating(3.0);
            MovieLight movieLight1 = new MovieLight();
            movieLight1.setId("2");
            movieLight1.setName("MSD");
            movieLight1.setRuntime(145);
            movieLight1.setRating(4.0);
            List<MovieLight> lights = new ArrayList<>();
            lights.add(movieLight);
            lights.add(movieLight1);
            return lights;

        };
    }

    public DataFetcher<List<Review>> getNReviewsForMovie() {
        return dataFetchingEnvironment -> {
            String movieId = dataFetchingEnvironment.getArgument("id");
            int start = dataFetchingEnvironment.getArgument("start");
            int offset = dataFetchingEnvironment.getArgument("offset");
            return movieDaoFactory.getMovieDao().getNReviewsForMovie(movieId, start, offset);
        };
    }

}
