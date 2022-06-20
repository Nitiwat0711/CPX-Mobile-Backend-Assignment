package com.cpx.assignment.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public boolean createNewUser(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isEmpty()) {
            userRepository.save(user);
            return true;
        }
        return false;

    }

    public ResponseEntity<User> getUserById(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            return  ResponseEntity.ok(optionalUser.get());
        }
        throw new IllegalStateException("user id " + userId + " does not exists.");

    }
}
