package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.dao.MovieDao;
import edu.sampleproject.movielens.dao.MovieWriterDao;
import edu.sampleproject.movielens.pojo.Review;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements IReviewService {

    //@Autowired
    private MovieDao movieDao = new MovieDao();

    //@Autowired
    private MovieWriterDao movieWriterDao = new MovieWriterDao();

    @SneakyThrows
    @Override
    public List<Review> getReviews(String movieId, int start, int offset) {
        return movieDao.getNReviewsForMovie(movieId,start,offset);
    }

    @SneakyThrows
    @Override
    public void addReview(Review review, String movieId) {
        review.setMovieId(movieId);
        movieWriterDao.writeReview(review);
    }

    @Override
    public void deleteReview(String reviewId) {

    }
}
