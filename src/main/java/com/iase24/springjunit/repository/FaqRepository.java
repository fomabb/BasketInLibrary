package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FaqRepository extends JpaRepository<Faq, Long> {

    @Query("select f from Faq f where f.descriptionCategory=:categoryId")
    Faq findByDescriptionCategory_Id(@Param("categoryId") DescriptionCategory categoryId);
}
