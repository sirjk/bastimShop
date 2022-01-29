package com.company.shopBastim.specifications;

import com.company.shopBastim.model.Permission;
import com.company.shopBastim.model.Permission_;
import org.springframework.data.jpa.domain.Specification;

public class PermissionSpecifications {

    public static Specification<Permission> likePermissionName(String nameProvided){
        if(nameProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(Permission_.NAME), "%" + nameProvided + "%");
        });
    }
}
