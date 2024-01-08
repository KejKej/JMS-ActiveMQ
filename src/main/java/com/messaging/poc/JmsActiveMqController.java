package com.messaging.poc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activemq")
@RequiredArgsConstructor
@Slf4j
public class JmsActiveMqController {

    private final JmsTemplate activemqJmsJsonTemplate;

    @PostMapping("")
    public ResponseEntity<Void> sendMessage() {
        try {
            CustomMessage message = CustomMessage.builder()
                    .id(12345L)
                    .content("My first message")
                    .build();

            activemqJmsJsonTemplate.convertAndSend("queue1", message);

            log.info("Message {} sent to queue1", message);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Exception happened {} ", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
