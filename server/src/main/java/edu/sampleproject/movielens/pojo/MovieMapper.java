package edu.sampleproject.movielens.pojo;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MovieMapper implements Mapper<Movie, MovieLight> {
    public MovieLight map(Movie movie) {
        return MovieLight.builder()
                .id(movie.getId())
                .name(movie.getName())
                .posterUri(movie.getPosterUri())
                .rating(movie.getTotalRating()/movie.getTotalReviewers())
                .genreList(movie.getGenres())
                .genres(String.join(" | ", movie.getGenres().stream().map(Genre::getGenreName).collect(Collectors.toList())))
                .languageList(movie.getLanguages())
                .languages(String.join(" | ", movie.getLanguages().stream().map(Language::getLanguageName).collect(Collectors.toList())))
                .build();
    }
}
