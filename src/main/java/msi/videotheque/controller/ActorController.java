package msi.videotheque.controller;

import jakarta.validation.Valid;
import msi.videotheque.model.Actor;
import msi.videotheque.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ActorController {

    @Autowired
    private ActorService service;

    @GetMapping("/actors")
    public ResponseEntity<List<Actor>> getAllActors() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/actors/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable("id") long id) {
        Optional<Actor> actor = service.findById(id);
        if (actor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(actor.get());
    }

    @PostMapping("/actors")
    public ResponseEntity<Actor> addActor(@Valid @RequestBody Actor actor) {
        return ResponseEntity.ok().body(service.insert(actor));
    }

    @PutMapping("/actors/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable("id") long id,
                                             @Valid @RequestBody Actor actor) {
        Actor updated = service.update(id, actor);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/actors/{id}")
    public ResponseEntity<Actor> deleteActor(@PathVariable("id") long id) {
        Optional<Actor> actor = service.findById(id);
        if (actor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(actor.get().getId());
        return ResponseEntity.accepted().build();
    }
}