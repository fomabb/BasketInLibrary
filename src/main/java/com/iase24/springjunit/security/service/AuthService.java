package com.iase24.springjunit.security.service;

import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.repository.UserRepository;
import com.iase24.springjunit.security.dto.RegistrationUserDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService implements UserDetailsService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Lazy
    @Autowired
    public AuthService(RoleService roleService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not exist", username)));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }

    public User registrationNewUser(RegistrationUserDTO registrationUserDTO) {
        User user = new User();
        user.setUsername(registrationUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDTO.getPassword()));
        user.setEmail(registrationUserDTO.getEmail());
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }

    public Optional<User> findUserByUsername(String username) {

        return userRepository.findUserByUsername(username);
    }
}
