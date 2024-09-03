package raf.vp.jmijatovic11421rn.repositories.category;

import raf.vp.jmijatovic11421rn.entities.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> getAllCategories();

    List<Category> getCategoriesByPage(Integer page);

    Category getCategory(String title);

    void editCategory(String title, Category category);

    void addCategory(Category category);

    void deleteCategory(String title);
}
