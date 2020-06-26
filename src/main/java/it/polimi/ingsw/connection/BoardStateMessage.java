package it.polimi.ingsw.connection;

import it.polimi.ingsw.client.ClientBoard;

/**
 *A specific type of message that sends the current state of the board.
 */
public class BoardStateMessage extends Message{
    private ClientBoard state;

    public BoardStateMessage(){
        setMessageType(MessageType.BOARD_STATE);
    }

    public ClientBoard getGameState() {
        return state;
    }

    public void setGameState(ClientBoard state) {
        this.state = state;
    }
}
