package com.company.shopBastim.specifications;

import com.company.shopBastim.model.Category;
import com.company.shopBastim.model.Category_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class CategorySpecifications {
    public static Specification<Category> likeName(String nameProvided){
        if(nameProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(Category_.NAME), "%" + nameProvided + "%");
        });
    }

    public static Specification<Category> eqParentIds(List<Long> idsProvided){
        if(idsProvided.isEmpty()){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return root.get(Category_.PARENT_ID).in(idsProvided);
        });
    }
}
