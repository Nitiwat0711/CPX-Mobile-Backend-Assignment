package com.cpx.assignment.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/user")
@AllArgsConstructor
public class UserController {
    private  final UserService userService;
    private KafkaTemplate<String,User> kafkaTemplate;

    @PostMapping
    public ResponseEntity<?> createNewUser(@RequestBody User user) {
        List<String> requiredList = new ArrayList<>();

        if (user.getFirstName() == null) {
            requiredList.add("firstName");
        }

        if (user.getLastName() == null) {
            requiredList.add("lastName");
        }

        if (user.getEmail() == null) {
            requiredList.add("email");
        }

        if (user.getDob() == null) {
            requiredList.add("dob");
        }

        if (!requiredList.isEmpty()) {
            return new ResponseEntity<String>("request body required " + String.join(", ", requiredList) , HttpStatus.UNPROCESSABLE_ENTITY);
        }

        boolean isCreate = userService.createNewUser(user);
        if (isCreate) {
            kafkaTemplate.send("createNewUser", user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Email already use.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping(path = "{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.getAllUser();
        return  ResponseEntity.ok(users);
    }

    @PutMapping(path = "{userId}")
    public ResponseEntity<?> updateAllDataById(@RequestBody User user, @PathVariable("userId") Integer userId) {
        List<String> requiredList = new ArrayList<>();

        if (user.getFirstName() == null) {
            requiredList.add("firstName");
        }

        if (user.getLastName() == null) {
            requiredList.add("lastName");
        }

        if (user.getEmail() == null) {
            requiredList.add("email");
        }

        if (user.getDob() == null) {
            requiredList.add("dob");
        }

        if (user.getUrl() == null) {
            requiredList.add("url");
        }

        if (user.getBio() == null) {
            requiredList.add("bio");
        }

        if (!requiredList.isEmpty()) {
            return new ResponseEntity<String>("request body required " + String.join(", ", requiredList) , HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User updatedUser = userService.updateAllDataById(
                userId,
                user
        );

        return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping(path = "{userId}")
    public ResponseEntity<?> updateById(@RequestBody User user, @PathVariable("userId") Integer userId) {
        User updatedUser = userService.updateById(userId, user);
        if (
                user.getFirstName() == null && user.getMiddleName() == null && user.getLastName() == null &&
                        user.getEmail() == null && user.getDob() == null && user.getUrl() == null && user.getBio() == null
        ) {
            return  new ResponseEntity<String>("Invalid request body!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return  new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping(path = "{userId}")
    public ResponseEntity<String> deleteById(@PathVariable("userId") Integer userId) {
        boolean success = userService.deleteById(userId);

        if (success == true) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        return  new ResponseEntity<>("Something went wrong!", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
