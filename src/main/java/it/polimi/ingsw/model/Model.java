package it.polimi.ingsw.model;


import it.polimi.ingsw.model.god.God;

import java.util.ArrayList;

/**
 * The type Model.
 */
public class Model {
    private Board board;
    private ArrayList<Player> players;

    /**
     * Instantiates a new Model.
     */
    public Model() {
        this.board = new Board();
        this.players = new ArrayList<>();
    }

    /**
     * Add a new player.
     *
     * @param god the god.
     */
    public void addNewPlayer(God god) {
        this.players.add(new Player(god, this.board));
    }

    /**
     * Check players lose condition.
     */
    public void checkPlayersLoseCondition() {
        for(Player p : this.players) {
            if(p.checkLoseCondition()) {
                // player lost the game
                p.setSpectator(true);
                this.players.remove(p);
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
     */
    public void executeAction(int playerIndex, Action action) {
        // possible usage of InvalidMoveException
        players.get(playerIndex).doAction(action);
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
}
