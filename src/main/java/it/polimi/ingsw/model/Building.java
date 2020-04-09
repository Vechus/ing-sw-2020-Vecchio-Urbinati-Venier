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
     * Get height int.
     *
     * @return the height
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Set height int.
     *
     * @param height the height of the building.
     */
    public void setHeight(int height){
        this.height = height;
    }

    /**
     * Is a complete building boolean.
     *
     * @return the boolean
     */
    public boolean isComplete(){
        return this.complete;
    }

    /**
     * Set complete building boolean.
     *
     * @param complete boolean to set.
     */
    public void setComplete(boolean complete){
        this.complete = complete;
    }
}
