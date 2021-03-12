package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.dao.MovieDao;
import edu.sampleproject.movielens.dao.MovieWriterDao;
import edu.sampleproject.movielens.pojo.Actor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorServiceImpl implements IActorService {

    //@Autowired
    MovieDao movieDao = new MovieDao();

    //@Autowired
    MovieWriterDao movieWriterDao = new MovieWriterDao();

    @SneakyThrows
    @Override
    public void addActor(Actor actor) {
        movieWriterDao.writeActor(actor);
    }

    @SneakyThrows
    @Override
    public Actor getActor(String id) {
        return movieDao.getActor(id);
    }

    @Override
    public void updateActor(Actor actor, String id) {

    }
}
