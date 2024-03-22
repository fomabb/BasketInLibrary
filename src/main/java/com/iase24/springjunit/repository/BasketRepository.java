package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
