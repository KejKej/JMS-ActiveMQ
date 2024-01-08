package com.messaging.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(name = "jms.activemq", havingValue = "true")
public class JmsActiveMqListener {

    @JmsListener(destination = "queue1", containerFactory = "queue1Factory")
    public void receiveMessage(CustomMessage message){
        log.info("Received message in queue1 {} ", message);
    }
}
