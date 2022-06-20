package com.cpx.assignment.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
@AllArgsConstructor
public class UserController {
    private  final UserService userService;

    @PostMapping
    public ResponseEntity<String> createNewUser(@RequestBody User user) {
        boolean isCreate = userService.createNewUser(user);
        System.out.println("post");
        if (isCreate) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Email already use.", HttpStatus.BAD_REQUEST);
    }

}
