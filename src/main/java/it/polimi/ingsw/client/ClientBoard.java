package it.polimi.ingsw.client;

import java.util.List;

public class ClientBoard {
    int[][] height;
    boolean[][] dome;
    int[][] workerPlayer;
    List<String> playerNames;

    int[][] getHeight(){return height;}
    void setHeight(int[][] height){this.height = height;}

    boolean[][] getDome(){return dome;}
    void setDome(boolean[][] dome){this.dome = dome;}

    int[][] getWorkerPlayer(){return workerPlayer;}
    void setWorkerPlayer(int[][] workerPlayer){this.workerPlayer = workerPlayer;}

    List<String> getPlayerNames(){return playerNames;}
    void addPlayerName(String name){playerNames.add(name);}
}
