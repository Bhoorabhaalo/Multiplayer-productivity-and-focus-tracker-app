package com.focusforge.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RoomEventPublisher {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RoomEventPublisher.class);

    public RoomEventPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }



    private final SimpMessagingTemplate messagingTemplate;

    public <T> void publishToRoom(String roomCode, WsEventType type, T payload) {
        WsEnvelope<T> envelope = WsEnvelope.<T>builder()
                .type(type)
                .roomCode(roomCode)
                .serverTime(Instant.now())
                .payload(payload)
                .build();

        String destination = "/topic/room/" + roomCode;
        log.debug("Publishing {} event to {}", type, destination);
        messagingTemplate.convertAndSend(destination, envelope);
    }

    public <T> void publishToUser(String usernameOrEmail, WsEventType type, T payload) {
        WsEnvelope<T> envelope = WsEnvelope.<T>builder()
                .type(type)
                .serverTime(Instant.now())
                .payload(payload)
                .build();

        log.debug("Publishing personal {} event to user {}", type, usernameOrEmail);
        messagingTemplate.convertAndSendToUser(usernameOrEmail, "/queue/notifications", envelope);
    }
}
