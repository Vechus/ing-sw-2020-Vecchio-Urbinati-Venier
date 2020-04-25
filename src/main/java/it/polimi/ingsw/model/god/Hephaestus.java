package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

public class Hephaestus extends God {
    private Vector2 posFirstBuild;
    private int counterHephaestusBuilds = 0;

    public Hephaestus(Board board, Player player) {
        super(board, player);
    }

    @Override
    public boolean chooseAction(Action action) {
        if (this.hasMoved) {
            if (counterHephaestusBuilds==0){
                if (action.getType() == Action.ActionType.BUILD) {
                    if (build(action)) {
                        posFirstBuild=action.getTargetPos();
                        counterHephaestusBuilds++;
                        return true;
                    }
                } else if (action.getType() == Action.ActionType.BUILD_DOME) {
                    if (buildDome(action)) {
                        hasFinishedTurn = true;
                        return true;
                    }
                }


            }else if(counterHephaestusBuilds==1){
                if (action.getType() == Action.ActionType.BUILD) {
                    if (build(action)) {
                        hasFinishedTurn = true;
                        return true;
                    }
                }
            }

        } else if (action.getType() == Action.ActionType.MOVE) {
            if (move(action)) {
                chosenWorker=action.getWorker();
                this.hasMoved = true;
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean isBuildValid(Action action){

        if(counterHephaestusBuilds==1 && action.getTargetPos()!=posFirstBuild){
            return false;
        }


        return true;
    }







}