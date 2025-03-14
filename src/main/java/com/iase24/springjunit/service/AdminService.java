package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.DescriptionDataDTO;
import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.entities.Faq;

import java.util.List;

public interface AdminService {
    void answerForFaq(Long faqId, FaqAnswerDTO answer);

    void deleteFaq(Long categoryId, Long faqId);

    List<Faq> findAllFaqIsQuestionNotRead();

    void updateUserRolesByUsername(Long userId);

    void createDescriptionByCategoryName(String categoryName, DescriptionDataDTO descriptionCategory);
}
