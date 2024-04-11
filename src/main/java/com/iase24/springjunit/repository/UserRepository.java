package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);

//    TODO
    @Query(value =
            "select * from cart c join users ut on ut.id = c.id where c.id=:id"
            , nativeQuery = true)
    Optional<User> findUserByIdAndCart(@Param("id") Long id);

    Optional<User> findUserByUsername(String username);
}
