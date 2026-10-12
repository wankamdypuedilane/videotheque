package msi.videotheque.repository;

import msi.videotheque.model.Category;
import msi.videotheque.model.Film;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FilmRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FilmRepository repository;

    @DisplayName("find films by category")
    @Test
    public void findByCategoryIdTest() {
        // GIVEN
        Category thriller = new Category();
        thriller.setName("Thriller");
        entityManager.persist(thriller);

        Category comedie = new Category();
        comedie.setName("Comedie");
        entityManager.persist(comedie);

        Film nikita = new Film();
        nikita.setTitle("Nikita");
        nikita.setYear(1990);
        nikita.setReleaseDate(LocalDate.of(1990, 2, 21));
        nikita.setDuration(118);
        nikita.setCategory(thriller);
        entityManager.persist(nikita);

        Film rrrr = new Film();
        rrrr.setTitle("RRRrrrr");
        rrrr.setYear(2004);
        rrrr.setReleaseDate(LocalDate.of(2004, 5, 1));
        rrrr.setDuration(90);
        rrrr.setCategory(comedie);
        entityManager.persist(rrrr);

        // WHEN
        List<Film> result = repository.findByCategoryId(thriller.getId());

        // THEN
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Nikita");
    }
}