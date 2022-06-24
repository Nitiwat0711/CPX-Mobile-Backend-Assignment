package com.cpx.assignment.user;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    public void testCreateUserSuccess() {
        when(kafkaTemplate.send("createNewUser", testUser)).thenReturn(Mockito.any());
        when(userService.createNewUser(testUser)).thenReturn(true);
        ResponseEntity<?> responseEntity = userController.createNewUser(testUser);

        assertEquals(new ResponseEntity<User>(testUser, HttpStatus.OK), responseEntity);
        verify(kafkaTemplate, times(1)).send("createNewUser", testUser);
        verify(userService, times(1)).createNewUser(testUser);
        verify(userController, times(1)).createNewUser(testUser);
    }

    @Test
    public void testCreateUserButEmailAlreadyUse() {
        when(userService.createNewUser(testUser)).thenReturn(false);
        ResponseEntity<?> responseEntity = userController.createNewUser(testUser);

        assertEquals(new ResponseEntity<String>("Email already use.", HttpStatus.UNPROCESSABLE_ENTITY), responseEntity);
        verify(userService, times(1)).createNewUser(testUser);
        verify(userController, times(1)).createNewUser(testUser);
    }

    @Test
    public void testGetUserByIdSuccess() {
        when(userService.getUserById(Mockito.anyInt())).thenReturn(testUser);
        ResponseEntity<?> responseEntity = userController.getUserById(Mockito.anyInt());
        assertEquals(ResponseEntity.ok(testUser), responseEntity);
    }

    @Test
    public void testGetAllUserSuccess() {
        when(userService.getAllUser()).thenReturn(users);
        ResponseEntity<List<User>> responseEntity = userController.getAllUser();
        assertEquals(ResponseEntity.ok(users), responseEntity);
    }

    @Test
    public void testUpdateAllUserDataByIdSuccess() {
        User updateUser = new User(1, "Nitiwat", null, "Apaikawee", "nitiwat.apaikawee@scb.co.th",
                LocalDate.parse("1999-11-07"), "nitiwat0711.github.io", "Hi!");
        when(userService.updateAllDataById(updateUser.getId(), updateUser)).thenReturn(updateUser);
        ResponseEntity responseEntity = userController.updateAllDataById(updateUser, 1);
        assertEquals(ResponseEntity.ok(updateUser), responseEntity);
    }

    @Test
    public void testUpdateAllUserDataByIdRequired() {
        User updateUser = new User();
        ResponseEntity responseEntity = userController.updateAllDataById(updateUser, 1);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateUserDataByIdSuccess() {
        User updateUser = new User(1, "Nitiwat", null, "Apaikawee", "nitiwat.apaikawee@scb.co.th",
                LocalDate.parse("1999-11-07"), null, null);
        when(userService.updateById(1, updateUser)).thenReturn(updateUser);
        ResponseEntity responseEntity = userController.updateById(updateUser, 1);
        assertEquals(ResponseEntity.ok(updateUser), responseEntity);
    }

    @Test
    public void testUpdateUserDataByIdRequired() {
        User updateUser = new User();
        ResponseEntity responseEntity = userController.updateById(updateUser, 1);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteUserByIdSuccess() {
        when(userService.deleteById(Mockito.anyInt())).thenReturn(true);
        ResponseEntity responseEntity = userController.deleteById(Mockito.anyInt());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteUserByIdNotExists() {
        when(userService.deleteById(Mockito.anyInt())).thenReturn(false);
        ResponseEntity responseEntity = userController.deleteById(Mockito.anyInt());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

}
