package com.company.shopBastim.service;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Order;
import com.company.shopBastim.model.Product;
import com.company.shopBastim.repository.CategoryRepository;
import com.company.shopBastim.repository.OrderRepository;
import com.company.shopBastim.repository.ProductRepository;
import com.company.shopBastim.specifications.ProductSpecifications;
import com.company.shopBastim.utility.PrepareQueryForDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepositoryArg, OrderRepository orderRepositoryArg, CategoryRepository categoryRepositoryArg){
        productRepository = productRepositoryArg;
        orderRepository = orderRepositoryArg;
        categoryRepository = categoryRepositoryArg;
    }

    public Page<Product> getProducts(Map<String,String> params) {


        PrepareQueryForDatabase PQFD =  new PrepareQueryForDatabase();
        Pageable pagingType = PQFD.setupPegableFromParams(params);


        // ========================================== FILTERS SETUP ==========================================
        Specification<Product> spec = null;

        if(params.get("search_phrase") != null){

            spec = ProductSpecifications.likeName(params.get("search_phrase"))
                    .or(ProductSpecifications.likeManufacturer(params.get("search_phrase")))
                    .or(ProductSpecifications.likeSpecification(params.get("search_phrase")));
        }
        if(params.get("category") != null){
            List<Long> allCategoryIds = new ArrayList<Long>();
            List<Long> childrenIds = new ArrayList<>(); //allCategoryIdsThatChildrenOfNeedsToBeFound
            childrenIds.add( Long.parseLong(params.get("category")) );
            for(;!childrenIds.isEmpty();){
                allCategoryIds.add(childrenIds.get(childrenIds.size()-1));
                List<Long> temp = (List<Long>) categoryRepository
                        .findCategoriesByParentId(childrenIds.get(childrenIds.size()-1)).get()
                        .stream().map(cat -> cat.getId()).collect(Collectors.toList());
                childrenIds.remove(childrenIds.size()-1);

                for(Long catId: temp){
                    if(catId != null)
                        childrenIds.add(catId);
                }

            }

            if(spec == null){
                spec = ProductSpecifications.inCategory(allCategoryIds);
            }
            else{
                spec = spec.and(ProductSpecifications.inCategory(allCategoryIds));
            }

        }
        if(params.get("min-price") != null){
            if(spec == null){
                spec = ProductSpecifications.gePrice(Float.parseFloat(params.get("min-price")));
            }
            else{
                spec = spec.and(ProductSpecifications.gePrice(Float.parseFloat(params.get("min-price"))));
            }

        }
        if(params.get("max-price") != null){
            if(spec == null){
                spec = ProductSpecifications.lePrice(Float.parseFloat(params.get("max-price")));
            }
            else{
                spec = spec.and(ProductSpecifications.lePrice(Float.parseFloat(params.get("max-price"))));
            }

        }
        if(params.get("present-in-stock") != null){

            Boolean temp = Boolean.parseBoolean(params.get("present-in-stock"));
            if (spec == null && temp != null) {
                spec = ProductSpecifications.isInStock(temp);
            } else if(spec != null && temp != null) {
                spec = spec.and(ProductSpecifications.isInStock(temp));
            }

        }
        // ========================================== END OF FILTERS SETUP ==========================================

        return productRepository.findAll(spec, pagingType);
    }

    public Product getProductById(Long id) throws DoesNotExistException{
        Optional<Product> output = productRepository.findById(id);
        if(output.isEmpty()){
            throw new DoesNotExistException("Product");
        }
        return output.get();
    }

    public ResponseEntity<String> postProducts(List<Product> products) {
        String response = "";

        //Sprawdzanie czy istnieje kategoria do której chce sie dodać produkt
        List<Product> productsToBeSaved = new ArrayList<>();
        for(Product product :products){
            if(!categoryRepository.findAll().stream().map(v -> v.getId()).collect(Collectors.toSet()).contains(product.getCategoryId()))
            {
                response+= "Category: " + product.getCategoryId() + " does not exist. Product not added\n";
            }
            else{
                productsToBeSaved.add(product);
            }
        }
        if(productsToBeSaved.isEmpty()){
            return new ResponseEntity<String>(response + "No products added.", HttpStatus.NOT_ACCEPTABLE);
        }
        if(response.equals("")){
            response += "Products successfully added.";
        }
        else{
            response += "Rest of the prodcuts succesfully added.";
        }

        productRepository.saveAll(productsToBeSaved);
        return new ResponseEntity<String>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        //Sprawdzanie czy nie ma produktu w jakims orderze
        List<Order> tempListOfOrders = orderRepository.findAll();
        for(Order order : tempListOfOrders){
            if(order.getProductMap().containsKey(id)){
                return new ResponseEntity<String>("Not deleted. Product used in order: " + order.getId() +"\n", HttpStatus.NOT_ACCEPTABLE);
            }
        }


        if(productOptional.isPresent()){
            productRepository.deleteById(id);
            return new ResponseEntity<String>("Deleted.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Not deleted. Product with provided ID not found", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<String> putProduct(Long id, Product product) {
        product.setId(id);
        productRepository.save(product);
        return new ResponseEntity<String>("Put.", HttpStatus.OK);
    }
}
