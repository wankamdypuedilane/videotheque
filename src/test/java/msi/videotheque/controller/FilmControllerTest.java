package msi.videotheque.controller;

import msi.videotheque.model.Film;
import msi.videotheque.service.FilmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FilmController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService service;

    @Test
    public void getAllFilmsTest() throws Exception {
        // GIVEN
        Film nikita = new Film();
        nikita.setTitle("Nikita");
        when(service.findAll()).thenReturn(List.of(nikita));

        // WHEN
        mockMvc.perform(get("/api/films"))

                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Nikita"));
    }
}