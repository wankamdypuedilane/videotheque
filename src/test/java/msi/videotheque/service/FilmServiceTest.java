package msi.videotheque.service;

import msi.videotheque.model.Film;
import msi.videotheque.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilmServiceTest {

    @Mock
    private FilmRepository repository;

    @InjectMocks
    private FilmServiceImpl service;

    @Test
    public void findAllTest() {
        // GIVEN
        Film nikita = new Film();
        nikita.setTitle("Nikita");
        Film leon = new Film();
        leon.setTitle("Leon");
        when(repository.findAll()).thenReturn(List.of(nikita, leon));

        // WHEN
        List<Film> result = service.findAll();

        // THEN
        assertThat(result.size()).isEqualTo(2);
    }
}