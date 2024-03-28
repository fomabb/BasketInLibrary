package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface NodeRepository extends JpaRepository<Node, Long> {
}
