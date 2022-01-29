package com.company.shopBastim.controller;

import com.company.shopBastim.exceptions.DoesNotExistException;
import com.company.shopBastim.model.Product;
import com.company.shopBastim.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productServiceArg){
        this.productService = productServiceArg;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam Map<String, String> params) {
        return new ResponseEntity<Page<Product>>(productService.getProducts(params),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try{
            return  new ResponseEntity<Product>(productService.getProductById(id), HttpStatus.OK);
        }
        catch(Exception exception) {
            if(exception.getClass() == DoesNotExistException.class)
                return  new ResponseEntity<String>("Product with provided id does not exist", HttpStatus.NOT_ACCEPTABLE);
            else
                return  new ResponseEntity<String>("It shuouldnt occur", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<String> postProduct(@RequestBody List<Product> products){
        if(products.size() >= 1){
            return productService.postProducts(products);
        }
        else{
            return new ResponseEntity<String>("Not enough customers.", HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){ return productService.deleteProduct(id);}

    @PutMapping("/{id}")
    public ResponseEntity<String> putProduct(@PathVariable Long id, @RequestBody Product product){ return productService.putProduct(id, product);}


}
