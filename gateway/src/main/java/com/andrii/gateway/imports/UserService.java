package com.andrii.gateway.imports;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void save(User user){
        userRepository.save(user);
    }
    public Optional<User> findByEmail(String email){return userRepository.findByEmail(email);}
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
}
