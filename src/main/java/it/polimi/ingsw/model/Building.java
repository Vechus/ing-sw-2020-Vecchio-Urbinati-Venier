package it.polimi.ingsw.model;

public class Building {
    private int height = 0;
    private boolean complete = false;

    public Building(){}

    public int getHeight(){
        return this.height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public boolean isComplete(){
        return this.complete;
    }

    public void setComplete(boolean complete){
        this.complete = complete;
    }
}
