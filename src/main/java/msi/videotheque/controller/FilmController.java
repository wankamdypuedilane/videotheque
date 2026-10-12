package msi.videotheque.controller;

import jakarta.validation.Valid;
import msi.videotheque.model.Film;
import msi.videotheque.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FilmController {

    @Autowired
    private FilmService service;

    @GetMapping("/films")
    public ResponseEntity<List<Film>> getAllFilms() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/films/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable("id") long id) {
        Optional<Film> film = service.findById(id);
        if (film.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(film.get());
    }

    @GetMapping("/categories/{id}/films")
    public ResponseEntity<List<Film>> getFilmsByCategory(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(service.findByCategory(id));
    }

    @PostMapping("/films")
    public ResponseEntity<Film> addFilm(@Valid @RequestBody Film film) {
        return ResponseEntity.ok().body(service.insert(film));
    }

    @PutMapping("/films/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable("id") long id,
                                           @Valid @RequestBody Film film) {
        Film updated = service.update(id, film);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updated);
    }

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