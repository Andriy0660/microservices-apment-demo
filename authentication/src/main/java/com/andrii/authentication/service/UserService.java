package com.andrii.authentication.service;

import com.andrii.authentication.entity.User;
import com.andrii.authentication.exception.BadRequestException;
import com.andrii.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void save(User user){
        userRepository.save(user);
    }
    public User findByEmail(String email){return userRepository.findByEmail(email).orElseThrow(()->
            new BadRequestException("There is no user with this email"));}
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
}
