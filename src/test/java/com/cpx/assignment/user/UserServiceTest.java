package com.cpx.assignment.user;

import org.hibernate.exception.JDBCConnectionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    User testUserBeforeUpdated = new User(1,"John", null, "Doe", "test@mail.com", LocalDate.parse("1999-05-18"),
            null, null);
    User testUser = new User(1,"John", null, "Doe", "test@mail.com", LocalDate.parse("1999-05-18"),
            "http://www.test.com", "Hello Test");
    List<User> users = new ArrayList<>();
    @Mock
    UserRepository userRepository;

    @Spy
    @InjectMocks
    UserService userService;

    @Test
    public void testCreateNewUserSuccess () {
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.empty());
        when(userRepository.save(testUser)).thenReturn(testUser);

        boolean success = userService.createNewUser(testUser);
        assertEquals(true, success);
    }

    @Test
    public void testCreateNewUserButEmailAlreadyUse () {
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(testUser));

        boolean success = userService.createNewUser(testUser);
        assertEquals(false, success);
    }

    @Test
    public void testCreateNewUserButCantConnectToDB () {
        when(userRepository.findByEmail("test@mail.com")).thenThrow(JDBCConnectionException.class);
        try {
            userService.createNewUser(testUser);
        } catch (Exception e) {
            assertEquals("Can't connect to database.", e.getMessage());
        }
    }

    @Test
    public void testGetUserByIdSuccess () {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        Optional<User> user = userRepository.findById(1);

        assertEquals(testUser, user.get());
    }

    @Test
    public void testGetUserByIdNotExists () {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        try {
            userService.getUserById(1);
        } catch (Exception e) {
            assertEquals("user id " + "1" + " does not exists.", e.getMessage());
        }
    }

    @Test
    public void testGetUserByIdButNotConnectToDB () {
        when(userRepository.findById(1)).thenThrow(JDBCConnectionException.class);
        try {
            userService.getUserById(1);
        } catch (Exception e) {
            assertEquals("Can't connect to database.", e.getMessage());
        }
    }

    @Test
    public void testGetAllUserByIdSuccess () {
        when(userRepository.findAllByOrderByIdAsc()).thenReturn(users);
        List<User> userList = userService.getAllUser();
        assertEquals(users, userList);
    }

    @Test
    public void testGetAllUserByIdButNotConnectToDB () {
        when(userRepository.findAllByOrderByIdAsc()).thenThrow(JDBCConnectionException.class);

        try {
            userService.getAllUser();
        } catch (Exception e) {
            assertEquals("Can't connect to database.", e.getMessage());
        }
    }

    @Test
    public void testUpdateAllDataByIdSuccess () {

        when(userRepository.findById(1)).thenReturn(Optional.of(testUserBeforeUpdated));
        when(userRepository.save(testUser)).thenReturn(testUser);
        User updateUser = userService.updateAllDataById(1,testUser);
        assertEquals(testUser, updateUser);
    }

    @Test
    public void testUpdateAllDataByIdButNotExists () {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        try {
            userService.updateAllDataById(testUser.getId(), testUser);
        } catch (Exception e) {
            assertEquals("user id 1 does not exists.", e.getMessage());
        }
    }

    @Test
    public void testUpdateAllDataByIdButNotConnectToDB () {
        when(userRepository.findById(1)).thenThrow(JDBCConnectionException.class);
        try {
            userService.updateAllDataById(1, testUser);
        } catch (Exception e) {
            assertEquals("Can't connect to database.", e.getMessage());
        }
    }

    @Test
    public void testUpdateDataByIdSuccess () {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUserBeforeUpdated));
        when((userRepository.save(testUser))).thenReturn(testUser);

        User user = userService.updateById(1, testUser);
        assertEquals(testUser, user);
    }

    @Test
    public void testUpdateDataByIdButNotExists () {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        try {
            userService.updateById(1, testUser);
        } catch (Exception e) {
            assertEquals("user id 1 does not exists.", e.getMessage());
        }


    }

    @Test
    public void testUpdateDataByIdButNotConnectToDB () {
        when(userRepository.findById(1)).thenThrow(JDBCConnectionException.class);
        try {
            userService.updateById(1, testUser);
        } catch (Exception e) {
            assertEquals("Can't connect to database.", e.getMessage());
        }

    }

    @Test
    public void testDeleteUserByIdSuccess () {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).deleteById(1);
        boolean success = userService.deleteById(1);
        assertEquals(true, success);
    }

    @Test
    public void testDeleteUserByIdButNotExists () {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        try {
            userService.deleteById(1);
        } catch (Exception e) {
            assertEquals("user id 1 does not exists.", e.getMessage());
        }
    }

    @Test
    public void testDeleteUserByIdButNotConnectDB () {
        when(userRepository.findById(1)).thenThrow(JDBCConnectionException.class);

        try {
            userService.deleteById(1);
        } catch (Exception e) {
            assertEquals("Can't connect to database.", e.getMessage());
        }
    }
}
