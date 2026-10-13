package msi.videotheque.repository;

import msi.videotheque.model.Category;
import msi.videotheque.model.Film;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FilmRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FilmRepository repository;

    @Test
    public void findByCategoryIdTest() {
        // GIVEN
        Category thriller = new Category();
        thriller.setName("Thriller");
        entityManager.persist(thriller);

        Film nikita = new Film();
        nikita.setTitle("Nikita");
        nikita.setDuration(118);
        nikita.setCategory(thriller);
        entityManager.persist(nikita);

        // WHEN
        List<Film> result = repository.findByCategoryId(thriller.getId());

        // THEN
        assertThat(result.get(0).getTitle()).isEqualTo("Nikita");
    }
}