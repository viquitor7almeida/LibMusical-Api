package com.libmusical.api.services;

import com.libmusical.api.models.UserModel;
import com.libmusical.api.repositories.UserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    public UserModel create(@NonNull UserModel user) {
        // user.setActive(true);
        return userRepository.save(user);
    }
}