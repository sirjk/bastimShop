package com.company.shopBastim.specifications;

import com.company.shopBastim.model.Role;
import com.company.shopBastim.model.Role_;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecifications {
    public static Specification<Role> likeName(String nameProvided){
        if(nameProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(Role_.ROLE_NAME), "%" + nameProvided + "%");
        });
    }
}
