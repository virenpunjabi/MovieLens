package edu.sampleproject.movielens.dao;


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

import java.io.IOException;
import java.util.*;


public class MovieDao {
    private static MovieDao INSTANCE = new MovieDao();
    RestHighLevelClient client;

    public MovieDao() {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"),
                        new HttpHost("localhost", 9201, "http")));
    }

    public Movie getMovie(String movieId) throws IOException {
        GetRequest request = new GetRequest("movie");
        request.id(movieId);
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        Map<String, Object> responseMap = response.getSourceAsMap();

        Movie movie = getMovieFromMap(responseMap);
        movie.setId(movieId);
        return movie;
    }

    private Movie getMovieFromMap(Map jsonMap) {
        Movie movie = new Movie();
        movie.setName((String) jsonMap.get("Name"));
        movie.setShortDesc((String) jsonMap.get("Description"));
        movie.setCertification(Certification.getFromName(((String) jsonMap.get("Certification")).toUpperCase()));
        movie.setRuntime((Integer) jsonMap.get("Runtime"));
        movie.setReleaseDate((Date) jsonMap.get("ReleaseDate"));
        movie.setPosterUri((String) jsonMap.get("PosterURI"));
        movie.setTrailerLink((String) jsonMap.get("TrailerLink"));
        movie.setRating((Double) jsonMap.get("Rating"));
        if (jsonMap.get("Language") instanceof List) {
            List<Language> languages = new ArrayList<Language>();
            for (String language : (List<String>) jsonMap.get("Language")) {
                languages.add(Language.valueOf(language.toUpperCase()));
            }
            movie.setLanguages(languages);
        } else {
            List<Language> languages = new ArrayList<Language>();
            String language = (String) jsonMap.get("Language");
            languages.add(Language.valueOf(language.toUpperCase()));
            movie.setLanguages(languages);
        }

        if (jsonMap.get("Genres") instanceof List) {
            List<Genre> genres = new ArrayList<Genre>();
            for (String genre : (List<String>) jsonMap.get("Genres")) {
                genres.add(Genre.valueOf(genre.toUpperCase()));
            }
            movie.setGenres(genres);
        } else {
            List<Genre> genres = new ArrayList<Genre>();
            String genre = (String) jsonMap.get("Genres");
            genres.add(Genre.valueOf(genre.toUpperCase()));
            movie.setGenres(genres);
        }
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

    private MovieLight getLightMovieFromMap(Map<String, Object> jsonMap) {
        MovieLight movie = new MovieLight();
        movie.setName((String) jsonMap.get("Name"));
        movie.setPosterUri((String) jsonMap.get("PosterURI"));
        movie.setRating((Double) jsonMap.get("Rating"));
        movie.setCertification(Certification.getFromName(((String) jsonMap.get("Certification")).toUpperCase()));
        if (jsonMap.get("Genres") instanceof List) {
            List<Genre> genres = new ArrayList<Genre>();
            for (String genre : (List<String>) jsonMap.get("Genres")) {
                genres.add(Genre.valueOf(genre.toUpperCase()));
            }
            movie.setGenreList(genres);
        } else {
            List<Genre> genres = new ArrayList<Genre>();
            String genre = (String) jsonMap.get("Genres");
            genres.add(Genre.valueOf(genre.toUpperCase()));
            movie.setGenreList(genres);
        }
        return movie;
    }

    private List<Review> getNReviewsForMovie(String movieId, int start, int offset) throws IOException {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.matchQuery("MovieId", movieId));
        //TODO routing field
        SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(query);
        builder.from(start);
        builder.size(offset);
        SearchHit[] searchHits = getResponse(builder, "Review");
        List<Review> reviewList = new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            reviewList.add(getReviewsForMovieMap(searchHit.getSourceAsMap()));
        }
        return reviewList;
    }

    private Review getReviewsForMovieMap(Map<String, Object> jsonMap) {
        Review review = new Review();
        review.setComment((String) jsonMap.get("Comment"));
        review.setRating((Double) jsonMap.get("Rating"));
        review.setReviewDate((Date) jsonMap.get("Date"));
        review.setUserName((User) jsonMap.get("Username"));
        review.setReviewType(ReviewType.getFromName((String) jsonMap.get("ReviewType")));
        return review;
    }

//    private List<LightActor> getLightActorsUsingIds(List<String> actorIds) throws IOException {
//        BoolQueryBuilder query = QueryBuilders.boolQuery()
//                .filter(QueryBuilders.idsQuery().addIds((String[])actorIds.toArray()));//matchQuery("MovieId", movieId));
//        SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(query);
//        SearchHit[] searchHits = getResponse(builder, "actor");
//        String[] includedFields = new String[] {"ActorId","Name"};
//        String[] excludedFields = new String[0];
//        builder.fetchSource(includedFields, excludedFields);
//
//        List<LightActor> lightActorList = new ArrayList<>();
//        for (SearchHit searchHit : searchHits) {
//            lightActorList.add(getLightActorsFromMap(searchHit.getSourceAsMap()));
//        }
//        return lightActorList;
//    }

    private LightActor getLightActorsFromMap(Map<String, Object> jsonMap) {
        LightActor lightActor = new LightActor();
        lightActor.setId((String) jsonMap.get("ActorId"));
        lightActor.setName((String) jsonMap.get("Name"));
        return lightActor;
    }

    private Actor getActor(String actorId) throws IOException {
        GetRequest request = new GetRequest("actor");
        request.id(actorId);
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        Map<String, Object> responseMap = response.getSourceAsMap();

        Actor actor = getActorFromMap(responseMap);
        actor.setId(actorId);
        return actor;
    }

    private Actor getActorFromMap(Map<String, Object> jsonMap) throws IOException {
        List<String> movieIdList = new ArrayList<String>();
        Actor actor = new Actor();
        actor.setId((String) jsonMap.get("ActorId"));
        actor.setName((String) jsonMap.get("Name"));
        actor.setActorImageUri((String) jsonMap.get("ActorImageURI"));
        actor.setDob((Date) jsonMap.get("Date"));
        actor.setBirthPlace((String) jsonMap.get("BirthPlace"));
        actor.setDescription((String) jsonMap.get("Description"));
        if (jsonMap.get("Movies") instanceof List) {
            for (String movie : (List<String>) jsonMap.get("Movies")) {
                movieIdList.add(movie);
            }
        } else {
            String movie = (String) jsonMap.get("Movies");
            movieIdList.add(movie);
        }
        actor.setMovies(getLightMoviesForActor(movieIdList));

        return null;
    }

    private List<MovieLight> getLightMoviesForActor(List<String> movieIdList) throws IOException {
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.idsQuery().addIds((String[]) movieIdList.toArray()));
        SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(query);
        return getLightMoviesUsingBuilder(builder);
    }

    private List<MovieLight> getLightMoviesUsingBuilder(SearchSourceBuilder builder) throws IOException {
        String[] includedFields = new String[]{"Name", "Genres", "PosterURI", "Rating", "Certification"};
        String[] excludedFields = new String[0];
        builder.fetchSource(includedFields, excludedFields);
        SearchHit[] searchHits = getResponse(builder, "movie");
        List<MovieLight> movieList = new ArrayList<MovieLight>();
        for (SearchHit searchHit : searchHits) {
            movieList.add(getLightMovieFromMap(searchHit.getSourceAsMap()));
        }
        return movieList;
    }

}
