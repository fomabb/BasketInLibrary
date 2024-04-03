package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Faq;
import com.iase24.springjunit.repository.DescriptionCategoryRepository;
import com.iase24.springjunit.repository.FaqRepository;
import com.iase24.springjunit.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final FaqRepository faqRepository;
    private final DescriptionCategoryRepository descriptionCategoryRepository;

    @Override
    public void answerForFaq(Long faqId, FaqAnswerDTO answer) {

        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new IllegalArgumentException("Faq id: " + faqId + " not found"));

        faq.setAnswer(answer.getAnswer());

        faqRepository.save(faq);
    }

    @Override
    public void deleteFaq(Long categoryId, Long faqId) {

        DescriptionCategory category = descriptionCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category id not found"));
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new IllegalArgumentException("Faq id not found"));

        if (category.getFaq().remove(faq)) {
            descriptionCategoryRepository.save(category);
        } else {
            throw new IllegalArgumentException("Faq in description category not found");
        }
    }

    @Override
    public void createDescriptionCategory(DescriptionCategory descriptionCategory) {

        descriptionCategoryRepository.save(descriptionCategory);
    }
}
