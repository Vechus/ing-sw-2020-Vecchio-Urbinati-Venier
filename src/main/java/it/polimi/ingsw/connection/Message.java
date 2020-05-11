package it.polimi.ingsw.connection;

/**
 * The type Message.
 */
public class Message {
    /**
     * The enum Payload type. It describes the status in detail.
     */
    public enum errorType{GEN_ERROR, MOVE_INVALID, BUILD_INVALID, BUILD_DOME_INVALID, NOT_TURN_ERROR, GAME_ID_INVALID_ERROR, LOBBY_FULL_ERROR, CLOSE_SESSION};
    public enum messageType{GEN_MESSAGE, BEGIN_TURN, END_TURN, START_SESSION};

    /**
     * The enum Status.
     */
    public enum status{ERROR, OK};
    private String payload;
    private errorType errorType;
    private messageType messageType;
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
    public Message.errorType getErrorType() {
        return errorType;
    }

    /**
     * Sets payload type.
     *
     * @param errorType the payload type
     */
    public void setErrorType(Message.errorType errorType) {
        this.errorType = errorType;
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

    /**
     * Gets message type.
     *
     * @return the message type
     */
    public Message.messageType getMessageType() {
        return messageType;
    }

    /**
     * Sets message type.
     *
     * @param messageType the message type
     */
    public void setMessageType(Message.messageType messageType) {
        this.messageType = messageType;
    }
}
