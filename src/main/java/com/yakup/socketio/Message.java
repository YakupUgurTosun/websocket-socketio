package com.yakup.socketio;

import lombok.Data;
import java.util.Map;

@Data
public class Message {
    private String messageId;
    private Map<String, String> attributes;


}
