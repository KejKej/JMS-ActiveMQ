package com.messaging.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

import javax.validation.constraints.NotNull;

@Slf4j
@Component
public class JmsExceptionHandler implements ErrorHandler {

    @Override
    public void handleError(@NotNull Throwable t) {
        log.error(t.getMessage(), t);
    }
}
