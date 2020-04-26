package it.polimi.ingsw.model;


import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.listeners.ModelChangeListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The type Model.
 */
public class Model {
    private Board board;
    private List<Player> players;
    private List<ModelChangeListener> listeners;

    /**
     * Instantiates a new Model.
     */
    public Model() {
        this.board = new Board();
        this.players = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    /**
     * Add a new ModelChangeListener
     *
     * @param listener the listener to add
     */
    public void addListener(ModelChangeListener listener){ listeners.add(listener); }

    /**
     * Add a new player.
     *
     * @param god the god.
     *
     * @return the index of the player
     */
    public int addNewPlayer(God god) {
        this.players.add(new Player(god, this.board));
        return this.players.size()-1;
    }

    /**
     * Check players lose condition.
     */
    public void checkPlayersLoseCondition() {
        Iterator<Player> playerIterator = this.players.iterator();
        while(playerIterator.hasNext()) {
            Player p = playerIterator.next();
            if(p.checkLoseCondition()) {
                // player lost the game
                p.setSpectator(true);
                playerIterator.remove();
            }
        }
    }

    /**
     * Check game over boolean.
     *
     * @return the boolean
     */
    public boolean checkGameOver() {
        // can also do this:
        // this.checkPlayersLoseCondition();
        return (this.players.size() == 1);
    }

    /**
     * Execute action.
     *
     * @param playerIndex the player index
     * @param action      the action
     *
     * @return was the action executed successfully
     */
    public boolean executeAction(int playerIndex, Action action) {
        // possible usage of InvalidMoveException
        boolean res = players.get(playerIndex).doAction(action);
        for(ModelChangeListener listener : listeners)
            listener.onModelChange(this);
        return res;
    }

    /**
     * Begin a new turn.
     *
     * @param currentPlayer the current player.
     */
    public void beginNewTurn(Player currentPlayer) {
        currentPlayer.beginNewTurn();
    }

    /**
     * Gets player.
     *
     * @param index the index
     * @return the player
     */
    public Player getPlayer(int index) {
        return players.get(index);
    }

    /**
     * Gets number of players.
     *
     * @return the number of players
     */
    public int getNumberOfPlayers() {
        return players.size();
    }

    /**
     * Gets board.
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }
}
