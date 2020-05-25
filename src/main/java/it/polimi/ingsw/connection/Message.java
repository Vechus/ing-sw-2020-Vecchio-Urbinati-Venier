package it.polimi.ingsw.connection;

import java.io.Serializable;

/**
 * The type Message.
 */
public class Message implements Serializable {
    /**
     * The enum Payload type. It describes the status in detail.
     */
    public enum ErrorType {MOVE_INVALID, LOBBY_ERROR, NAME_TAKEN_ERROR}
    public enum MessageType {ACTION, SETUP_REQ, SETUP, AVAILABLE_GODS, GOD_CHOICE, HANDSHAKE, BOARD_STATE, GAME_END}

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
                ", messageType=" + messageType +
                ", errorType=" + errorType +
                ", payload='" + payload + '\'' +
                '}';
    }
}
