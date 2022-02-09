package com.company.shopBastim.repository;

import com.company.shopBastim.model.Category;
import com.company.shopBastim.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor {

    @Query(value = "SELECT * FROM orders o WHERE o.user_id = ?1", nativeQuery = true)
    Page<Order> findUserOrder(Long userId, Specification specification, Pageable pageable);

    Page<Order> findAll(Specification specification, Pageable pageable);


}
