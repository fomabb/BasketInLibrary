package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Faq;

import java.util.List;

public interface AdminService {
    void answerForFaq(Long faqId, FaqAnswerDTO answer);

    void deleteFaq(Long categoryId, Long faqId);

    void createDescriptionCategory(DescriptionCategory descriptionCategory);

    List<Faq> findAllFaqIsQuestionNotRead();
}
