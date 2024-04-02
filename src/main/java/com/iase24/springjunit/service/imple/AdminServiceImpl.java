package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.entities.Faq;
import com.iase24.springjunit.repository.FaqRepository;
import com.iase24.springjunit.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final FaqRepository faqRepository;

    @Override
    public void answerForFaq(Long faqId, FaqAnswerDTO answer) {

        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new IllegalArgumentException("Faq id: " + faqId + " not found"));

        faq.setAnswer(answer.getAnswer());

        faqRepository.save(faq);
    }

    @Override
    public void deleteFaq(Long faqId) {
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new IllegalArgumentException("Faq id: " + faqId + " not found"));

        faqRepository.delete(faq);
    }
}
