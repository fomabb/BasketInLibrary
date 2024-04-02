package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.FaqAnswerDTO;

public interface AdminService {
    void answerForFaq(Long faqId, FaqAnswerDTO answer);

    void deleteFaq(Long categoryId, Long faqId);
}
