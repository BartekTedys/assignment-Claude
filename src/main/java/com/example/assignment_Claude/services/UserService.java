package com.example.assignment_Claude.services;


import com.example.assignment_Claude.daos.UserRepository;
import com.example.assignment_Claude.dtos.UserDto;
import com.example.assignment_Claude.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto registerUser(UserDto userDto, String rawPassword) {
        if (userRepository.existsByUsername(userDto.username())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(userDto.username());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(userDto.roles());

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getRoles()
        );
    }
}