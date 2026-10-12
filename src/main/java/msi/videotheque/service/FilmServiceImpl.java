package msi.videotheque.service;

import msi.videotheque.model.Film;
import msi.videotheque.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmRepository repository;

    @Override
    public List<Film> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Film> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Film> findByCategory(Long categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    @Override
    public Film insert(Film film) {
        return repository.save(film);
    }

    @Override
    public Film update(Long id, Film film) {
        Optional<Film> existing = this.findById(id);
        if (existing.isPresent()) {
            Film toUpdate = existing.get();
            toUpdate.setTitle(film.getTitle());
            toUpdate.setYear(film.getYear());
            toUpdate.setDescription(film.getDescription());
            toUpdate.setReleaseDate(film.getReleaseDate());
            toUpdate.setDuration(film.getDuration());
            toUpdate.setCategory(film.getCategory());
            toUpdate.setActors(film.getActors());
            return repository.save(toUpdate);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Film> film = this.findById(id);
        if (film.isPresent()) {
            repository.delete(film.get());
        }
    }
}