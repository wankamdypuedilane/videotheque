package msi.videotheque.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import msi.videotheque.model.Actor;
import msi.videotheque.service.ActorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ActorController.class)
public class ActorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorService service;

    private Actor newActor(String name, String bio) {
        Actor actor = new Actor();
        actor.setName(name);
        actor.setBio(bio);
        return actor;
    }

    @DisplayName("Actor list")
    @Test
    public void getAllActorsTest() throws Exception {
        // GIVEN
        when(service.findAll()).thenReturn(List.of(newActor("Jean Reno", "Acteur")));

        // WHEN
        mockMvc.perform(get("/api/actors"))

                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Jean Reno"));
    }

    @DisplayName("Actor not found")
    @Test
    public void getActorByIdNotFoundTest() throws Exception {
        // GIVEN
        when(service.findById(99L)).thenReturn(Optional.empty());

        // WHEN
        mockMvc.perform(get("/api/actors/99"))

                // THEN
                .andExpect(status().isNotFound());
    }

    @DisplayName("Actor creation")
    @Test
    public void addActorTest() throws Exception {
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(newActor("Jean Reno", "Acteur"));

        Actor out = newActor("Jean Reno", "Acteur");
        out.setId(1);
        when(service.insert(any(Actor.class))).thenReturn(out);

        // WHEN
        mockMvc.perform(post("/api/actors")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))

                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jean Reno"));
    }

    @DisplayName("Actor creation with empty name is refused")
    @Test
    public void addActorInvalidTest() throws Exception {
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(newActor("", "Acteur"));

        // WHEN
        mockMvc.perform(post("/api/actors")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))

                // THEN
                .andExpect(status().isBadRequest());
    }
}