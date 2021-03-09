package edu.sampleproject.movielens.service;

import edu.sampleproject.movielens.pojo.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActorController {

    IActorService service;

    @RequestMapping(method = RequestMethod.POST, value = "/actor")
    public void addActor(@RequestBody Actor actor) {
        service.addActor(actor);
    }

    @RequestMapping("/actor/{id}")
    public Actor getActor(@PathVariable String id) {
        return service.getActor(id);

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/actor/{id}")
    public void updateActor(@RequestBody Actor actor, @PathVariable String id) {

    }

}
