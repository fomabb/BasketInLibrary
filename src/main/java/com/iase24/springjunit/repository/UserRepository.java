package com.iase24.springjunit.repository;

import com.iase24.springjunit.dto.UserDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);
}
