package msi.videotheque.service;

import msi.videotheque.model.Category;
import msi.videotheque.repository.CategoryRepository;
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
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryServiceImpl service;

    private Category newCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }

    @DisplayName("find all categories")
    @Test
    public void findAllTest() {
        // GIVEN
        List<Category> input = new ArrayList<>();
        input.add(newCategory("Thriller"));
        input.add(newCategory("Comedie"));
        input.add(newCategory("Drame"));
        when(repository.findAll()).thenReturn(input);

        // WHEN
        List<Category> output = service.findAll();

        // THEN
        assertThat(output.size()).isEqualTo(3);
    }

    @DisplayName("update an existing category")
    @Test
    public void updateTest() {
        // GIVEN
        Category existing = newCategory("Thriller");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

        // WHEN
        Category result = service.update(1L, newCategory("Policier"));

        // THEN
        assertThat(result.getName()).isEqualTo("Policier");
    }

    @DisplayName("update an unknown category returns null")
    @Test
    public void updateUnknownTest() {
        // GIVEN
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // WHEN
        Category result = service.update(99L, newCategory("Inconnue"));

        // THEN
        assertThat(result).isNull();
    }

    @DisplayName("delete an existing category")
    @Test
    public void deleteTest() {
        // GIVEN
        Category existing = newCategory("Thriller");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        // WHEN
        service.delete(1L);

        // THEN
        verify(repository).delete(existing);
    }
}