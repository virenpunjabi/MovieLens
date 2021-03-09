package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.pojo.Actor;

public interface IActorService {

    void addActor(Actor actor);

    Actor getActor(String id);

    void updateActor(Actor actor, String id);

}
