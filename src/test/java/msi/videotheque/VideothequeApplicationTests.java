package msi.videotheque;

import msi.videotheque.controller.ActorController;
import msi.videotheque.controller.CategoryController;
import msi.videotheque.controller.FilmController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VideothequeApplicationTests {

    @Autowired
    private FilmController filmController;

    @Autowired
    private CategoryController categoryController;

    @Autowired
    private ActorController actorController;

    @Test
    void contextLoads() {
        assertThat(filmController).isNotNull();
        assertThat(categoryController).isNotNull();
        assertThat(actorController).isNotNull();
    }
}