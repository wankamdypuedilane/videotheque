package msi.videotheque.service;

import msi.videotheque.model.Actor;
import java.util.List;
import java.util.Optional;

public interface ActorService {
    List<Actor> findAll();
    Optional<Actor> findById(Long id);
    Actor insert(Actor actor);
    Actor update(Long id, Actor actor);
    void delete(Long id);
}
