package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GodTest {

    Board board;
    Player player;
    Vector2 highPos=new Vector2(1,0);
    Vector2 lowPos=new Vector2(1,1);
    Vector2 midPos=new Vector2(0,0);
    Vector2 outsidePos=new Vector2(-1,-1);
    Worker worker;
    God god;

    @BeforeEach
    void setup(){
        board= new Board();
        player=new Player();
        god= new God(board, player);
        board.setHeight(highPos, 2);
        board.setHeight(lowPos,0);
        board.setHeight(midPos,1);
        worker=new Worker (player);
        board.placeWorker(worker, midPos);
    }

    //CHOOSE ACTION
    @Test
    void chooseAction(){

    }

    //MOVE
    @Test
    void moveValid(){
        Action action=new Action (worker, highPos, ActionType.MOVE);
        god.move(action);
        assertTrue(board.getWorker(midPos)==null && board.getWorker(highPos)==worker);
    }
    @Test
    void moveNotValid(){
        board.setComplete(highPos,true);
        Action action=new Action (worker, highPos, ActionType.MOVE);
        god.move(action);
        assertTrue(board.getWorker(midPos)==worker && board.getWorker(highPos)==null);
    }


    // 6 TESTS FOR MOVE
    @Test
    void testIsCellFreeFromWorkes(){
        Worker otherWorker=new Worker (player);
        board.placeWorker(otherWorker, highPos);
        Action action=new Action (worker, otherWorker.getPosition(), ActionType.MOVE);
        assertFalse(god.isMoveValid(action));
    }
    @Test
    void testIsCellFreeFromDomes(){
        board.setComplete(highPos,true);
        Action action=new Action(worker, highPos, ActionType.MOVE);
        assertFalse(god.isMoveValid(action));
    }
    @Test
    void testIsMoveOnBoard(){
        Action action = new Action(worker, outsidePos, ActionType.MOVE);
        assertFalse(god.isMoveValid(action));
    }
    @Test
    void testHasWorkerChanchedPos(){
        Action action=new Action(worker, midPos, ActionType.MOVE);
        assertFalse(god.isMoveValid(action));
    }
    @Test
    void testIsCellAdjacent(){
        Vector2 farPos= new Vector2(2,2);
        Action action =new Action(worker, farPos, ActionType.MOVE);
        assertFalse(god.isMoveValid(action));
    }
    @Test
    void testIsHeightDiffCorrect(){
        worker.setPosition(lowPos);
        Action action = new Action(worker, highPos, ActionType.MOVE);
        assertFalse(god.isMoveValid(action));
    }



    //BUILD BLOCK
    @Test
    void BuildBlockValid(){
        Action action = new Action (worker, highPos, ActionType.BUILD);
        god.buildBlock(action);
        assertTrue(board.getWorker(midPos)==worker && board.getHeight(highPos)==3);
    }
    @Test
    void BuildBlockNotValid(){
        board.setHeight(highPos, 3);
        Action action = new Action (worker, highPos, ActionType.BUILD);
        assertTrue(board.getWorker(midPos)==worker && board.getHeight(highPos)==3);
    }

    //1 TEST FOR BUILD BLOCK
    @Test
    void testIsBuildHeightLessThanThreeWhenBuilding(){
        board.setHeight(highPos, 3);
        Action action=new Action(worker,highPos, ActionType.BUILD);
        assertFalse(god.isBuildBlockValid(action));
    }



    //BUILD DOME
    @Test
    void BuildDomeValid(){
        board.setHeight(highPos, 3);
        Action action = new Action (worker, highPos, ActionType.BUILD_DOME);
        god.buildDome(action);
        assertTrue(board.getWorker(midPos)==worker && board.isComplete(highPos));
    }
    @Test
    void BuildDomeNotValid(){
        Action action = new Action (worker, highPos, ActionType.BUILD_DOME);
        assertTrue(board.getWorker(midPos)==worker && !board.isComplete(highPos));
    }

    //1 TESTS FOR BUILD_DOME
    @Test
    void testIsBuildHeightThreeWhenBuildingDome(){
        board.setHeight(highPos, 2);
        Action action=new Action(worker,highPos, ActionType.BUILD_DOME);
        assertFalse(god.isBuildDomeValid(action));
    }





    @Test
    void testWinConditionValid(){
        board.setHeight(midPos, 2);
        board.setHeight(highPos, 3);
        Action action=new Action(worker, highPos, ActionType.MOVE);
        assertTrue(god.checkWinCondition(action));
    }
    @Test
    void testWinCOnditionNotValid(){
        Action action=new Action(worker, highPos, ActionType.MOVE);
        assertFalse(god.checkWinCondition(action));
    }

    @Test
    void testBeginNewTurnNotValid(){
        god.beginNewTurn();
        assertFalse(god.getHasMoved());
        assertFalse(god.getHasBuilt());
        assertFalse(god.getHasFinishedTurn());
    }

    @Test
    void testEndTurnNotValid(){
        god.beginNewTurn();
        assertFalse(god.endTurn());
    }

    @Test
    void testEndTurnValid(){
        god.beginNewTurn();
        Action actionMove=new Action(worker, highPos, ActionType.MOVE);
        Action actionBuild=new Action(worker, lowPos, ActionType.BUILD);
        assertTrue(god.chooseAction(actionMove));
        assertTrue(god.chooseAction(actionBuild));
        assertTrue(god.endTurn());
    }


    //CHOOSE ACTION TESTS
    @Test
    void testCannotDoAnythingAfterBuilding(){
        Action move = new Action(worker, highPos, ActionType.MOVE);
        assertTrue(god.chooseAction(move));
        Action build = new Action(worker, lowPos, ActionType.BUILD);
        assertTrue(god.chooseAction(build));
        Action secondMove = new Action(worker, lowPos, ActionType.MOVE);
        assertFalse(god.chooseAction(secondMove));
        Action secondBuild = new Action(worker, midPos, ActionType.BUILD);
        assertFalse(god.chooseAction(secondBuild));
    }
}
