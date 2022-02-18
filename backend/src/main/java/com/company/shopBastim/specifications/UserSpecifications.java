package com.company.shopBastim.specifications;

import com.company.shopBastim.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class UserSpecifications {
    public static Specification<User> likeFirstName(String firstNameProvided) {
        if (firstNameProvided == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(User_.FIRST_NAME), "%" + firstNameProvided + "%");
        });
    }

    public static Specification<User> likeLastName(String lastNameProvided) {
        if (lastNameProvided == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(User_.LAST_NAME), "%" + lastNameProvided + "%");
        });
    }

    public static Specification<User> likeEmail(String emailProvided) {
        if (emailProvided == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(User_.EMAIL), "%" + emailProvided + "%");
        });
    }

    public static Specification<User> geBirthDate(LocalDate birthDateProvided){
        if(birthDateProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(User_.BIRTH_DATE), birthDateProvided);
        });
    }
    public static Specification<User> leBirthDate(LocalDate birthDateProvided){
        if(birthDateProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get(User_.BIRTH_DATE), birthDateProvided);
        });
    }

    public static Specification<User> gePoints(Integer pointsProvided){
        if(pointsProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.le(root.get(User_.POINTS), pointsProvided);
        });
    }
    public static Specification<User> lePoints(Integer pointsProvided){
        if(pointsProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.ge(root.get(User_.POINTS), pointsProvided);
        });
    }

    public static Specification<User> likePassword(String passwordProvided){
        if(passwordProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(User_.PASSWORD), "%" + passwordProvided + "%");
        });
    }

    public static Specification<User> likeCountry(String countryProvided){
        if(countryProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(User_.COUNTRY), "%" + countryProvided + "%");
        });
    }

    public static Specification<User> likeCity(String cityProvided){
        if(cityProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(User_.CITY), "%" + cityProvided + "%");
        });
    }

    public static Specification<User> likeAddress(String addressProvided){
        if(addressProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(User_.ADDRESS), "%" + addressProvided + "%");
        });
    }

    public static Specification<User> likePostalAddress(String postalAddressProvided){
        if(postalAddressProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(User_.POSTAL_ADDRESS), "%" + postalAddressProvided + "%");
        });
    }

    public static Specification<User> hasRoles(String postalAddressProvided){
        if(postalAddressProvided == null){
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(User_.ROLES), "%" + postalAddressProvided + "%");
        });
    }



    //====== Turaj trzeba zrobic metode ktora bedzie wyszukiwała userów, ktorzy posiadaja wprowadzoną kolekcje ról========
    //static Specification<User> hasRoles(List<Integer> roleIds) {
        //return (root, query, cb) -> {
            //query.distinct(true);
            //Root<User> user = root;
            ////Root<Role> role = query.from(Role.class);
            //Expression<Collection<Role>> userRoles = user.get("roles");//
            //
            //return cb.and(cb.in(), cb.isMember(cat, ownerCats));
        //};
    //}

}