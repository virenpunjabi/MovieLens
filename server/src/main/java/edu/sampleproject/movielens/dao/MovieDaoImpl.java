package edu.sampleproject.movielens.dao;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sampleproject.movielens.configurations.CommonConfig;
import edu.sampleproject.movielens.pojo.*;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Component
@Lazy
public class MovieDaoImpl implements MovieDao {
    Logger LOGGER = LoggerFactory.getLogger(MovieWriterDao.class);

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private CommonConfig commonConfig;

    @Override
    public Movie getMovie(String movieId) throws IOException {
        GetRequest request = new GetRequest("movie");
        request.id(movieId);
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        ObjectMapper mapper = new ObjectMapper();
        Movie movie = mapper.readValue(response.getSourceAsString(), Movie.class);
        movie.setId(movieId);
        return movie;
    }

    public List<MovieLight> getNRecentMovies(int n) throws IOException {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(n);
        builder.sort(new FieldSortBuilder("ReleaseDate").order(SortOrder.DESC));
        return getLightMoviesUsingBuilder(builder);
    }

    private SearchHit[] getResponse(SearchSourceBuilder builder, String indice) throws IOException {
        SearchRequest request = new SearchRequest(indice);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] searchHits = response.getHits().getHits();
        return searchHits;
    }

    public List<Review> getNReviewsForMovie(String movieId, int start, int offset) throws IOException {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.matchQuery("MovieId", movieId));
        //TODO routing field
        SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(query);
        builder.from(start);
        builder.size(offset);
        SearchHit[] searchHits = getResponse(builder, "Review");
        List<Review> reviewList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (SearchHit searchHit : searchHits) {
            reviewList.add(mapper.readValue(searchHit.getSourceAsString(), Review.class));
        }
        return reviewList;
    }

    public Actor getActor(String actorId) throws IOException {
        GetRequest request = new GetRequest("actor");
        request.id(actorId);
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        Actor actor = new ObjectMapper().readValue(response.getSourceAsString(), Actor.class);
        actor.setId(actorId);
        return actor;
    }

    private List<MovieLight> getLightMoviesForActor(List<String> movieIdList) throws IOException {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.idsQuery().addIds((String[]) movieIdList.toArray()));
        SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(query);
        return getLightMoviesUsingBuilder(builder);
    }

    private List<MovieLight> getLightMoviesUsingBuilder(SearchSourceBuilder builder) throws IOException {
        String[] includedFields = new String[]{"Name", "Genres", "PosterURI", "Rating", "Language"};
        String[] excludedFields = new String[0];
        builder.fetchSource(includedFields, excludedFields);
        SearchHit[] searchHits = getResponse(builder, "movie");
        List<MovieLight> movieList = new ArrayList<MovieLight>();
        ObjectMapper mapper = new ObjectMapper();
        for (SearchHit searchHit : searchHits) {
            MovieLight movieLight = mapper.readValue(searchHit.getSourceAsString(), MovieLight.class);
            movieLight.setId(searchHit.getId());
            movieLight.setPosterUri(commonConfig.getImageURI() + searchHit.getId() + ".jpg");
            movieList.add(movieLight);
        }
        return movieList;
    }

}
