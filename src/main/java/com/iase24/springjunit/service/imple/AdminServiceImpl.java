package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Faq;
import com.iase24.springjunit.repository.DescriptionCategoryRepository;
import com.iase24.springjunit.repository.FaqRepository;
import com.iase24.springjunit.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {

    private final FaqRepository faqRepository;
    private final DescriptionCategoryRepository descriptionCategoryRepository;

    @Override
    @Transactional
    public void answerForFaq(Long faqId, FaqAnswerDTO answer) {

        // находим коментарий по ID проверяем или есть такой, если нет выводим exception
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new IllegalArgumentException("Faq id: " + faqId + " not found"));

        // сохранение в базу данных
        faq.setAnswer(answer.getAnswer());
        faq.setDateAnswerCreate(LocalDateTime.now());
        faqRepository.save(faq);
    }

    @Override
    @Transactional
    public void deleteFaq(Long categoryId, Long faqId) {

        // находим категорию по ID проверяем или есть такая, если нет выводим exception
        DescriptionCategory category = descriptionCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category id not found"));

        // находим коментарий по ID проверяем или есть такой, если нет выводим exception
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new IllegalArgumentException("Faq id not found"));

        // удаляем коментарий с проверкой или удалился такой, если нет значит выведет exception
        if (category.getFaq().remove(faq)) {
            descriptionCategoryRepository.save(category);
        } else {
            throw new IllegalArgumentException("Faq in description category not found");
        }
    }

    @Override
    @Transactional
    public void createDescriptionCategory(DescriptionCategory descriptionCategory) {

        // добавление коментария к категории
        descriptionCategoryRepository.save(descriptionCategory);
    }

    @Override
    public List<Faq> findAllFaqIsQuestionNotRead() {
        return faqRepository.findAllByAnswerIsNull()
                .stream()
                .sorted((o1, o2) -> 0)
                .toList();
    }
}
