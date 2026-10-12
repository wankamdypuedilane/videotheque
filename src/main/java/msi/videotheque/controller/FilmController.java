package msi.videotheque.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import msi.videotheque.model.Film;
import msi.videotheque.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Tag(name = "Films", description = "CRUD des films et films par catégorie")
@RestController
@RequestMapping("/api")
public class FilmController {

    @Autowired
    private FilmService service;

    @Operation(summary = "Liste de tous les films")
    @GetMapping("/films")
    public ResponseEntity<List<Film>> getAllFilms() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @Operation(summary = "Un film par son id (404 s'il n'existe pas)")
    @GetMapping("/films/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable("id") long id) {
        Optional<Film> film = service.findById(id);
        if (film.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(film.get());
    }

    @Operation(summary = "Films d'une catégorie")
    @GetMapping("/categories/{id}/films")
    public ResponseEntity<List<Film>> getFilmsByCategory(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(service.findByCategory(id));
    }

    @Operation(summary = "Créer un film (400 si données invalides ou film déjà existant)")
    @PostMapping("/films")
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film) {
        return ResponseEntity.ok().body(service.insert(film));
    }

    @Operation(summary = "Modifier un film (404 s'il n'existe pas)")
    @PutMapping("/films/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable("id") long id,
                                           @Valid @RequestBody Film film) {
        Film updated = service.update(id, film);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updated);
    }

    @Operation(summary = "Supprimer un film (404 s'il n'existe pas)")
    @DeleteMapping("/films/{id}")
    public ResponseEntity<Film> deleteFilm(@PathVariable("id") long id) {
        Optional<Film> film = service.findById(id);
        if (film.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(film.get().getId());
        return ResponseEntity.accepted().build();
    }
}