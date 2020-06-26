package it.polimi.ingsw.model;


import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.model.god.GodFactory;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.util.Vector2;
import it.polimi.ingsw.util.listeners.ModelChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Model.
 */
public class Model {
    private Board board;
    private List<Player> players;
    private List<ModelChangeListener> listeners;
    private int curPlayer = 0;

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
     * Add a new player to the model
     *
     * @param godName the name of the player's god
     * @param name the name of the player
     * @return the integer id of the generated player
     */
    public int addNewPlayer(String godName, String name) {
        Player player = new Player(board);
        player.setPlayerName(name);
        God god = GodFactory.createGod(godName, player, board);
        god.setPlayer(player);
        player.setPlayerGod(god);
        this.players.add(player);
        int pid = this.players.size()-1;
        player.setPlayerId(pid);
        return pid;
    }

    /**
     * Check the player's lose condition.
     */
    public void checkPlayersLoseCondition() {
        for (Player p : this.players) {
            if (p.checkLoseCondition()) {
                System.out.println(p.getPlayerName()+" lost!");
                // player lost the game
                p.setSpectator(true);
            }
        }
    }

    /**
     * Checks if everybody is stalled. If true is returned, game ends as a tie.
     *
     * @return  boolean
     */
    public boolean checkGameOver() {
        this.checkPlayersLoseCondition();
        int activePlayers = 0;
        for(Player p : this.players)
            if(!p.isSpectator()) activePlayers++;
        return activePlayers == 0;
    }

    /**
     * Execute action.
     *
     * @param playerIndex : the player index of the action
     * @param action :  the action made by the player
     *
     * @return was the action executed successfully
     */
    public boolean executeAction(int playerIndex, Action action) {
        return players.get(playerIndex).doAction(action);
    }

    /**
     * Begins a new turn.
     *
     * @param pid : the player's pid
     */
    public void beginNewTurn(int pid) {
        players.get(pid).beginNewTurn();
    }

    /**
     * Sends a message to the views, saying that the game has ended
     *
     * @param winnerPid the id of the winner
     */
    public void sendGameOver(int winnerPid){
        for(ModelChangeListener listener : listeners)
            listener.notifyGameOver(winnerPid == -1 ? "" : players.get(winnerPid).getPlayerName());
    }

    /**
     * Getter of player
     *
     * @param index : the index of the player
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
     * Getter of board.
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the current Player
     * @return int : the index of the current player
     */
    public int getCurPlayer() {
        return this.curPlayer;
    }

    /**
     *
     */

    public void incrementCurPlayer(){
        int orig = curPlayer;
        do{
            curPlayer = (curPlayer + 1) % getNumberOfPlayers();
        } while(players.get(curPlayer).isSpectator() && curPlayer != orig);
    }

    /**
     *
     * @param playerId
     * @return
     */
    public boolean isPlayersTurn(int playerId){
        return playerId == this.curPlayer;
    }

    /**
     * Places the worker on the board
     * @param pid : the number used to identify the  worker
     * @param initPos The position where the worker is placed
     * @return
     */
    public boolean placeWorker(int pid, Vector2 initPos) {
        if(board.getWorker(initPos) != null) return false;
        Worker worker = new Worker(players.get(pid));
        if(!board.placeWorker(worker, initPos)) return false;
        players.get(pid).addWorker(worker);
        return true;
    }

    /**
     * Sends the new status to the clients, every time an Action is completed successfully
     */
    public void updateClientModel() {
        System.out.println("[MODEL] Notifying listeners of board update");
        for(ModelChangeListener listener : listeners)
            listener.onModelChange(this);
    }
}
