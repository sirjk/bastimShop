package com.company.shopBastim.controller;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Category;
import com.company.shopBastim.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryServiceArg){
        categoryService = categoryServiceArg;
    }

    @GetMapping
    public ResponseEntity<Page<Category>> getAllCategories(@RequestParam Map<String, String> params) {
        return new ResponseEntity<Page<Category>>(categoryService.getCategories(params),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try{
            return  new ResponseEntity<Category>(categoryService.getCategoryById(id), HttpStatus.OK);
        }
        catch(Exception exception) {
            if(exception.getClass() == DoesNotExistException.class)
                return  new ResponseEntity<String>("Category with provided id does not exist", HttpStatus.OK);
            else
                return  new ResponseEntity<String>("It shuouldnt occur", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<String> postCategory(@RequestBody List<Category> categories){
        if(categories.size() >= 1){
            return categoryService.postCategories(categories);
        }
        else{
            return new ResponseEntity<String>("Not enough categories.", HttpStatus.NOT_ACCEPTABLE);
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){ return categoryService.deleteCategory(id);}

    @PutMapping("/{id}")
    public ResponseEntity<String> putCategory(@PathVariable Long id, @RequestBody Category category){ return categoryService.putCategory(id, category);}
}
