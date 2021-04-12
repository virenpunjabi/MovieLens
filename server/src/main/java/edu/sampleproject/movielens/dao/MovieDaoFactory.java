package edu.sampleproject.movielens.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class MovieDaoFactory {
    @Autowired
    @Lazy
    private MovieDaoImpl dbImpl;

    @Autowired
    @Lazy
    private MovieDaoFromFileImpl fileImpl;

    @Value("${load.data.from.file:false}")
    private boolean loadDataFromFile;

    public MovieDao getMovieDao() {
        return loadDataFromFile ? fileImpl : dbImpl;
    }
}
