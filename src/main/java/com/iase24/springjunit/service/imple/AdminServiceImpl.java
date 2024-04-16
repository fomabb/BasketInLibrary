package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Faq;
import com.iase24.springjunit.entities.Role;
import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.repository.DescriptionCategoryRepository;
import com.iase24.springjunit.repository.FaqRepository;
import com.iase24.springjunit.repository.UserRepository;
import com.iase24.springjunit.security.service.RoleService;
import com.iase24.springjunit.service.AdminService;
import jakarta.persistence.EntityManager;
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
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final EntityManager entityManager;

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

    @Transactional
    @Override
    public void updateUserRolesByUsername(Long userId) {
//        User user = entityManager.find(User.class, userId);
//        if (user != null) {
//            Role roleAdmin = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = 'ROLE_ADMIN'", Role.class)
//                    .getSingleResult();
//
//            user.getRoles().add(roleAdmin);
//            entityManager.refresh(user);
//    } else {
//        throw new IllegalArgumentException("User not found");
//    }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User id: " + userId + " not found"));
        Role roleAdmin = roleService.getAdminRole();
        user.getRoles().add(roleAdmin);
        userRepository.save(user);
    }
}











