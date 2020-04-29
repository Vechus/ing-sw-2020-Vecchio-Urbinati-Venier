package it.polimi.ingsw.connection;

public class Handshake extends Message {
    public enum handshakeType{HOST, JOIN};
    private int gameId;
    private String playerName;
    private handshakeType type;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public handshakeType getType() {
        return type;
    }

    public void setType(handshakeType type) {
        this.type = type;
    }
}
