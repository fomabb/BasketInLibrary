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
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o JOIN FETCH o.user u WHERE u.username =:username")
    Optional<Order> findOrderByUser_Username(@Param("username") String username);
}
