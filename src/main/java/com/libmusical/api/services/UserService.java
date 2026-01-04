package com.libmusical.api.services;

import com.libmusical.api.models.UserModel;
import com.libmusical.api.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.libmusical.api.dto.UserRequestDTO;
import com.libmusical.api.dto.UserResponseDTO;
import com.libmusical.api.exceptions.UserNotFoundException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    public UserResponseDTO findById(Long id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return new UserResponseDTO(user);
    }

    public UserResponseDTO create(UserRequestDTO dto) {
        UserModel user = new UserModel();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        return new UserResponseDTO(userRepository.save(user));
    }

    public UserResponseDTO update(Long id, UserRequestDTO dto) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        user.setName(dto.name());
        user.setEmail(dto.email());
        
        return new UserResponseDTO(userRepository.save(user));
    }

    public void updatePassword(Long id, String newPassword) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}