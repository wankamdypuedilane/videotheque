package msi.videotheque.service;

import msi.videotheque.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category insert(Category category);
    Category update(Long id, Category category);
    void delete(Long id);
}
