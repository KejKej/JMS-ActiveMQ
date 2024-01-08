package com.messaging.poc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomMessage {

    public static final String TYPE_ID = "CustomMessage";

    Long id;

    String content;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CustomMessage(@JsonProperty("id") Long id, @JsonProperty("content") String content) {
        this.id = id;
        this.content = content;
    }
}
