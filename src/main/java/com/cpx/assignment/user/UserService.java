package com.cpx.assignment.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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

    public User getUserById(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            return  optionalUser.get();
        }
        throw new IllegalStateException("user id " + userId + " does not exists.");

    }

    public List<User> getAllUser() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User updateAllDataById(Integer userId, String firstName, String lastName, String middleName, String email, LocalDate dob, String url, String bio) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setMiddleName(middleName);
            user.setEmail(email);
            user.setDob(dob);
            user.setUrl(url);
            user.setBio(bio);

            User updatedUser = userRepository.save(user);
            return updatedUser;
        }

        throw  new IllegalStateException("user id " + userId + " does not exists.");
    }
}
