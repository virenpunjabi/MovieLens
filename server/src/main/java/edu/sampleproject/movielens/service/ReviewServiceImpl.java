package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.dao.MovieDao;
import edu.sampleproject.movielens.dao.MovieWriterDao;
import edu.sampleproject.movielens.pojo.Review;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewServiceImpl implements IReviewService {

    //@Autowired
    private MovieDao movieDao = new MovieDao();

    //@Autowired
    private MovieWriterDao movieWriterDao = new MovieWriterDao();

    @Override
    public List<Review> getReviews(String movieId, int start, int offset) {
        try {
            return movieDao.getNReviewsForMovie(movieId,start,offset);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void addReview(Review review, String movieId) {
        review.setMovieId(movieId);
        try {
            movieWriterDao.writeReview(review);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReview(String reviewId) {

    }
}
