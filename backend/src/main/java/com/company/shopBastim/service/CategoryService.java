package com.company.shopBastim.service;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Category;
import com.company.shopBastim.repository.CategoryRepository;
import com.company.shopBastim.specifications.CategorySpecifications;
import com.company.shopBastim.specifications.ProductSpecifications;
import com.company.shopBastim.utility.PrepareQueryForDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepositoryArg){
        categoryRepository = categoryRepositoryArg;
    }

    public Page<Category> getCategories(Map<String, String> params) {

        PrepareQueryForDatabase PQFD = new PrepareQueryForDatabase();
        Pageable pageable = PQFD.setupPegableFromParams(params);

        // ========================================== FILTERS SETUP ==========================================
        Specification<Category> spec = null;

        if(params.get("search-phrase") != null){
            spec = CategorySpecifications.likeName(params.get("search-phrase"));
        }
        if(params.get("parent-id") != null){

            List<Long> idsProvided = new ArrayList<>();
            String[] temp = params.get("parent-id").split(",");
            for(String oneId: temp){
                try {
                    idsProvided.add(Long.parseLong(oneId));
                }
                catch (Exception ignored){

                }
            }
            if(!idsProvided.isEmpty()) {
                if (spec == null) {
                    spec = CategorySpecifications.eqParentIds(idsProvided);
                } else {
                    spec = spec.and(CategorySpecifications.eqParentIds(idsProvided));
                }
            }
        }
        // ========================================== END OF FILTERS SETUP ==========================================

        return categoryRepository.findAll(spec , pageable);
    }

    public Category getCategoryById(Long id) throws DoesNotExistException {
        Optional<Category> output = categoryRepository.findById(id);
        if(output.isEmpty()){

            throw new DoesNotExistException("Category");

        }
        return output.get();
    }


    public ResponseEntity<String> postCategories(List<Category> categories) {

        String response = "";
        List<Category> toBeSaved = new ArrayList<Category>();
        List<Category> allCategories = categoryRepository.findAll();

        for(Category category : categories){ //sprawdzanie czy w danej hierarchii kategorii istnieje identyczna kategora. jesli tak - nie mozna jej dodac
            Boolean wrongCategoryFlag = false;
            for(Category allCategory : allCategories){
                if(allCategory.getName().equals(category.getName()) && allCategory.getParentId()==category.getParentId()){
                    response += "Cannot add existing category in this hierarchy (category name: "+ category.getName()+").\n";
                    wrongCategoryFlag = true;
                }
            }
            if(category.getName()==null){
                response += "Category name cannot be null\n";
                wrongCategoryFlag = true;
            }
                if(!wrongCategoryFlag){
                response += "Ok. \n";
                toBeSaved.add(category);
            }
        }

        if (!response.contains("Ok")){
            return new ResponseEntity<String>(response, HttpStatus.NOT_ACCEPTABLE);
        }
        else {
            categoryRepository.saveAll(toBeSaved);
            return new ResponseEntity<String>(response, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<String> deleteCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isPresent()){
            categoryRepository.deleteById(id);
            return new ResponseEntity<String>("Deleted.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Not deleted. Category with provided ID not found", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<String> putCategory(Long id, Category category) {
        category.setId(id);
        if(category.getName()==null){
            return new ResponseEntity<String>("Cannot put - category name cannot be null", HttpStatus.NOT_ACCEPTABLE);
        }
        else{
            categoryRepository.save(category);
            return new ResponseEntity<String>("Put.", HttpStatus.OK);
        }
    }
}

