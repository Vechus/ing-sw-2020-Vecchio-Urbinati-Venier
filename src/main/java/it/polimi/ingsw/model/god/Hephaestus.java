package it.polimi.ingsw.model.god;

import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Hephaestus extends God {
    private Vector2 posFirstBuild;
    private int counterHephaestusBuilds = 0;

    public Hephaestus(Board board, Player player) {
        super(board, player);
        this.buildBlockValidationFunctions = new ArrayList<>(
                Arrays.asList(GodValidationMethods::isTargetPosWithinBoard,
                        GodValidationMethods::isCellWorkersFree,
                        GodValidationMethods::isTargetPosOnDifferentCell,
                        GodValidationMethods::isTargetPosDomesFree,
                        GodValidationMethods::isTargetPosAdjacent,
                        GodValidationMethods::isBuildingHeightLessThanThree,
                        this::isCellTheSame
                ));

    }

    @Override
    public boolean chooseAction(Action action) {
        if (chosenWorker==null){ chosenWorker=action.getWorker(); }
        if (this.hasMoved) {
            if (counterHephaestusBuilds==0){
                if (action.getType() == ActionType.BUILD) {
                    if (buildBlock(action)) {
                        posFirstBuild=action.getTargetPos();
                        counterHephaestusBuilds++;
                        return true;
                    }
                } else if (action.getType() == ActionType.BUILD_DOME) {
                    if (buildDome(action)) {
                        return true;
                    }
                }
            }else if(counterHephaestusBuilds==1){
                if (action.getType() == ActionType.BUILD) {
                    if (buildBlock(action)) {
                        counterHephaestusBuilds++;
                        return true;
                    }
                }
            }
        } else if (action.getType() == ActionType.MOVE&&counterHephaestusBuilds==0) {
            if (move(action)) {
                this.hasMoved = true;
                return true;
            }
        }if(action.getType()== ActionType.END_TURN ) {
            if (endTurn()) {
                this.hasFinishedTurn = true;
                return true;
            }
        }
        return false;
    }


    public boolean isCellTheSame(Pair<Action, Board> actionBoardPair){
        Action action = actionBoardPair.first();
        if(counterHephaestusBuilds==1 && action.getTargetPos()!=posFirstBuild){
            return false;
        }
        return true;
    }







}