package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Product;
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
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    @Query(value = """
            select * from products p where make_tsvector_book_product(p.title, p.genre, p.author) @@ plainto_tsquery(?1)
            or similarity(p.title, ?1) > 0.3
            or title % ?1
            or similarity(p.genre, ?1) > 0.1
            or similarity(p.author, ?1) > 0.6
            order by ts_rank(make_tsvector_book_product(p.title, p.genre, p.author), plainto_tsquery(?1)) desc
            """, nativeQuery = true)
    List<Product> search(String text);

    @Query(value =
            "select b.* from products b join tree t on t.id = b.node_id where t.id=:categoryId"
            , nativeQuery = true)
    List<Product> findBooksChildCategoryId(@Param("categoryId") Long categoryId, PageRequest pageRequest);

    @Query(value =
            "select b.* from products b join tree t on t.id = b.node_id where parent_id=:categoryId"
            , nativeQuery = true)
    List<Product> findBooksParentCategoryId(@Param("categoryId") Long categoryId, PageRequest pageRequest);

    @Query("select dc from DescriptionCategory dc where dc.id=:categoryId")
    List<DescriptionCategory> findDescriptionCategory(Long categoryId);

    List<Product> findBooksByProductCartsId(Long basketId);

    @Query("select b from Product b where b.genre=:category")
    List<Product> findBooksByCategoryName(String category);

    @Query("select b from Product b join ProductOrder bc on b.id=bc.id and bc.statusDeliveryId=2 where bc.id=:categoryId")
    List<Product> findBookByCategoryId(Long categoryId);
}