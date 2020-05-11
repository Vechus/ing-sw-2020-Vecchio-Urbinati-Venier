package it.polimi.ingsw.connection;

import it.polimi.ingsw.client.ClientBoard;

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
