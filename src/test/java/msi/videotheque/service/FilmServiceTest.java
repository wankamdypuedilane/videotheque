package msi.videotheque.service;

import msi.videotheque.model.Film;
import msi.videotheque.repository.FilmRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilmServiceTest {

    @Mock
    private FilmRepository repository;

    @InjectMocks
    private FilmServiceImpl service;

    private Film newFilm(String title) {
        Film film = new Film();
        film.setTitle(title);
        return film;
    }

    @DisplayName("find all films")
    @Test
    public void findAllTest() {
        // GIVEN
        List<Film> input = new ArrayList<>();
        input.add(newFilm("Nikita"));
        input.add(newFilm("Leon"));
        input.add(newFilm("Subway"));
        when(repository.findAll()).thenReturn(input);

        // WHEN
        List<Film> output = service.findAll();

        // THEN
        assertThat(output.size()).isEqualTo(3);
    }

    @DisplayName("find films by category")
    @Test
    public void findByCategoryTest() {
        // GIVEN
        List<Film> input = List.of(newFilm("Nikita"));
        when(repository.findByCategoryId(1L)).thenReturn(input);

        // WHEN
        List<Film> output = service.findByCategory(1L);

        // THEN
        assertThat(output.size()).isEqualTo(1);
        assertThat(output.get(0).getTitle()).isEqualTo("Nikita");
    }

    @DisplayName("update an existing film")
    @Test
    public void updateTest() {
        // GIVEN
        Film existing = newFilm("Nikita");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Film.class))).thenAnswer(inv -> inv.getArgument(0));

        Film changes = newFilm("Nikita (remaster)");

        // WHEN
        Film result = service.update(1L, changes);

        // THEN
        assertThat(result.getTitle()).isEqualTo("Nikita (remaster)");
    }

    @DisplayName("update an unknown film returns null")
    @Test
    public void updateUnknownTest() {
        // GIVEN
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // WHEN
        Film result = service.update(99L, newFilm("Inconnu"));

        // THEN
        assertThat(result).isNull();
    }

    @DisplayName("delete an existing film")
    @Test
    public void deleteTest() {
        // GIVEN
        Film existing = newFilm("Nikita");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        // WHEN
        service.delete(1L);

        // THEN
        verify(repository).delete(existing);
    }
}