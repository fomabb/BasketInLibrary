package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.repository.UserRepository;
import com.iase24.springjunit.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDataDTO getUserById(Long id) {

        Optional<User> user = userRepository.findById(id);
        UserDataDTO userDataDTO = new UserDataDTO();

        if (user.isPresent()) {
            userDataDTO.setId(user.get().getId());
            userDataDTO.setLogin(user.get().getLogin());
            userDataDTO.setEmail(user.get().getEmail());
            return userDataDTO;
        } else {
            throw new EntityNotFoundException("User with id: " + id + "not found");
        }
    }

    @Override
    public void createNewUser(User user) {

        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        userRepository.save(user);
    }

    @Override
    public List<UserDataDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDataDTO(user.getId(), user.getLogin(), user.getEmail()))
                .collect(Collectors.toList());
    }
}
