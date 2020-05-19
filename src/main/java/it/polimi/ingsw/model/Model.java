package it.polimi.ingsw.model;


import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.model.god.GodFactory;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.util.ActionType;
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
    int curPlayer = 0;

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
     * Check players lose condition.
     */
    public void checkPlayersLoseCondition() {
        for (Player p : this.players) {
            if (p.checkLoseCondition()) {
                // player lost the game
                p.setSpectator(true);
            }
        }
    }

    /**
     * Check game over boolean.
     *
     * @return the boolean
     */
    public boolean checkGameOver() {
        System.out.println("Is everyone dead?");
        this.checkPlayersLoseCondition();
        int activePlayers = 0;
        for(Player p : this.players)
            if(!p.isSpectator()) activePlayers++;
        return activePlayers <= 1;
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
        if(res) updateClientModel();
        return res;
    }

    /**
     * Begin a new turn.
     *
     * @param pid the player's pid
     */
    public void beginNewTurn(int pid) {
        players.get(pid).beginNewTurn();
    }

    /**
     * Sends an ActionRequest message to the appropriate player
     * @param pid the id of the player to notify
     */
    public void sendActionRequest(int pid){
        List<ActionType> allowedActions = players.get(pid).getPlayerGod().getAllowedActions();
        if(pid < listeners.size())
            listeners.get(pid).notifyActionRequired(allowedActions);
    }

    /**
     * Sends an ActionRequest to the player with PLACE_WORKER, thus asking the player to place a worker
     *
     * @param pid the id of the player to notify
     */
    public void sendPlaceRequest(int pid){
        List<ActionType> allowedActions = new ArrayList<>();
        allowedActions.add(ActionType.PLACE_WORKER);
        if(pid < listeners.size())
            listeners.get(pid).notifyActionRequired(allowedActions);
    }

    /**
     * Sends a message to the views, saying that the game has ended
     *
     * @param winnerPid the id of the winner
     */
    public void sendGameOver(int winnerPid){
        for(ModelChangeListener listener : listeners)
            listener.notifyGameOver(players.get(winnerPid).getPlayerName());
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

    public int getCurPlayer() {
        return this.curPlayer;
    }

    public void incrementCurPlayer(){
        int orig = curPlayer;
        do{
            curPlayer = (curPlayer + 1) % getNumberOfPlayers();
        } while(players.get(curPlayer).isSpectator() && curPlayer != orig);
    }

    public boolean isPlayersTurn(int playerId){
        return playerId == this.curPlayer;
    }

    public boolean placeWorker(int pid, Vector2 initPos) {
        if(board.getWorker(initPos) != null) return false;
        Worker worker = new Worker(players.get(pid));
        if(!board.placeWorker(worker, initPos)) return false;
        players.get(pid).addWorker(worker);
        updateClientModel();
        return true;
    }

    public void updateClientModel() {
        System.out.println("[MODEL] Notifying listeners of board update");
        for(ModelChangeListener listener : listeners)
            listener.onModelChange(this);
    }
}
