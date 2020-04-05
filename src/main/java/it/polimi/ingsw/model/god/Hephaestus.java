package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.util.Vector2;

public class Hephaestus extends God {
    private Worker firstWorker;
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
                        firstWorker=action.getWorker();
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
                this.hasMoved = true;
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean isBuildValid(Action action){
        Vector2 pos=action.getTargetPos();

        //check if pos is within board
        if(pos.getY()>=5 || pos.getX()>=5 || pos.getY()<0 || pos.getX()<0){
            return false;
        }
        //check if targeted pos doesn't have any other worker. It also check that you don't build where your worker is.
        if (this.board.getWorker(pos) != null){
            return false;
        }


        if(this.board.isComplete(pos)){
            return false;
        }
        if (this.board.getHeight(pos)>=3){
            return false;
        }

        //check worker is building within their range
        if(action.getWorker().getPosition().getX() - pos.getX()>1 || action.getWorker().getPosition().getY()-pos.getY()>1){
            return false;
        }

        if(counterHephaestusBuilds==1 && action.getTargetPos()!=posFirstBuild){
            return false;
        }
        if (counterHephaestusBuilds==1 && !(firstWorker.equals(action.getWorker())) ){return false;}

        return true;
    }







}