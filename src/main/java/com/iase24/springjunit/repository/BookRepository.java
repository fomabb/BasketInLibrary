package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.BookCart;
import com.iase24.springjunit.entities.DescriptionCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {

    @Query(value =
            "select * from book b where make_tsvector(b.title, b.genre, b.author) @@ plainto_tsquery(?1)" +
                    "or similarity(b.title, ?1) > 0.3 " +
                    "or similarity(b.genre, ?1) > 0.1 " +
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

    List<Book> findBooksByBookBasketsId(Long basketId);

    @Query("select b from Book b where b.genre=:category")
    List<Book> findBooksByCategoryName(String category);

    @Query("select b from Book b join BookCart bc on b.id=bc.id where bc.id=:categoryId")
    Book findBookByCategoryId(Long categoryId);
}