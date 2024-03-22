package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Book;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {

//"UPDATE books SET book_count=?, book_status=? WHERE book_id=?";

    @Query("select b from Book b where b.status='ACTIVE'")
    @NonNull
    List<Book> findAll();

    @Query("select b from Book b where b.status='INACTIVE'")
    List<Book> findAll(String inActive);

    @Query(value =
            ""
//            "select * from book where basket_id=:id"
            , nativeQuery = true)
    List<Book> getBasketById(Long id);
}
