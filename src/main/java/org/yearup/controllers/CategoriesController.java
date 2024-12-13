package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.mysql.interfaces.CategoryDao;
import org.yearup.data.mysql.interfaces.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;


// add annotation to allow cross site origin requests
@RestController
@RequestMapping("/categories")
public class CategoriesController
{
    private CategoryDao categoryDao;
    private ProductDao productDao;

    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao){
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }



    @RequestMapping(path = "/categories", method = RequestMethod.GET)
    public List<Category> getAll() {
        return categoryDao.getAllCategories();
    }

    @RequestMapping(path = "/categories/{id}", method = RequestMethod.GET)
    public Category getById(@PathVariable int id) {
        return categoryDao.getById(id);
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("/category/{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId) {
        return productDao.listByCategoryId(categoryId);
    }

    @RequestMapping(path = "/category", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Category addCategory(@RequestBody Category category) {
        return categoryDao.create(category);
    }

    @RequestMapping(path = "/category/{id}", method = RequestMethod.PUT)
    // add annotation to ensure that only an ADMIN can call this function
    public void updateCategory(@PathVariable int id, @RequestBody Category category) {
        categoryDao.update(id, category);
    }


    @RequestMapping(path = "/category/{id}", method = RequestMethod.DELETE)
    // add annotation to ensure that only an ADMIN can call this function
    public void deleteCategory(@PathVariable int id) {
        categoryDao.delete(id);
    }
}
