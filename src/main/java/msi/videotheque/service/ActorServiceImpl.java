package msi.videotheque.service;

import msi.videotheque.model.Actor;
import msi.videotheque.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorRepository repository;

    @Override
    public List<Actor> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Actor> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Actor insert(Actor actor) {
        return repository.save(actor);
    }

    @Override
    public Actor update(Long id, Actor actor) {
        Optional<Actor> existing = this.findById(id);
        if (existing.isPresent()) {
            Actor toUpdate = existing.get();
            toUpdate.setName(actor.getName());
            if (actor.getBio() != null) {
                toUpdate.setBio(actor.getBio());
            }
            return repository.save(toUpdate);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Actor> actor = this.findById(id);
        if (actor.isPresent()) {
            repository.delete(actor.get());
        }
    }
}
