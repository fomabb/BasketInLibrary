package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.CreateUserDTO;
import com.iase24.springjunit.dto.FaqQuestionDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Faq;
import com.iase24.springjunit.exception.ValidationException;
import com.iase24.springjunit.mapper.user.CreateUserMapper;
import com.iase24.springjunit.mapper.user.UserMapper;
import com.iase24.springjunit.repository.DescriptionCategoryRepository;
import com.iase24.springjunit.repository.FaqRepository;
import com.iase24.springjunit.repository.RoleRepository;
import com.iase24.springjunit.repository.UserRepository;
import com.iase24.springjunit.service.UserService;
import com.iase24.springjunit.validator.CreateUserValidator;
import com.iase24.springjunit.validator.ValidationResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CreateUserMapper createUserMapper;
    private final UserMapper userMapper;
    private final CreateUserValidator createUserValidator;
    private final DescriptionCategoryRepository descriptionCategoryRepository;
    private final FaqRepository faqRepository;
    private final RoleRepository roleRepository;

    @Override
    public Optional<UserDataDTO> login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).map(userMapper::map);
    }

    @SneakyThrows
    @Transactional
    @Override
    public UserDataDTO createNewUser(CreateUserDTO createUserDTO) {
        ValidationResult validationResult = createUserValidator.validate(createUserDTO);
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createUserMapper.map(createUserDTO);
        userEntity.setPassword(userEntity.getPassword());
        userEntity.setRoles(List.of(roleRepository.findByName("ROLE_USER").get()));
        userRepository.save(userEntity);
        return userMapper.map(userEntity);
    }

    @Override
    public Optional<UserDataDTO> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .map(userMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + id + " not found")));
    }

    @Override
    public List<UserDataDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDataDTO(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
    }

    //    TODO
    @Override
    public Optional<UserDataDTO> getCartByUserId(Long userId) {
        return userRepository.findUserByIdAndCart(userId).map(userMapper::map);
    }

    @Override
    @Transactional
    public void questionCategory(Long categoryId, FaqQuestionDTO question) {
        DescriptionCategory descriptionCategory = descriptionCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Description category not found"));
        Faq faq = new Faq();
        faq.setQuestion(question.getQuestion());
        faq.setDateQuestionCreate(LocalDateTime.now());
        faq.setDescriptionCategory(descriptionCategory);
        descriptionCategory.getFaq().add(faq);
        descriptionCategoryRepository.save(descriptionCategory);
    }

    @Override
    @Transactional
    public void removeFaqFromCategory(Long categoryId, Long faqId) {
        DescriptionCategory category = descriptionCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with id not found"));
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new IllegalArgumentException("Faq with id not found"));
        if (category.getFaq().remove(faq)) {
            descriptionCategoryRepository.save(category);
        } else {
            throw new IllegalArgumentException("Faq in description category not found");
        }
    }

    @Override
    @Transactional
    public void updateQuestion(Long faqId, FaqQuestionDTO question) {
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new IllegalArgumentException("Faq id not found"));
        faq.setQuestion(question.getQuestion());
        faqRepository.save(faq);
    }
}
