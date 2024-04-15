package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {

    List<Faq> findAllByAnswerIsNull();
}
