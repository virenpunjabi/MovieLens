package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.pojo.Movie;
import edu.sampleproject.movielens.pojo.MovieLight;
import edu.sampleproject.movielens.pojo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomePage {

    @Autowired
    private IService service;


    @GetMapping(path = "/home")
    public List<MovieLight> getNRecentMovies() {
        return service.getLightMoviesBasedOnFilter(null,0,0);
//        List<MovieLight> result = new ArrayList<MovieLight>();
//        return result;
    }

    //@GetMapping(path = "home/{n}")
    @RequestMapping("/home/{id}")
    public Movie getMovie(@PathVariable String id) {
        return new Movie();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/home")
    public void addMovie(@RequestBody Movie movie) {

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/home/{id}")
    public void updateMovie(@RequestBody Movie movie, @PathVariable String id) {

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/home/{id}")
    public void deleteMovie(@PathVariable String id){

    }


}
