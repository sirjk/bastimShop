package com.company.shopBastim.repository;

import com.company.shopBastim.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>, JpaSpecificationExecutor {

    Page<Category> findAll(Specification specification, Pageable pageable);

    @Query(value = "SELECT * FROM categories c WHERE c.parent_id = ?1", nativeQuery = true)
    Optional<List<Category>> findCategoriesByParentId(Long id);


}
