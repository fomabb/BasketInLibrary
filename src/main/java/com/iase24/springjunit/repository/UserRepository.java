package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
