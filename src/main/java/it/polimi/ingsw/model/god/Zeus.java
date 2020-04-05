package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.Vector2;

public class Zeus extends God {
    public Zeus(Board board, Player player) {
        super(board, player);
    }

    @Override
    public boolean isBuildValid(Action action){
        Vector2 pos=action.getTargetPos();

        //check if pos is within board
        if(pos.getY()>=5 || pos.getX()>=5 || pos.getY()<0 || pos.getX()<0){
            return false;
        }
        //check if targeted pos doesn't have any other worker. It also check that you don't build where your worker is.
        if (this.board.getWorker(pos) != null && !(action.getTargetPos().equals(action.getWorker().getPosition()))  ){
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

        return true;
    }

}
