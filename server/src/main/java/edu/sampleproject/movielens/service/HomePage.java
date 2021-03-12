package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.pojo.Movie;
import edu.sampleproject.movielens.pojo.MovieLight;
import edu.sampleproject.movielens.pojo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
public class HomePage {

    @Autowired
    private IService service;


    @GetMapping(path = "/home")
    public List<MovieLight> getNRecentMovies() {
        return Collections.singletonList(new MovieLight("id", "name", null, null, null,null, null));
//        return service.getNLightMovies(2);
    }


    //@GetMapping(path = "home/{n}")
    @RequestMapping("/home/{id}")
    public Movie getMovie(@PathVariable String id) {

        return service.getMovieWithNReviews(id, 10);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/home")
    public void addMovie(@RequestBody Movie movie) {
        service.addMovie(movie);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/home/{id}")
    public void updateMovie(@RequestBody Movie movie, @PathVariable String id) {

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/home/{id}")
    public void deleteMovie(@PathVariable String id){

    }


}
