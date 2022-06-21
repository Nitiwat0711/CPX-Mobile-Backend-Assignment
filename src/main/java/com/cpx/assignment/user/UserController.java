package com.cpx.assignment.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/user")
@AllArgsConstructor
public class UserController {
    private  final UserService userService;

    @PostMapping
    public ResponseEntity<String> createNewUser(@RequestBody User user) {
        boolean isCreate = userService.createNewUser(user);
        if (isCreate) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Email already use.", HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<?> updateAllDataById(@RequestBody Map<String, Object> requestBody, @PathVariable("userId") Integer userId) {
        if (
                requestBody.get("firstName") != null &&
                requestBody.get("lastName") != null &&
                requestBody.get("middleName") != null &&
                        requestBody.get("email") != null &&
                        requestBody.get("dob") != null &&
                        requestBody.get("url") != null &&
                        requestBody.get("bio") != null
        ) {
            User updatedUser = userService.updateAllDataById(
                    userId,
                    requestBody.get("firstName").toString(),
                    requestBody.get("lastName").toString(),
                    requestBody.get("middleName").toString(),
                    requestBody.get("email").toString(),
                    LocalDate.parse(requestBody.get("dob").toString()),
                    requestBody.get("url").toString(),
                    requestBody.get("bio").toString()
            );

            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Invalid request body!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping(path = "{userId}")
    public ResponseEntity<?> updateById(@RequestBody User user, @PathVariable("userId") Integer userId) {
        User updatedUser = userService.updateById(userId, user);

        return  new ResponseEntity<User>(updatedUser, HttpStatus.OK);

    }
}
