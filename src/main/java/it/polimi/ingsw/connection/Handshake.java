package it.polimi.ingsw.connection;

/**
 * The type Handshake.
 */
public class Handshake extends Message {
    private String playerName;

    public Handshake(){
        setMessageType(MessageType.HANDSHAKE);
    }

    /**
     * Gets player name.
     *
     * @return the player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets player name.
     *
     * @param playerName the player name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
