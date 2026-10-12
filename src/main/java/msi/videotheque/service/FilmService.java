package msi.videotheque.service;

import msi.videotheque.model.Film;
import java.util.List;
import java.util.Optional;

public interface FilmService {
    List<Film> findAll();
    Optional<Film> findById(Long id);
    List<Film> findByCategory(Long categoryId);
    Film insert(Film film);
    Film update(Long id, Film film);
    void delete(Long id);
}