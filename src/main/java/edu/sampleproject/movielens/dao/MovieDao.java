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
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MovieDao {
    private static MovieDao INSTANCE = new MovieDao();
    RestHighLevelClient client;

    public MovieDao(){
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
        if(jsonMap.get("Language") instanceof List){
            List<Language> languages = new ArrayList<Language>();
            for(String language : (List<String>)jsonMap.get("Language")){
                languages.add(Language.valueOf(language.toUpperCase()));
            }
            movie.setLanguages(languages);
        }else{
            List<Language> languages = new ArrayList<Language>();
            String language = (String)jsonMap.get("Language");
            languages.add(Language.valueOf(language.toUpperCase()));
            movie.setLanguages(languages);
        }

        if(jsonMap.get("Genres") instanceof List){
            List<Genre> genres = new ArrayList<Genre>();
            for(String genre : (List<String>)jsonMap.get("Genres")){
                genres.add(Genre.valueOf(genre.toUpperCase()));
            }
            movie.setGenres(genres);
        }else{
            List<Genre> genres = new ArrayList<Genre>();
            String genre = (String)jsonMap.get("Genres");
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
        String[] includedFields = new String[] {"Name", "Genres", "PosterURI","Rating","Certification"};
        String[] excludedFields = new String[0];
        builder.fetchSource(includedFields, excludedFields);
        SearchRequest request = new SearchRequest("movie");
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] searchHits = response.getHits().getHits();
        List<MovieLight> nLightMovies = new ArrayList<MovieLight>();
        for(SearchHit searchHit : searchHits){
            nLightMovies.add(getLightMovieFromMap(searchHit.getSourceAsMap()));
        }
        return nLightMovies;
    }

    private MovieLight getLightMovieFromMap(Map<String, Object> jsonMap) {
        MovieLight movie = new MovieLight();
        movie.setName((String) jsonMap.get("Name"));
        movie.setPosterUri((String) jsonMap.get("PosterURI"));
        movie.setRating((Double) jsonMap.get("Rating"));
        movie.setCertification(Certification.getFromName(((String) jsonMap.get("Certification")).toUpperCase()));
        if(jsonMap.get("Genres") instanceof List){
            List<Genre> genres = new ArrayList<Genre>();
            for(String genre : (List<String>)jsonMap.get("Genres")){
                genres.add(Genre.valueOf(genre.toUpperCase()));
            }
            movie.setGenreList(genres);
        }else{
            List<Genre> genres = new ArrayList<Genre>();
            String genre = (String)jsonMap.get("Genres");
            genres.add(Genre.valueOf(genre.toUpperCase()));
            movie.setGenreList(genres);
        }
        return movie;
    }

    public static void main(String[] args) {
        MovieDao movieDao = new MovieDao();
        try {
            movieDao.getMovie("aZFkuHYBwD7mSNmlL366");
            movieDao.getNRecentMovies(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
