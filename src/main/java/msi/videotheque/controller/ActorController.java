package msi.videotheque.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import msi.videotheque.model.Actor;
import msi.videotheque.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Tag(name = "Acteurs", description = "CRUD des acteurs")
@RestController
@RequestMapping("/api")
public class ActorController {

    @Autowired
    private ActorService service;

    @Operation(summary = "Liste de tous les acteurs")
    @GetMapping("/actors")
    public ResponseEntity<List<Actor>> getAllActors() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @Operation(summary = "Un acteur par son id (404 s'il n'existe pas)")
    @GetMapping("/actors/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable("id") long id) {
        Optional<Actor> actor = service.findById(id);
        if (actor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(actor.get());
    }

    @Operation(summary = "Créer un acteur (400 si données invalides)")
    @PostMapping("/actors")
    public ResponseEntity<Actor> addActor(@Valid @RequestBody Actor actor) {
        return ResponseEntity.ok().body(service.insert(actor));
    }

    @Operation(summary = "Modifier un acteur (404 s'il n'existe pas)")
    @PutMapping("/actors/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable("id") long id,
                                             @Valid @RequestBody Actor actor) {
        Actor updated = service.update(id, actor);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updated);
    }

    @Operation(summary = "Supprimer un acteur (404 s'il n'existe pas)")
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