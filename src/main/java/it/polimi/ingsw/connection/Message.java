package it.polimi.ingsw.connection;

/**
 * The type Message.
 */
public class Message {
    /**
     * The enum Payload type. It describes the status in detail.
     */
    public enum ErrorType {GEN_ERROR, MOVE_INVALID, BUILD_INVALID, BUILD_DOME_INVALID, NOT_TURN_ERROR, GAME_ID_INVALID_ERROR, LOBBY_FULL_ERROR, CLOSE_SESSION}
    public enum MessageType {GEN_MESSAGE, ACTION_REQUEST, ACTION, HANDSHAKE, BOARD_STATE, GAME_END}

    /**
     * The enum Status.
     */
    public enum Status {ERROR, OK}

    private String payload;
    private ErrorType errorType;
    private MessageType messageType;
    private Status status;

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
    public ErrorType getErrorType() {
        return errorType;
    }

    /**
     * Sets payload type.
     *
     * @param errorType the payload type
     */
    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets message type.
     *
     * @return the message type
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * Sets message type.
     *
     * @param messageType the message type
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "status=" + status +
                (status == Status.OK ?
                    ", messageType=" + messageType :
                    ", errorType=" + errorType) +
                ", payload='" + payload + '\'' +
                '}';
    }
}
