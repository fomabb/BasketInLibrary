package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Cart, Long> {
}
