package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
