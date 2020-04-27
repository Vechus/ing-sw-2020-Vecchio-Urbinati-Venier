package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Hestia extends God {
    List<Function<Pair<Action, Board>, Boolean>> buildBlockValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                    GodValidationMethods::isCellWorkersFree,
                    GodValidationMethods::isTargetPosOnDifferentCell,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isBuildingHeightLessThanThree,
                    this::isBuildOffThePerimeter
            ));
    List<Function<Pair<Action, Board>, Boolean>> buildDomeValidationFunctions = new ArrayList<>(
            Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                    GodValidationMethods::isCellWorkersFree,
                    GodValidationMethods::isTargetPosOnDifferentCell,
                    GodValidationMethods::isTargetPosDomesFree,
                    GodValidationMethods::isTargetPosAdjacent,
                    GodValidationMethods::isBuildingHeightThree,
                    this::isBuildOffThePerimeter
            ));


    int counterHestiaBuilds=0;

    public Hestia(Board board, Player player) {
        super(board, player);
    }


    public  boolean chooseAction (Action action){
        if (chosenWorker==null){ chosenWorker=action.getWorker(); }

        if(this.hasMoved ){
           if(counterHestiaBuilds==0){
               if(action.getType()==Action.ActionType.BUILD&& chosenWorker==action.getWorker()){
                   if(buildBlock(action)) {
                       counterHestiaBuilds++;
                       return true;
                   }
               }else if(action.getType()==Action.ActionType.BUILD_DOME&& chosenWorker==action.getWorker()){
                   if(buildDome(action)) {
                       counterHestiaBuilds++;
                       return true;
                   }
               }


           } else if(counterHestiaBuilds==1){
               if(action.getType()==Action.ActionType.BUILD&& chosenWorker==action.getWorker()){
                   if(buildBlock(action)) {
                       return true;
                   }
               }else if(action.getType()==Action.ActionType.BUILD_DOME&& chosenWorker==action.getWorker()){
                   if(buildDome(action)) {
                       return true;
                   }
               }



           }


        }else if (action.getType()==Action.ActionType.MOVE&& chosenWorker==action.getWorker()) {
            if (move(action)) {
                this.hasMoved = true;
                return true;
            }
        }
        if(action.getType()==Action.ActionType.END_TURN ) {
            if (endTurn()) {
                this.hasFinishedTurn = true;
                return true;
            }
        }
        return false;
    }


    public boolean isBuildOffThePerimeter(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        if(counterHestiaBuilds==1 && (action.getTargetPos().getY()==4 || action.getTargetPos().getX()==4 || action.getTargetPos().getY()==0 || action.getTargetPos().getX()==0)){
            return false;
        }
        return true;
    }



}
