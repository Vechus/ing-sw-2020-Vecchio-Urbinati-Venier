package it.polimi.ingsw.connection;

public class Handshake extends Message{
    public enum handshakeType{HOST, JOIN};
    private String matchId;
    private String playerName;
    private String playerColor;

}
