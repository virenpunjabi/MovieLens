package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.dao.MovieDao;
import edu.sampleproject.movielens.dao.MovieWriterDao;
import edu.sampleproject.movielens.pojo.Actor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ActorServiceImpl implements IActorService {
    private static final Logger LOG = LoggerFactory.getLogger(ActorServiceImpl.class);

    //@Autowired
    MovieDao movieDao = MovieDao.getInstance();

    //@Autowired
    MovieWriterDao movieWriterDao = new MovieWriterDao();

    @Override
    public void addActor(Actor actor) {
        try {
            movieWriterDao.writeActor(actor);
        } catch (IOException e) {
            LOG.warn("Could not save actor.", e);
        }
    }

    @Override
    public Actor getActor(String id) {
        try {
            return movieDao.getActor(id);
        } catch (IOException e) {
            LOG.warn("Could not get actor. id=" + id, e);
        }
        return null;
    }

    @Override
    public void updateActor(Actor actor, String id) {

    }
}
