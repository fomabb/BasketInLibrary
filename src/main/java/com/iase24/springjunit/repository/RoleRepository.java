package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Role;
import jakarta.persistence.SequenceGenerators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String username);

    @Query("select r from Role r where r.name='ROLE_ADMIN'")
    Role findRoleByRoleAdmin();
}
