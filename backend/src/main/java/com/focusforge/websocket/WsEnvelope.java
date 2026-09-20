package com.focusforge.websocket;


import java.time.Instant;

public class WsEnvelope<T> {

    private WsEventType type;
    private String roomCode;
        private Instant serverTime = Instant.now();
    private T payload;

    public WsEnvelope() {}

    public WsEnvelope(WsEventType type, String roomCode, Instant serverTime, T payload) {
        this.type = type;
        this.roomCode = roomCode;
        this.serverTime = serverTime;
        this.payload = payload;
    }

    public WsEventType getType() {
        return this.type;
    }

    public void setType(WsEventType type) {
        this.type = type;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public Instant getServerTime() {
        return this.serverTime;
    }

    public void setServerTime(Instant serverTime) {
        this.serverTime = serverTime;
    }

    public T getPayload() {
        return this.payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public static <T> Builder<T> builder() {
        return new Builder<T>();
    }

    public static class Builder<T> {
        private WsEventType type;
        private String roomCode;
        private Instant serverTime = Instant.now();
        private T payload;

        public Builder<T> type(WsEventType type) {
            this.type = type;
            return this;
        }

        public Builder<T> roomCode(String roomCode) {
            this.roomCode = roomCode;
            return this;
        }

        public Builder<T> serverTime(Instant serverTime) {
            this.serverTime = serverTime;
            return this;
        }

        public Builder<T> payload(T payload) {
            this.payload = payload;
            return this;
        }

        public WsEnvelope<T> build() {
            WsEnvelope<T> instance = new WsEnvelope<T>();
            instance.type = this.type;
            instance.roomCode = this.roomCode;
            instance.serverTime = this.serverTime;
            instance.payload = this.payload;
            return instance;
        }
    }
}
