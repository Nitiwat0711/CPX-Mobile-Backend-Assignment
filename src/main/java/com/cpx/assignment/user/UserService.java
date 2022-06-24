package com.cpx.assignment.user;

import lombok.AllArgsConstructor;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.sql.SQLException;
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
        try {
            Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
            if (userOptional.isEmpty()) {
                userRepository.save(user);
                return true;
            }
            return false;
        } catch (JDBCConnectionException exception) {
            throw new IllegalStateException("Can't connect to database.");
        }


    }

    public User getUserById(Integer userId) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                return  optionalUser.get();
            }
            throw new IllegalStateException("user id " + userId + " does not exists.");
        } catch (JDBCConnectionException exception) {
            throw new IllegalStateException("Can't connect to database.");
        }


    }

    public List<User> getAllUser() {
        try {
            List<User> users = userRepository.findAllByOrderByIdAsc();
            return users;
        } catch (JDBCConnectionException exception) {
            throw new IllegalStateException("Can't connect to database.");
        }

    }

    public User updateAllDataById(Integer userId, User updateUser) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                user.setFirstName(updateUser.getFirstName());
                user.setLastName(updateUser.getLastName());
                user.setMiddleName(updateUser.getMiddleName());
                user.setEmail(updateUser.getEmail());
                user.setDob(updateUser.getDob());
                user.setUrl(updateUser.getUrl());
                user.setBio(updateUser.getBio());

                User updatedUser = userRepository.save(user);
                return updatedUser;
            }

            throw  new IllegalStateException("user id " + userId + " does not exists.");
        }
         catch (JDBCConnectionException exception) {
            throw new IllegalStateException("Can't connect to database.");
        }

    }

    public User updateById(Integer userId,User user) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                User updateUser = optionalUser.get();

                if (
                        user.getFirstName() != null &&
                        !user.getFirstName().equals(updateUser.getFirstName())
                ) {
                    updateUser.setFirstName(user.getFirstName());
                }

                if (
                        user.getMiddleName() != null &&
                        !user.getMiddleName().equals(updateUser.getMiddleName())
                ) {
                    updateUser.setMiddleName((user.getMiddleName()));
                }

                if (
                        user.getLastName() != null &&
                                !user.getLastName().equals(updateUser.getLastName())
                ) {
                    updateUser.setLastName(user.getLastName());
                }

                if (
                        user.getEmail() != null &&
                                !user.getEmail().equals(updateUser.getEmail())
                ) {
                    updateUser.setEmail((user.getEmail()));
                }

                if (
                        user.getDob() != null &&
                                !user.getDob().equals(updateUser.getDob())
                ) {
                    updateUser.setDob(user.getDob());
                }

                if (
                        user.getUrl() != null &&
                                !user.getUrl().equals(updateUser.getUrl())
                ) {
                    updateUser.setUrl(user.getUrl());
                }

                if (
                        user.getBio() != null &&
                                !user.getBio().equals(updateUser.getBio())
                ) {
                    updateUser.setBio(user.getBio());
                }

                User updatedUser = userRepository.save(updateUser);
                return updatedUser;
            }

            throw  new IllegalStateException("user id " + userId + " does not exists.");
        } catch (JDBCConnectionException exception) {
            throw new IllegalStateException("Can't connect to database.");
        }
    }

    public boolean deleteById(Integer userId) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                userRepository.deleteById(userId);
                return true;
            }

            throw new IllegalStateException("user id " + userId + " does not exists.");
        } catch (JDBCConnectionException exception) {
            throw new IllegalStateException("Can't connect to database.");
        }
    }
}
