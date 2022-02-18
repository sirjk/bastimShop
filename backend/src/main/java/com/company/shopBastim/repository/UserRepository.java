package com.company.shopBastim.repository;

import com.company.shopBastim.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor {

    @Query(value = "SELECT * FROM users c WHERE c.email = ?1 AND  c.state != 'deleted'", nativeQuery = true)
    Optional<User> findUserByEmail(String email);


    public Page<User> findAll(Specification specification, Pageable pageable);

}
