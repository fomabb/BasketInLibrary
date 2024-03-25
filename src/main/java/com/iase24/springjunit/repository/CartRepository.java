package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c JOIN FETCH c.user u WHERE u.login = :login")
    Optional<Cart> findByUserLogin(@Param("login") String login);
}
