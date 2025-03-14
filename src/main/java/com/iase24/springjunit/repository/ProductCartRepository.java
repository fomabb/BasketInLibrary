package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.entities.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart, Long> {

    Optional<ProductCart> findByCartAndProduct(Cart cart, Product product);

    /**
     * Находим по корзине пользователя все книги в его корзине, проводим расчёт общей суммы за все товары в корзине
     *
     * @return float count price by all products
     */
    @Query("select sum(pc.priceQuantity) from ProductCart pc where pc.cart=:cart")
    float findBooksByBasket(Cart cart);
}
