package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.pojo.Review;

import java.util.List;

public interface IReviewService {

    List<Review> getReviews(String movieId, int start, int offset);

    void addReview(Review review,String movieId);

    void deleteReview(String reviewId);
}
