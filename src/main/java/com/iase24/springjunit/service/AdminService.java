package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.entities.DescriptionCategory;

public interface AdminService {
    void answerForFaq(Long faqId, FaqAnswerDTO answer);

    void deleteFaq(Long categoryId, Long faqId);

    void createDescriptionCategory(DescriptionCategory descriptionCategory);
}
