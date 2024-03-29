package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.BookCart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BookCartRepository extends JpaRepository<BookCart, Long> {


    List<BookCart> findAllByCart_Id(Long cartId);
}
