package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Order, Long> {

    @Query("SELECT c FROM Order c JOIN FETCH c.user u WHERE u.username =:username")
    Optional<Order> findCartByUser_Username(@Param("username") String username);
}
