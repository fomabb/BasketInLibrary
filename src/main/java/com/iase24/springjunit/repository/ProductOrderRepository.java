package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.ProductOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    List<ProductOrder> findAllByOrder_Id(Long cartId);

    @Query(value =
            "select * from products_carts pc join products p on pc.product_id = p.id where cart_id=:cartId"
            , nativeQuery = true)
    List<ProductOrder> findAllByCart_IdaAndAndBook(Long cartId);
}
