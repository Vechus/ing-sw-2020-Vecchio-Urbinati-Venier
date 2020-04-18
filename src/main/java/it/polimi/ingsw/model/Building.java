package it.polimi.ingsw.model;

/**
 * The type Building.
 */
public class Building {
    private int height = 0;
    private boolean complete = false;

    /**
     * Instantiates a new Building.
     */
    public Building(){}

    /**
     * Getter for height
     * @return height of the building
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Setter for height. Valid heights are 0 to 3.
     * @param height new building height
     */
    public void setHeight(int height){
        if(height < 0 || height > 3) return;
        this.height = height;
    }

    /**
     * Getter for complete
     * @return whether the building has a dome
     */
    public boolean isComplete(){
        return this.complete;
    }

    /**
     * Setter for complete
     * @param complete bool
     */
    public void setComplete(boolean complete){
        this.complete = complete;
    }
}
