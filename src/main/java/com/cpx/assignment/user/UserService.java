package com.cpx.assignment.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public boolean createNewUser(User user) {
        System.out.println(user.toString());
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isEmpty()) {
            userRepository.save(user);
            return true;
        }
        return false;

    }

}
