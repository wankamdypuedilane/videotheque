package msi.videotheque.controller;

import jakarta.validation.Valid;
import msi.videotheque.model.Category;
import msi.videotheque.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") long id) {
        Optional<Category> category = service.findById(id);
        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(category.get());
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.ok().body(service.insert(category));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") long id,
                                                   @Valid @RequestBody Category category) {
        Category updated = service.update(id, category);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") long id) {
        Optional<Category> category = service.findById(id);
        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(category.get().getId());
        return ResponseEntity.accepted().build();
    }
}