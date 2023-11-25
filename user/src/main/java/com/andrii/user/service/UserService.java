package com.andrii.user.service;

import com.andrii.user.entity.User;
import com.andrii.user.exception.BadRequestException;
import com.andrii.user.repository.UserRepository;
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
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()->
                new BadRequestException("There is no user with this id")
        );
    }
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
}
