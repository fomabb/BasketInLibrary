package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.title ilike %:title%")
    List<Book> findBookByTitle(@Param("title") String title);

    @Query("select b from Book b where b.genre ilike %:genre%")
    List<Book> findAllByGenre(@Param("genre") String genre);

    @Query("select b from Book b where b.author ilike %:author%")
    List<Book> findAllByAuthor(@Param("author") String author);
}
