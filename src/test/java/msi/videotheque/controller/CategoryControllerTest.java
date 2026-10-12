package msi.videotheque.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import msi.videotheque.model.Category;
import msi.videotheque.service.CategoryService;
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

@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService service;

    private Category newCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }

    @DisplayName("Category list")
    @Test
    public void getAllCategoriesTest() throws Exception {
        // GIVEN
        when(service.findAll()).thenReturn(List.of(newCategory("Thriller")));

        // WHEN
        mockMvc.perform(get("/api/categories"))

                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Thriller"));
    }

    @DisplayName("Category not found")
    @Test
    public void getCategoryByIdNotFoundTest() throws Exception {
        // GIVEN
        when(service.findById(99L)).thenReturn(Optional.empty());

        // WHEN
        mockMvc.perform(get("/api/categories/99"))

                // THEN
                .andExpect(status().isNotFound());
    }

    @DisplayName("Category creation")
    @Test
    public void addCategoryTest() throws Exception {
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(newCategory("Thriller"));

        Category out = newCategory("Thriller");
        out.setId(1);
        when(service.insert(any(Category.class))).thenReturn(out);

        // WHEN
        mockMvc.perform(post("/api/categories")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))

                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Thriller"));
    }

    @DisplayName("Category creation with empty name is refused")
    @Test
    public void addCategoryInvalidTest() throws Exception {
        // GIVEN
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(newCategory(""));

        // WHEN
        mockMvc.perform(post("/api/categories")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))

                // THEN
                .andExpect(status().isBadRequest());
    }
}