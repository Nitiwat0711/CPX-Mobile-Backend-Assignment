package com.cpx.assignment.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class HelloControllerTest {
    @Mock
    HelloService helloService;

    @Spy
    @InjectMocks
    HelloController helloController;

    @Test
    public void testHello() {
        when(helloService.hello()).thenReturn("Hello Backend Team");

        String string = helloController.hello();

        assertEquals("Hello Backend Team", string);
        verify(helloController, times(1)).hello();
        verify(helloService, times(1)).hello();
    }
}
