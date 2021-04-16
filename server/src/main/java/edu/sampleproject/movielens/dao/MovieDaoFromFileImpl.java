package edu.sampleproject.movielens.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sampleproject.movielens.configurations.CommonConfig;
import edu.sampleproject.movielens.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class MovieDaoFromFileImpl implements MovieDao {
    @Autowired
    private CommonConfig commonConfig;

    @Autowired
    private MovieMapper movieMapper;

    @Override
    public Movie getMovie(String movieId) throws IOException {
        return null;
    }

    @Override
    public List<MovieLight> getNRecentMovies(int n) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode movies = mapper.readTree(new File("src/main/resources/data/movie.json"));
        List<MovieLight> result = new ArrayList<>();
        for(int i=0; i<movies.size(); i++) {
            Movie movie = mapper.readValue(movies.get(i).toString(), Movie.class);
            movie.setPosterUri(commonConfig.getImageURI() + movie.getPosterUri());
            result.add(movieMapper.map(movie));
        }
        return result;
    }

    @Override
    public List<Review> getNReviewsForMovie(String movieId, int start, int offset) throws IOException {
        return Collections.emptyList();
    }

    @Override
    public Actor getActor(String actorId) throws IOException {
        return null;
    }
}
