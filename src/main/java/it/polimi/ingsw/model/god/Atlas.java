package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;

public class Atlas extends God {
    public Atlas(Board board, Player player) {
        super(board, player);
    }

    @Override
    public boolean isBuildDomeValide(Action action){

        //check if pos is within board
        if(action.getTargetPos().getY()>=5 || action.getTargetPos().getX()>=5 || action.getTargetPos().getY()<0 || action.getTargetPos().getX()<0){
            return false;
        }
        //check if targeted pos doesn't have any other worker
        if (this.board.getWorker(action.getTargetPos()) != null){
            return false;
        }


        if(this.board.isComplete(action.getTargetPos())){
            return false;
        }

        //check worker is building within their range
        if(action.getWorker().getPosition().getX() - action.getTargetPos().getX()>1 || action.getWorker().getPosition().getY()-action.getTargetPos().getY()>1){
            return false;
        }

        return true;
    }
}
