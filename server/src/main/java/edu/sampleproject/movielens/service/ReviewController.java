package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.pojo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    IReviewService service;

    @RequestMapping("/home/{id}/reviews/{startNo}")
    public List<Review> getReviews(@PathVariable String movieId, @PathVariable int startNo) {
        return service.getReviews(movieId,startNo,5);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/home/{id}/reviews")
    public void addReview(@RequestBody Review review, @PathVariable String movieId) {
        service.addReview(review, movieId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/home/{id}/reviews/{reviewId}")
    public void deleteReview(@PathVariable String reviewId){

    }

}
