package raf.vp.jmijatovic11421rn.services;

import raf.vp.jmijatovic11421rn.entities.Category;
import raf.vp.jmijatovic11421rn.repositories.category.CategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return this.categoryRepository.getAllCategories();
    }

    public List<Category> getCategoriesByPage(Integer page) {
        return this.categoryRepository.getCategoriesByPage(page);
    }

    public Category getCategory(String title) {
        return this.categoryRepository.getCategory(title);
    }

    public void editCategory(String title, Category category) {
        this.categoryRepository.editCategory(title, category);
    }

    public void addCategory(Category category) {
        this.categoryRepository.addCategory(category);
    }

    public void deleteCategory(String title) {
        this.categoryRepository.deleteCategory(title);
    }
}
