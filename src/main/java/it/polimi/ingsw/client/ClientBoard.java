package it.polimi.ingsw.client;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Is the representation of the board sent to the clients.
 */
public class ClientBoard implements Serializable {
    int[][] height;
    boolean[][] dome;
    int[][] workerPlayer;
    List<String> playerNames;
    Map<String, List<ActionType>> allowedMoves;
    Map<String, String> gods;
    String currentPlayer;

    public ClientBoard(){
        height = new int[5][5];
        dome = new boolean[5][5];
        workerPlayer = new int[5][5];
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++)
                workerPlayer[i][j] = -1;
        playerNames = new ArrayList<>();
        allowedMoves = new HashMap<>();
        gods = new HashMap<>();
    }

    public int getHeight(Vector2 pos){return height[pos.getX()][pos.getY()];}
    public void setHeight(Vector2 pos, int height){this.height[pos.getX()][pos.getY()] = height;}

    public boolean getDome(Vector2 pos){return dome[pos.getX()][pos.getY()];}
    public void setDome(Vector2 pos, boolean dome){this.dome[pos.getX()][pos.getY()] = dome;}

    public int getWorkerPlayer(Vector2 pos){return workerPlayer[pos.getX()][pos.getY()];}
    public void setWorkerPlayer(Vector2 pos, int workerPlayer){this.workerPlayer[pos.getX()][pos.getY()] = workerPlayer;}

    public List<String> getPlayerNames(){return playerNames;}
    public void addPlayerName(String name){playerNames.add(name);}

    public List<ActionType> getAllowedMoves(String name){return allowedMoves.get(name);}
    public void setAllowedMoves(String name, List<ActionType> allowedMoves){this.allowedMoves.put(name, allowedMoves);}

    public String getGod(String name){return gods.get(name);}
    public void setGod(String name, String god){this.gods.put(name, god);}

    public String getCurrentPlayer() {return currentPlayer;}
    public void setCurrentPlayer(String currentPlayer) {this.currentPlayer = currentPlayer;}
}
