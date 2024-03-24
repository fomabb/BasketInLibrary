package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
