package it.polimi.ingsw.connection;

import java.util.ArrayList;
import java.util.List;

/**
 *A specific type of message that gives the player the remaining gods that can bo chosen.
 */
public class AvailableGodsMessage extends Message {
    List<String> gods;
    String curPlayer;

    public AvailableGodsMessage(){
        setMessageType(MessageType.AVAILABLE_GODS);
        gods = new ArrayList<>();
    }

    public List<String> getGods(){return gods;}
    public void addGod(String god){gods.add(god);}
    public void removeGod(String god){gods.remove(god);}

    public String getCurPlayer() {return curPlayer;}
    public void setCurPlayer(String curPlayer) {this.curPlayer = curPlayer;}
}
