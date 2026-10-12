package msi.videotheque.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import msi.videotheque.model.Film;
import msi.videotheque.service.FilmService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FilmController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService service;

    private Film newFilm(String title) {
        Film film = new Film();
        film.setTitle(title);
        film.setYear(1990);
        film.setReleaseDate(LocalDate.of(1990, 2, 21));
        film.setDuration(118);
        return film;
    }

    @DisplayName("Film list")
    @Test
    public void getAllFilmsTest() throws Exception {
        // GIVEN
        when(service.findAll()).thenReturn(List.of(newFilm("Nikita")));

        // WHEN
        mockMvc.perform(get("/api/films"))

                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Nikita"));
    }

    @DisplayName("Film not found")
    @Test
    public void getFilmByIdNotFoundTest() throws Exception {
        // GIVEN
        when(service.findById(99L)).thenReturn(Optional.empty());

        // WHEN
        mockMvc.perform(get("/api/films/99"))

                // THEN
                .andExpect(status().isNotFound());
    }

    @DisplayName("Film creation")
    @Test
    public void addFilmTest() throws Exception {
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(newFilm("Nikita"));

        Film out = newFilm("Nikita");
        out.setId(1);
        when(service.insert(any(Film.class))).thenReturn(out);

        // WHEN
        mockMvc.perform(post("/api/films")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))

                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Nikita"));
    }

    @DisplayName("Film creation with empty title is refused")
    @Test
    public void addFilmInvalidTest() throws Exception {
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(newFilm(""));

        // WHEN
        mockMvc.perform(post("/api/films")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))

                // THEN
                .andExpect(status().isBadRequest());
    }
}