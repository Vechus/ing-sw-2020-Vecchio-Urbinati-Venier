package it.polimi.ingsw.connection;

public class Message {
    public enum payloadType{MESSAGE, GEN_ERROR, MOVE_INVALID, BUILD_INVALID, BUILD_DOME_INVALID, NOT_TURN_ERROR, GAME_ID_INVALID_ERROR, LOBBY_FULL_ERROR};
    public enum status{ERROR, OK};
    private String payload;
    private payloadType payloadType;
    private status status;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Message.payloadType getPayloadType() {
        return payloadType;
    }

    public void setPayloadType(Message.payloadType payloadType) {
        this.payloadType = payloadType;
    }

    public Message.status getStatus() {
        return status;
    }

    public void setStatus(Message.status status) {
        this.status = status;
    }
}
