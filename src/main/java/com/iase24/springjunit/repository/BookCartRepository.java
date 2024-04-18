package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BookCartRepository extends JpaRepository<BookCart, Long> {

    List<BookCart> findAllByCart_Id(Long cartId);

    @Query(value =
            "select * from book_cart bc join book b on bc.book_id = b.id where cart_id=:cartId"
            , nativeQuery = true)
    List<BookCart> findAllByCart_IdaAndAndBook(Long cartId);
}
