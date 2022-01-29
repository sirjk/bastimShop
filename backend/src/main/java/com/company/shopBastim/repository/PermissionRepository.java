package com.company.shopBastim.repository;

import com.company.shopBastim.model.Permission;
import com.company.shopBastim.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long>, JpaSpecificationExecutor {
    @Query(value = "SELECT * FROM permissions p WHERE p.name = ?1", nativeQuery = true)
    Optional<Permission> findRolePermissionByName(String name);

    public Page<Permission> findAll(Specification specification, Pageable pageable);
}
