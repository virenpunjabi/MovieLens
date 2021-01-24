package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.pojo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    IService service;

    @RequestMapping("/home/{id}/reviews/{startNo}")
    public List<Review> getReviews(@PathVariable String movieId, @PathVariable String startNo) {
        return null;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/home/{id}/reviews")
    public void addReview(@RequestBody Review review, @PathVariable String movieId) {

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/home/{id}/reviews/{reviewId}")
    public void deleteReview(@PathVariable String reviewId){

    }

}
