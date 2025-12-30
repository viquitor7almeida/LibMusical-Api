package com.libmusical.api.services;

import com.libmusical.api.models.UserModel;
import com.libmusical.api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import com.libmusical.api.dto.UserRequestDTO;
import com.libmusical.api.dto.UserResponseDTO;
import com.libmusical.api.exceptions.UserNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO create(UserRequestDTO dto) {
        UserModel user = new UserModel();
        copyDtoToEntity(dto, user);
        return new UserResponseDTO(userRepository.save(user));
    }

    public UserResponseDTO update(Long id, UserRequestDTO dto) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        copyDtoToEntity(dto, user);
        return new UserResponseDTO(userRepository.save(user));
    }

    // Método privado para centralizar a cópia de dados
    private void copyDtoToEntity(UserRequestDTO dto, UserModel entity) {
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        if (dto.password() != null) {
            // Aqui entraria a criptografia: encoder.encode(dto.password())
            entity.setPassword(dto.password()); 
        }
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) throw new UserNotFoundException(id);
        userRepository.deleteById(id);
    }
}