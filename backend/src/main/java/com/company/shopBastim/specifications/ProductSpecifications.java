package com.company.shopBastim.specifications;


import com.company.shopBastim.model.Product;
import com.company.shopBastim.model.Product_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecifications {
    public static Specification<Product> likeName( String nameProvided){
        if(nameProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
           return criteriaBuilder.like(root.get(Product_.NAME), "%" + nameProvided + "%");
        });
    }
    public static Specification<Product> likeManufacturer( String nameProvided){
        if(nameProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(Product_.MANUFACTURER), "%" + nameProvided + "%");
        });
    }
    public static Specification<Product> likeSpecification( String nameProvided){
        if(nameProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(Product_.SPECIFICATION), "%" + nameProvided + "%");
        });
    }

    public static Specification<Product> inCategory(List<Long> categoryIds){
        if(categoryIds.isEmpty()){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return root.get(Product_.CATEGORY_ID).in(categoryIds);
        });
    }
    public static Specification<Product> gePrice(Float priceProvided){
        if(priceProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.ge(root.get(Product_.PRICE), priceProvided);
        });
    }
    public static Specification<Product> lePrice(Float priceProvided){
        if(priceProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.le(root.get(Product_.PRICE), priceProvided);
        });
    }

    public static Specification<Product> isInStock(Boolean inStock){
        if(inStock == null){
            return null;
        }
        if(inStock == true){
            return ((root, query, criteriaBuilder) -> {
                return criteriaBuilder.ge(root.get(Product_.QUANTITY_IN_STOCK), 1);
            });
        }
        else{
            return ((root, query, criteriaBuilder) -> {
                return criteriaBuilder.le(root.get(Product_.QUANTITY_IN_STOCK), 0);
            });
        }
    }

}
