package msi.videotheque.service;

import msi.videotheque.model.Category;
import msi.videotheque.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Category insert(Category category) {
        return repository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Optional<Category> existing = this.findById(id);
        if (existing.isPresent()) {
            Category toUpdate = existing.get();
            toUpdate.setName(category.getName());
            return repository.save(toUpdate);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Category> category = this.findById(id);
        if (category.isPresent()) {
            repository.delete(category.get());
        }
    }
}
