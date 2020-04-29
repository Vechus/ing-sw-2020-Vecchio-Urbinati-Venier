package it.polimi.ingsw.connection;

/**
 * The type Handshake.
 */
public class Handshake extends Message {
    /**
     * The enum Handshake type. Host if the player wants to host a new Game, Join if the player wants to join an existing Game.
     */
    public enum handshakeType{HOST, JOIN};
    private int gameId;
    private String playerName;
    private handshakeType type;

    /**
     * Gets game id.
     *
     * @return the game id
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * Sets game id.
     *
     * @param gameId the game id
     */
    public void setGameId(int gameId) {
        this.gameId = gameId;
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

    /**
     * Gets type.
     *
     * @return the handshake type
     */
    public handshakeType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the handshake type
     */
    public void setType(handshakeType type) {
        this.type = type;
    }
}
