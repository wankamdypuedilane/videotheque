package msi.videotheque.service;

import msi.videotheque.model.Actor;
import msi.videotheque.repository.ActorRepository;
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
public class ActorServiceTest {

    @Mock
    private ActorRepository repository;

    @InjectMocks
    private ActorServiceImpl service;

    private Actor newActor(String name, String bio) {
        Actor actor = new Actor();
        actor.setName(name);
        actor.setBio(bio);
        return actor;
    }

    @DisplayName("find all actors")
    @Test
    public void findAllTest() {
        // GIVEN
        List<Actor> input = new ArrayList<>();
        input.add(newActor("Jean Reno", "Acteur"));
        input.add(newActor("Natalie Portman", "Actrice"));
        when(repository.findAll()).thenReturn(input);

        // WHEN
        List<Actor> output = service.findAll();

        // THEN
        assertThat(output.size()).isEqualTo(2);
    }

    @DisplayName("update an existing actor")
    @Test
    public void updateTest() {
        // GIVEN
        Actor existing = newActor("Jean Reno", "Acteur");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Actor.class))).thenAnswer(inv -> inv.getArgument(0));

        // WHEN
        Actor result = service.update(1L, newActor("Jean Reno", "Acteur francais"));

        // THEN
        assertThat(result.getBio()).isEqualTo("Acteur francais");
    }

    @DisplayName("update an unknown actor returns null")
    @Test
    public void updateUnknownTest() {
        // GIVEN
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // WHEN
        Actor result = service.update(99L, newActor("Inconnu", "Bio"));

        // THEN
        assertThat(result).isNull();
    }

    @DisplayName("delete an existing actor")
    @Test
    public void deleteTest() {
        // GIVEN
        Actor existing = newActor("Jean Reno", "Acteur");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        // WHEN
        service.delete(1L);

        // THEN
        verify(repository).delete(existing);
    }
}