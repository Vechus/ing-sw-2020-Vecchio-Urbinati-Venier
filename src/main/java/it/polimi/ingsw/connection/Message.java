package it.polimi.ingsw.connection;

/**
 * The type Message.
 */
public class Message {
    /**
     * The enum Payload type. It describes the status in detail.
     */
    public enum payloadType{MESSAGE, GEN_ERROR, MOVE_INVALID, BUILD_INVALID, BUILD_DOME_INVALID, NOT_TURN_ERROR, GAME_ID_INVALID_ERROR, LOBBY_FULL_ERROR};

    /**
     * The enum Status.
     */
    public enum status{ERROR, OK};
    private String payload;
    private payloadType payloadType;
    private status status;

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Sets payload.
     *
     * @param payload the payload
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * Gets payload type.
     *
     * @return the payload type
     */
    public Message.payloadType getPayloadType() {
        return payloadType;
    }

    /**
     * Sets payload type.
     *
     * @param payloadType the payload type
     */
    public void setPayloadType(Message.payloadType payloadType) {
        this.payloadType = payloadType;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Message.status getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Message.status status) {
        this.status = status;
    }
}
