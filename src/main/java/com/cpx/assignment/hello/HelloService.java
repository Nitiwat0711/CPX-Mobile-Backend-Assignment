package com.cpx.assignment.hello;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String hello() {
        return "Hello Backend Team";
    }
}
