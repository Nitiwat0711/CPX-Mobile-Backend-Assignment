package com.cpx.assignment.user;

import org.apache.zookeeper.Op;
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
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {
    User testUserBeforeUpdated = new User(1,"John", null, "Doe", "test@mail.com", LocalDate.parse("1999-05-18"),
            null, null);
    User testUser = new User(1,"John", null, "Doe", "test@mail.com", LocalDate.parse("1999-05-18"),
            "http://www.test.com", "Hello Test");
    List<User> users = new ArrayList<>();

    @Mock
    UserRepository userRepository;

    @Test
    public void testFindByEmailSuccess() {
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.of(testUserBeforeUpdated));
        Optional<User> optionalUser = userRepository.findByEmail("test@mail.com");
        assertEquals(testUserBeforeUpdated, optionalUser.get());
    }

    @Test
    public void testFindByEmailNotExists() {
        when(userRepository.findByEmail("test@mail.com")).thenReturn(Optional.empty());
        Optional<User> optionalUser = userRepository.findByEmail("test@mail.com");
        assertEquals(false, optionalUser.isPresent());
    }

    @Test
    public void testFindByIdSuccess() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUserBeforeUpdated));
        Optional<User> optionalUser = userRepository.findById(1);
        assertEquals(testUserBeforeUpdated, optionalUser.get());
    }

    @Test
    public void testFindByIdNotExists() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        Optional<User> optionalUser = userRepository.findById(1);
        assertEquals(false, optionalUser.isPresent());
    }

    @Test
    public void testFindAllByOrderByIdAscSuccess() {
        when(userRepository.findAllByOrderByIdAsc()).thenReturn(users);
        List<User> userList = userRepository.findAllByOrderByIdAsc();
        assertEquals(users, userList);
    }

    @Test
    public void testSaveSuccess() {
        when(userRepository.save(testUser)).thenReturn(testUser);
        User user = userRepository.save(testUser);
        assertEquals(testUser, user);
    }

    @Test
    public void testDeleteByIdSuccess() {
        doNothing().when(userRepository).deleteById(1);
        userRepository.deleteById(1);
        verify(userRepository, times(1)).deleteById(1);
    }


}
