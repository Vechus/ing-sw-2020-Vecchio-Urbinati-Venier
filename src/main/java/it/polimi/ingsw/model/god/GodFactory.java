package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;

/**
 * Creates the god and assigns it to the player and board
 */
public class GodFactory {
    public static God createGod(String godName, Player player, Board board) {
        switch (godName) {
            case "Apollo":
                return new Apollo(board, player);
            case "Artemis":
                return new Artemis(board, player);
            case "Athena":
                return new Athena(board, player);
            case "Atlas":
                return new Atlas(board, player);
            case "Cronus":
                return new Cronus(board, player);
            case "Demeter":
                return new Demeter(board, player);
            case "Hephaestus":
                return new Hephaestus(board, player);
            case "Hera":
                return new Hera(board, player);
            case "Hestia":
                return new Hestia(board, player);
            case "Minotaur":
                return new Minotaur(board, player);
            case "Pan":
                return new Pan(board, player);
            case "Prometheus":
                return new Prometheus(board, player);
            case "Triton":
                return new Triton(board, player);
            case "Zeus":
                return new Zeus(board, player);
            default:
                return new God(board, player);
        }
    }
}
