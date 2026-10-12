package msi.videotheque.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import msi.videotheque.model.Category;
import msi.videotheque.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Tag(name = "Catégories", description = "CRUD des catégories de films")
@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @Operation(summary = "Liste de toutes les catégories")
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @Operation(summary = "Une catégorie par son id (404 si elle n'existe pas)")
    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") long id) {
        Optional<Category> category = service.findById(id);
        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(category.get());
    }

    @Operation(summary = "Créer une catégorie (400 si nom invalide ou déjà existant)")
    @PostMapping("/categories")
    public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.ok().body(service.insert(category));
    }

    @Operation(summary = "Modifier une catégorie (404 si elle n'existe pas)")
    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") long id,
                                                   @Valid @RequestBody Category category) {
        Category updated = service.update(id, category);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updated);
    }

    @Operation(summary = "Supprimer une catégorie (404 si elle n'existe pas)")
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