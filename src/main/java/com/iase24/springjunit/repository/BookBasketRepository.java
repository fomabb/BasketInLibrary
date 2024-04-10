package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.BookBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookBasketRepository extends JpaRepository<BookBasket, Long> {
}
