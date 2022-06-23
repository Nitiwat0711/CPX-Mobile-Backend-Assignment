package com.cpx.assignment.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    User testUser = new User(1,"John", null, "Doe", "test@mail.com", LocalDate.parse("1999-05-18"),
            "http://www.test.com", "Hello Test");
    List<User> users = new ArrayList<>();
    @Mock
    UserService userService;

    @Mock
    KafkaTemplate<String,User> kafkaTemplate;

    @Spy
    @InjectMocks
    UserController userController;

    @Test
    public void createUserSuccess() {
        when(kafkaTemplate.send("createNewUser", testUser)).thenReturn(Mockito.any());
        when(userService.createNewUser(testUser)).thenReturn(true);
        ResponseEntity<?> responseEntity = userController.createNewUser(testUser);

        assertEquals(new ResponseEntity<User>(testUser, HttpStatus.OK), responseEntity);
        verify(kafkaTemplate, times(1)).send("createNewUser", testUser);
        verify(userService, times(1)).createNewUser(testUser);
        verify(userController, times(1)).createNewUser(testUser);
    }

    @Test
    public void createUserButEmailAlreadyUse() {
        when(userService.createNewUser(testUser)).thenReturn(false);
        ResponseEntity<?> responseEntity = userController.createNewUser(testUser);

        assertEquals(new ResponseEntity<String>("Email already use.", HttpStatus.UNPROCESSABLE_ENTITY), responseEntity);
        verify(userService, times(1)).createNewUser(testUser);
        verify(userController, times(1)).createNewUser(testUser);
    }

    @Test
    public void getUserByIdSuccess() {
        when(userService.getUserById(Mockito.anyInt())).thenReturn(testUser);
        ResponseEntity<?> responseEntity = userController.getUserById(Mockito.anyInt());
        assertEquals(ResponseEntity.ok(testUser), responseEntity);
    }

    @Test
    public void getAllUserSuccess() {
        when(userService.getAllUser()).thenReturn(users);
        ResponseEntity<List<User>> responseEntity = userController.getAllUser();
        assertEquals(ResponseEntity.ok(users), responseEntity);
    }

    @Test
    public void updateAllUserDataByIdSuccess() {
        User updateUser = new User(1, "Nitiwat", null, "Apaikawee", "nitiwat.apaikawee@scb.co.th",
                LocalDate.parse("1999-11-07"), "nitiwat0711.github.io", "Hi!");
        when(userService.updateAllDataById(updateUser.getId(), updateUser.getFirstName(), updateUser.getLastName(), updateUser.getMiddleName(),
                updateUser.getEmail(), updateUser.getDob(), updateUser.getUrl(), updateUser.getBio())).thenReturn(updateUser);
        ResponseEntity responseEntity = userController.updateAllDataById(updateUser, 1);
        assertEquals(ResponseEntity.ok(updateUser), responseEntity);
    }

    @Test
    public void updateAllUserDataByIdRequired() {
        User updateUser = new User();
        ResponseEntity responseEntity = userController.updateAllDataById(updateUser, 1);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    @Test
    public void updateUserDataByIdSuccess() {
        User updateUser = new User(1, "Nitiwat", null, "Apaikawee", "nitiwat.apaikawee@scb.co.th",
                LocalDate.parse("1999-11-07"), null, null);
        when(userService.updateById(1, updateUser)).thenReturn(updateUser);
        ResponseEntity responseEntity = userController.updateById(updateUser, 1);
        assertEquals(ResponseEntity.ok(updateUser), responseEntity);
    }

    @Test
    public void updateUserDataByIdRequired() {
        User updateUser = new User();
        ResponseEntity responseEntity = userController.updateById(updateUser, 1);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    @Test
    public void deleteUserByIdSuccess() {
        when(userService.deleteById(Mockito.anyInt())).thenReturn(true);
        ResponseEntity responseEntity = userController.deleteById(Mockito.anyInt());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void deleteUserByIdNotExists() {
        when(userService.deleteById(Mockito.anyInt())).thenReturn(false);
        ResponseEntity responseEntity = userController.deleteById(Mockito.anyInt());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

}
