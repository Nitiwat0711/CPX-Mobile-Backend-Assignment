package com.cpx.assignment;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(topics = "createNewUser", groupId = "groupId")
    void listener(String message) {
        System.out.println("Listener received from createNewUser topic :"+ "\"" + message + "\"" );
    }
}
