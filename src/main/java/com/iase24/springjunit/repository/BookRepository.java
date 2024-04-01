package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.DescriptionCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {

    @Query("select b from Book b where b.title ilike %:title%")
    List<Book> findBookByTitle(@Param("title") String title);

    @Query("select b from Book b where b.genre ilike %:genre%")
    List<Book> findAllByGenre(@Param("genre") String genre);

    @Query("select b from Book b where b.author ilike %:author%")
    List<Book> findAllByAuthor(@Param("author") String author);

    @Query(value =
            "select * from book b where make_tsvector(b.title, b.genre, b.author) @@ plainto_tsquery(?1)" +
                    "or similarity(b.title, ?1) > 0.6 " +
                    "or similarity(b.genre, ?1) > 0.6 " +
                    "or similarity(b.author, ?1) > 0.6 " +
                    "order by ts_rank(make_tsvector(b.title, b.genre, b.author), plainto_tsquery(?1)) " +
                    "desc",
            nativeQuery = true)
    List<Book> search(String text);

    @Query(value =
            "select b.* from book b join tree t on t.id = b.node_id where t.id=:categoryId"
            , nativeQuery = true)
    List<Book> findBooksChildCategoryId(@Param("categoryId") Long categoryId, PageRequest pageRequest);

    @Query(value =
            "select b.* from book b join tree t on t.id = b.node_id where parent_id=:categoryId"
            , nativeQuery = true)
    List<Book> findBooksParentCategoryId(@Param("categoryId") Long categoryId, PageRequest pageRequest);

    @Query("select dc from DescriptionCategory dc where dc.id=:categoryId")
    List<DescriptionCategory> findDescriptionCategory(Long categoryId);
}
