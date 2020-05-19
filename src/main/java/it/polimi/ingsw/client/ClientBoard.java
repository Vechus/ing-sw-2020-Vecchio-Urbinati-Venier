package it.polimi.ingsw.client;

import it.polimi.ingsw.util.Vector2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientBoard implements Serializable {
    int[][] height;
    boolean[][] dome;
    int[][] workerPlayer;
    List<String> playerNames;

    public ClientBoard(){
        height = new int[5][5];
        dome = new boolean[5][5];
        workerPlayer = new int[5][5];
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++)
                workerPlayer[i][j] = -1;
        playerNames = new ArrayList<>();
    }

    public int getHeight(Vector2 pos){return height[pos.getX()][pos.getY()];}
    public void setHeight(Vector2 pos, int height){this.height[pos.getX()][pos.getY()] = height;}

    public boolean getDome(Vector2 pos){return dome[pos.getX()][pos.getY()];}
    public void setDome(Vector2 pos, boolean dome){this.dome[pos.getX()][pos.getY()] = dome;}

    public int getWorkerPlayer(Vector2 pos){return workerPlayer[pos.getX()][pos.getY()];}
    public void setWorkerPlayer(Vector2 pos, int workerPlayer){this.workerPlayer[pos.getX()][pos.getY()] = workerPlayer;}

    public List<String> getPlayerNames(){return playerNames;}
    public void addPlayerName(String name){playerNames.add(name);}
}
