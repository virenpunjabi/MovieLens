package edu.sampleproject.movielens.dao;

import edu.sampleproject.movielens.pojo.*;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Component
public class MovieWriterDao {
    Logger LOGGER = LoggerFactory.getLogger(MovieWriterDao.class);

    @Autowired
    private RestHighLevelClient client;

    public String writeMovie(Movie movie) throws IOException{
        List<LightActor> actors = movie.getActors();
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("Name", movie.getName())
                .field("Description", movie.getShortDesc())
                .field("Genres", getGenreNames(movie.getGenres()))
                .startArray("Actors");
                for(LightActor actor:actors)
                  builder =  builder.startObject().field("Id",actor.getId()).field("Name",actor.getName()).endObject();

                builder.endArray()
                .field("Certification",movie.getCertification().getCertificationName())
                .field("Runtime",movie.getRuntime())
                .field("MovieFormat",getMovieFormatNames(movie.getMovieFormat()))
                .field("Language",getLanguageNames(movie.getLanguages()))
                .field("ReleaseDate",movie.getReleaseDate())
                .field("TrailerLink",movie.getTrailerLink())
                .field("TotalRating",0)
                .field("TotalReviewers",0)
                .endObject();

        IndexRequest indexRequest = new IndexRequest("movie");
        indexRequest.source(builder);

        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        if(response.getShardInfo().getSuccessful() == 1)
            return response.getId();

        return "-1";
    }

    public String writeReview(Review review) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("MovieId",review.getMovieId())
                .field("Username",review.getUserName())
                .field("Comment",review.getComment())
                .field("ReviewDate",review.getReviewDate())
                .field("ReviewType",review.getReviewType().name())
                .field("Rating",review.getRating())
                .endObject();

        IndexRequest indexRequest = new IndexRequest("review");
        indexRequest.source(builder);
        indexRequest.routing(review.getMovieId());

        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

        if(response.getShardInfo().getSuccessful() == 1){

            UpdateRequest request = new UpdateRequest(
                    "movie",
                    review.getMovieId());

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("TotalRating",review.getRating());
            parameters.put("TotalReviewers",1);

            Script inline = new Script(ScriptType.INLINE, "painless",
                    "ctx._source.TotalRating += params.TotalRating; ctx._source.TotalReviewers += params.TotalReviewers", parameters);
            UpdateRequest updateRequest = request.script(inline);
            UpdateResponse updateResponse = client.update(updateRequest,RequestOptions.DEFAULT);

            return updateResponse.getShardInfo().getSuccessful() == 1 ? updateResponse.getId() : "-1";

        }

        return "-1";

    }

    public String writeActor(Actor actor) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("Name",actor.getName())
                .field("Dob",actor.getDob())
                .field("BirthPlace",actor.getBirthPlace())
                .field("Description",actor.getDescription())
                .endObject();

        IndexRequest indexRequest = new IndexRequest("actor");
        indexRequest.source(builder);

        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

        if(response.getShardInfo().getSuccessful() == 1)
            return response.getId();

        return "-1";

    }

    public boolean addMovieInActorDocument(String actorId, String movieId) throws IOException {
        UpdateRequest request = new UpdateRequest(
                "actor",
                actorId);
        String[] movies = new String[1];
        movies[0] = movieId;
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Movies",movieId);

        //if (!ctx._source.Movies) { ctx._source.Movies = [] }
        Script inline = new Script(ScriptType.INLINE, "painless",
                "if (ctx._source.Movies == null) { ctx._source.Movies = new ArrayList() } ctx._source.Movies.add(params.Movies)", parameters);
        UpdateRequest updateRequest = request.script(inline);
        UpdateResponse response = client.update(updateRequest,RequestOptions.DEFAULT);

        System.out.println("SSup");
        return response.getShardInfo().getSuccessful() == 1;
    }

    private static List<String> getLanguageNames(List<Language> languages) {
        List<String> stringLanguages = new ArrayList<String>();
        for(Language language: languages){
            stringLanguages.add(language.getLanguageName());
        }
        return stringLanguages;
    }

    private static List<String> getGenreNames(List<Genre> genres) {
        List<String> stringGenres = new ArrayList<String>();
        for(Genre genre: genres){
            stringGenres.add(genre.getGenreName());
        }
        return stringGenres;
    }

    private static List<String> getMovieFormatNames(List<MovieFormat> movieFormatList){
        List<String> stringMovies = new ArrayList<String>();
        for(MovieFormat movieFormat: movieFormatList){
            stringMovies.add(movieFormat.getFormatName());
        }
        return stringMovies;
    }

    public static String writeActor(LightActor actor){
        return null;
    }

    public static void main(String[] args) throws IOException {
//        Movie movie = new MovieDao().getMovie("apEaw3YBwD7mSNmlDH6E");
//        movie.setName("Dil Bahut Jyada Bechara 2");
//        LightActor actor=new LightActor();
//        actor.setName("Sushant Singh Rajput");
//        actor.setId("rltDIXcB0-ocmwxxgFc6");
//        LightActor actor2=new LightActor();
//        actor2.setName("Sanjana Sanghi");
//        actor2.setId("r1tGIXcB0-ocmwxxCVde");
//        List<LightActor> actorList = new ArrayList<LightActor>();
//        actorList.add(actor);
//        actorList.add(actor2);
//        movie.setActors(actorList);
//        List<MovieFormat> movieFormats = new ArrayList<MovieFormat>();
//        movieFormats.add(MovieFormat.TWOD);
//        movie.setMovieFormat(movieFormats);
//        new MovieWriterDao().writeMovie(movie);
        //new MovieWriterDao().testWriteReview();
        new MovieWriterDao().testAddMovieInActorDoc();
        //new MovieWriterDao().testAddActor();//MovieInActorDoc();
    }

    private void testAddActor() throws IOException {
        Actor actor = new Actor();
        actor.setName("Shraddha Kapoor");
        actor.setDob(LocalDate.of(1,1,1));
        actor.setBirthPlace("Mumbai");
        actor.setDescription("Desciptionnn");
        writeActor(actor);
    }

    private void testAddMovieInActorDoc() throws IOException {
        addMovieInActorDocument("uVuFMHcB0-ocmwxxslcv","aZFkuHYBwD7mSNmlL366");
    }

    private void testWriteReview() throws IOException {
        Review review= new Review();
        review.setMovieId("apEaw3YBwD7mSNmlDH6E");
        review.setUserName("Bob");
        review.setComment("This is very  heart touching and beautiful emotional movie of Sushant.U will always remain in our hearts forever with lots of love. I still can't believe that Sushant is no more with us. Lots and lots of love to you Sushant. U will always be missed");
        review.setReviewDate(new Date());
        review.setReviewType(ReviewType.GOOD);
        review.setRating(4.0);
        writeReview(review);
    }


}
