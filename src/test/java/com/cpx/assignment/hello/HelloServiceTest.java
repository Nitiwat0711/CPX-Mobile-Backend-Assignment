package com.cpx.assignment.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class HelloServiceTest {
    @Spy
    @InjectMocks
    HelloService helloService;

    @Test
    public void testHello() {
        String string = helloService.hello();

        assertEquals("Hello Backend Team", string);
        verify(helloService, times(1)).hello();
    }
}
