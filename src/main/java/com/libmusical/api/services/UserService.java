package com.libmusical.api.services;

import com.libmusical.api.models.UserModel;
import com.libmusical.api.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.libmusical.api.dto.User.UserRequestDTO;
import com.libmusical.api.dto.User.UserResponseDTO;
import com.libmusical.api.dto.User.UserUpdateDTO;
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

    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        UserModel userLogado = getAuthenticatedUser();

        if (!id.equals(userLogado.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você só pode atualizar o seu próprio perfil.");
        }

        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        user.setName(dto.name());
        user.setEmail(dto.email());
        
        return new UserResponseDTO(userRepository.save(user));
    }

    public void updatePassword(Long id, String newPassword) {
        UserModel userLogado = getAuthenticatedUser();

        if (!id.equals(userLogado.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você só pode alterar a sua própria senha.");
        }

        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void delete(Long id) {
        UserModel userLogado = getAuthenticatedUser();

        if (!id.equals(userLogado.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para deletar outra conta.");
        }

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    private UserModel getAuthenticatedUser() {
        return (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}