package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.effect.AthenaEffect;
import it.polimi.ingsw.model.god.effect.HeraEffect;
import it.polimi.ingsw.util.ActionType;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Creates the god and assigns it to the player and board
 */
public class GodFactory {
    private static TurnActionGraph createDefaultGraph(){
        TurnActionGraph actionGraph = new TurnActionGraph();
        int movedState = actionGraph.addState();
        int builtState = actionGraph.addState();
        actionGraph.addTransition(actionGraph.INITIAL_STATE_IDX, movedState, ActionType.MOVE);
        actionGraph.addTransition(movedState, builtState, ActionType.BUILD);
        actionGraph.addTransition(movedState, builtState, ActionType.BUILD_DOME);
        actionGraph.addTransition(builtState, actionGraph.FINAL_STATE_IDX, ActionType.END_TURN);
        return actionGraph;
    }

    public static God createGod(String godName, Board board, Player player) {
        God god = new God(board, player);
        TurnActionGraph actionGraph = createDefaultGraph();
        switch (godName) {
            case "Apollo":
                /*
                 *Simple god: Your Worker may
                 * move into an opponent Worker’s
                 * space by forcing their Worker to
                 * the space yours just vacated.
                 */
                god.setMoveValidationMethods(new ArrayList<>(
                        Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                                GodValidationMethods::isTargetPosWithinBoard,
                                GodValidationMethods::isTargetPosOnDifferentCell,
                                GodValidationMethods::isTargetPosDomesFree,
                                GodValidationMethods::isTargetPosAdjacent,
                                GodValidationMethods::isMoveHeightLessThanOne
                        )));
                god.setMoveMethod(GodMoveMethods::swapMove);
                break;
            case "Artemis":
                /*
                 * Simple god: Your Move: Your Worker may
                 * move one additional time, but not
                 * back to its initial space.
                 */
                god.setMoveValidationMethods(new ArrayList<>(
                        Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                                GodValidationMethods::isTargetPosWithinBoard,
                                GodValidationMethods::isCellWorkersFree,
                                GodValidationMethods::isTargetPosOnDifferentCell,
                                GodValidationMethods::isTargetPosDomesFree,
                                GodValidationMethods::isTargetPosAdjacent,
                                GodValidationMethods::isMoveHeightLessThanOne,
                                GodValidationMethods::isCellDifferentFromInitialSpace
                        )));
                int artSecondMovedState = actionGraph.addState();
                int artMovedState = actionGraph.getNextState(actionGraph.INITIAL_STATE_IDX, ActionType.MOVE);
                int artBuiltState = actionGraph.getNextState(artMovedState, ActionType.BUILD);
                actionGraph.addTransition(artMovedState, artSecondMovedState, ActionType.MOVE);
                actionGraph.addTransition(artSecondMovedState, artBuiltState, ActionType.BUILD);
                actionGraph.addTransition(artSecondMovedState, artBuiltState, ActionType.BUILD_DOME);
                break;
            case "Athena":
                /*
                 * Simple god: Opponent’s Turn: If one of your
                 * Workers moved up on your last
                 * turn, opponent Workers cannot
                 * move up this turn.
                 */
                board.addEffect(new AthenaEffect(board, player));
                god.setMoveMethod(GodMoveMethods::activateEffectOnRiseMove);
                break;
            case "Atlas":
                /*
                 * Simple god: Your Build: Your Worker may
                 * build a dome at any level.
                 */
                god.setBuildDomeValidationMethods(new ArrayList<>(
                        Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                                GodValidationMethods::isTargetPosWithinBoard,
                                GodValidationMethods::isCellWorkersFree,
                                GodValidationMethods::isTargetPosOnDifferentCell,
                                GodValidationMethods::isTargetPosDomesFree,
                                GodValidationMethods::isTargetPosAdjacent
                        )));
                break;
            case "Cronus":
                /*
                 * Advanced god: Win Condition: You also win
                 * when there are at least five
                 * Complete Towers on the board.
                 */
                god.setWinValidationFunctions(new ArrayList<>(
                        Arrays.asList(GodWinMethods::isWinPermittedByEffects,
                                GodWinMethods::cronusWinCondition
                        )));
                break;
            case "Demeter":
                /*
                 * Simple god: Your Build: Your Worker may
                 * build one additional time, but not
                 * on the same space.
                 */
                god.setBuildBlockValidationMethods(new ArrayList<>(
                        Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                                GodValidationMethods::isTargetPosWithinBoard,
                                GodValidationMethods::isCellWorkersFree,
                                GodValidationMethods::isTargetPosOnDifferentCell,
                                GodValidationMethods::isTargetPosDomesFree,
                                GodValidationMethods::isTargetPosAdjacent,
                                GodValidationMethods::isBuildingHeightLessThanThree,
                                GodValidationMethods::isCellDifferentWhenBuilding
                        )));
                god.setBuildDomeValidationMethods(new ArrayList<>(
                        Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                                GodValidationMethods::isTargetPosWithinBoard,
                                GodValidationMethods::isCellWorkersFree,
                                GodValidationMethods::isTargetPosOnDifferentCell,
                                GodValidationMethods::isTargetPosDomesFree,
                                GodValidationMethods::isTargetPosAdjacent,
                                GodValidationMethods::isBuildingHeightThree,
                                GodValidationMethods::isCellDifferentWhenBuilding
                        )));
                int demMovedState = actionGraph.getNextState(actionGraph.INITIAL_STATE_IDX, ActionType.MOVE);
                int demBuiltState = actionGraph.getNextState(demMovedState, ActionType.BUILD);
                int demSecondBuildState = actionGraph.addState();
                actionGraph.addTransition(demBuiltState, demSecondBuildState, ActionType.BUILD);
                actionGraph.addTransition(demBuiltState, demSecondBuildState, ActionType.BUILD_DOME);
                actionGraph.addTransition(demSecondBuildState, actionGraph.FINAL_STATE_IDX, ActionType.END_TURN);
                break;
            case "Hephaestus":
                /*
                 * Simple god: Your Build: Your Worker may
                 * build one additional block (not
                 * dome) on top of your first block.
                 */
                god.setBuildBlockValidationMethods(new ArrayList<>(
                        Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                                GodValidationMethods::isTargetPosWithinBoard,
                                GodValidationMethods::isCellWorkersFree,
                                GodValidationMethods::isTargetPosOnDifferentCell,
                                GodValidationMethods::isTargetPosDomesFree,
                                GodValidationMethods::isTargetPosAdjacent,
                                GodValidationMethods::isBuildingHeightLessThanThree,
                                GodValidationMethods::isCellSameWhenBuilding
                        )));
                int hepMovedState = actionGraph.getNextState(actionGraph.INITIAL_STATE_IDX, ActionType.MOVE);
                int hepBuiltState = actionGraph.getNextState(hepMovedState, ActionType.BUILD);
                int hepSecondBuildState = actionGraph.addState();
                actionGraph.addTransition(hepBuiltState, hepSecondBuildState, ActionType.BUILD);
                actionGraph.addTransition(hepBuiltState, hepSecondBuildState, ActionType.BUILD_DOME);
                actionGraph.addTransition(hepSecondBuildState, actionGraph.FINAL_STATE_IDX, ActionType.END_TURN);
                break;
            case "Hera":
                /*
                 * Advanced god: Opponent’s Turn: An opponent
                 * cannot win by moving into a
                 * perimeter space.
                 */
                board.addEffect(new HeraEffect(board, player));
                break;
            case "Hestia":
                /*
                 * Advanced god: Your Build: Your Worker may
                 * build one additional time, but this
                 * cannot be on a perimeter space.
                 */
                god.setBuildBlockValidationMethods((
                        Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                                GodValidationMethods::isTargetPosWithinBoard,
                                GodValidationMethods::isCellWorkersFree,
                                GodValidationMethods::isTargetPosOnDifferentCell,
                                GodValidationMethods::isTargetPosDomesFree,
                                GodValidationMethods::isTargetPosAdjacent,
                                GodValidationMethods::isBuildingHeightLessThanThree,
                                GodValidationMethods::isBuildOffThePerimeter
                        )));
                god.setBuildDomeValidationMethods(new ArrayList<>(
                        Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                                GodValidationMethods::isTargetPosWithinBoard,
                                GodValidationMethods::isCellWorkersFree,
                                GodValidationMethods::isTargetPosOnDifferentCell,
                                GodValidationMethods::isTargetPosDomesFree,
                                GodValidationMethods::isTargetPosAdjacent,
                                GodValidationMethods::isBuildingHeightThree,
                                GodValidationMethods::isBuildOffThePerimeter
                        )));
                int hesMovedState = actionGraph.getNextState(actionGraph.INITIAL_STATE_IDX, ActionType.MOVE);
                int hesBuiltState = actionGraph.getNextState(hesMovedState, ActionType.BUILD);
                int hesSecondBuildState = actionGraph.addState();
                actionGraph.addTransition(hesBuiltState, hesSecondBuildState, ActionType.BUILD);
                actionGraph.addTransition(hesBuiltState, hesSecondBuildState, ActionType.BUILD_DOME);
                actionGraph.addTransition(hesSecondBuildState, actionGraph.FINAL_STATE_IDX, ActionType.END_TURN);
                break;
            case "Minotaur":
                /*
                 * Simple god: Your Move: Your Worker may
                 * move into an opponent Worker’s
                 * space, if their Worker can be
                 * forced one space straight backwards to an
                 * unoccupied space at any level.
                 */
                god.setMoveValidationMethods(new ArrayList<>(
                        Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                                GodValidationMethods::isTargetPosWithinBoard,
                                GodValidationMethods::isTargetPosOnDifferentCell,
                                GodValidationMethods::isTargetPosDomesFree,
                                GodValidationMethods::isTargetPosAdjacent,
                                GodValidationMethods::isMoveHeightLessThanOne,
                                GodValidationMethods::isPushPossible
                        )));
                god.setMoveMethod(GodMoveMethods::pushMove);
                break;
            case "Pan":
                /*
                 * Simple god: Win Condition: You also win if
                 * your Worker moves down two or
                 * more levels.
                 */
                god.setWinValidationFunctions(new ArrayList<>(
                        Arrays.asList(GodWinMethods::isWinPermittedByEffects,
                                GodWinMethods::panWinCondition
                        )));
                break;
            case "Prometheus":
                /*
                 * Simple god: Your Turn: If your Worker does
                 * not move up, it may build both
                 * before and after moving.
                 */
                god.setMoveValidationMethods(new ArrayList<>(
                        Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                                GodValidationMethods::isTargetPosWithinBoard,
                                GodValidationMethods::isCellWorkersFree,
                                GodValidationMethods::isTargetPosOnDifferentCell,
                                GodValidationMethods::isTargetPosDomesFree,
                                GodValidationMethods::isTargetPosAdjacent,
                                GodValidationMethods::isMoveHeightLessThanOne,
                                GodValidationMethods::noUpIfPrebuilt
                        )));
                int proPreBuiltState = actionGraph.addState();
                actionGraph.addTransition(actionGraph.INITIAL_STATE_IDX, proPreBuiltState, ActionType.BUILD);
                actionGraph.addTransition(actionGraph.INITIAL_STATE_IDX, proPreBuiltState, ActionType.BUILD_DOME);
                int movedState = actionGraph.getNextState(actionGraph.INITIAL_STATE_IDX, ActionType.MOVE);
                actionGraph.addTransition(proPreBuiltState, movedState, ActionType.MOVE);
                break;
            case "Triton":
                /*
                 * Advanced God: Your Move: Each time
                 * your Worker moves into a perimeter space,
                 * it may immediately move again.
                 */
                god.setMoveValidationMethods(new ArrayList<>(
                        Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                                GodValidationMethods::isTargetPosWithinBoard,
                                GodValidationMethods::isCellWorkersFree,
                                GodValidationMethods::isTargetPosOnDifferentCell,
                                GodValidationMethods::isTargetPosDomesFree,
                                GodValidationMethods::isTargetPosAdjacent,
                                GodValidationMethods::isMoveHeightLessThanOne,
                                GodValidationMethods::isMoveOnPerimeter
                        )));
                int triMovedState = actionGraph.getNextState(actionGraph.INITIAL_STATE_IDX, ActionType.MOVE);
                actionGraph.addTransition(triMovedState, triMovedState, ActionType.MOVE);
                break;
            case "Zeus":
                /*
                 * Advanced god: Your Build: Your Worker may
                 * build a block under itself.
                 */
                god.setBuildBlockValidationMethods(new ArrayList<>(
                        Arrays.asList(GodValidationMethods::isActionPermittedByEffects,
                                GodValidationMethods::isTargetPosWithinBoard,
                                GodValidationMethods::isDifferentCellFree,
                                GodValidationMethods::isTargetPosDomesFree,
                                GodValidationMethods::isTargetPosAdjacent,
                                GodValidationMethods::isBuildingHeightLessThanThree
                        )));
                break;
        }
        god.setName(godName);
        god.setActionGraph(actionGraph);
        return god;
    }
}
