package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.pojo.Movie;
import edu.sampleproject.movielens.pojo.MovieLight;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("/")
public class HomePage {

    @GetMapping(path = "home/")
    public List<MovieLight> getNRecentMovies() {
        int n = 10;
        List<MovieLight> result = new ArrayList<MovieLight>();
        for(int i=1; i<=n; i++)
        result.add(new MovieLight("Id" + i, "Movie" + i, "Desc" + 1));
        return result;
    }

    @GetMapping(path = "home/{n}")
    public List<MovieLight> getNRecentMovies(@RequestParam("n") int n) {
//        int n = 10;
        List<MovieLight> result = new ArrayList<MovieLight>();
        for(int i=1; i<=n; i++)
            result.add(new MovieLight("Id" + i, "Movie" + i, "Desc" + 1));
        return result;
    }

}
