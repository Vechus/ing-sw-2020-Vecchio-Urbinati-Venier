package it.polimi.ingsw.view;

import it.polimi.ingsw.client.ClientAction;
import it.polimi.ingsw.client.ClientBoard;
import it.polimi.ingsw.connection.ActionMessage;
import it.polimi.ingsw.connection.ActionRequestMessage;
import it.polimi.ingsw.connection.BoardStateMessage;
import it.polimi.ingsw.connection.Message;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.util.Action;
import it.polimi.ingsw.util.ActionType;
import it.polimi.ingsw.util.Vector2;

import java.util.List;

public class RemoteView extends View {
    public RemoteView(Server server, Model model, ClientConnection clientConnection, int playerId) {
        super(server, model, clientConnection, playerId);
    }

    @Override
    public void showModel(Model model) {
        BoardStateMessage message = new BoardStateMessage();
        ClientBoard clientBoard = new ClientBoard();
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++){
                Vector2 pos = new Vector2(i, j);
                clientBoard.setHeight(pos, model.getBoard().getHeight(pos));
                clientBoard.setDome(pos, model.getBoard().isComplete(pos));
            }
        for(int pid=0;pid<model.getNumberOfPlayers();pid++){
            Player player = model.getPlayer(pid);
            clientBoard.addPlayerName(player.getPlayerName());
            for(int i=0;i<player.getNumWorkers();i++)
                clientBoard.setWorkerPlayer(player.getWorker(i).getPosition(), pid);
        }
        message.setGameState(clientBoard);
        clientConnection.asyncSend(message);
    }

    @Override
    public void onModelChange(Model model) {
        super.onModelChange(model);
    }

    @Override
    public void notifyActionRequired(List<ActionType> allowed) {
        ActionRequestMessage message = new ActionRequestMessage();
        message.setAllowedActions(allowed);
        clientConnection.asyncSend(message);
    }

    @Override
    public void notifyGameOver(String winner) {
        Message msg = new Message();
        msg.setMessageType(Message.MessageType.GAME_END);
        msg.setPayload(winner);
        clientConnection.asyncSend(msg);
    }

    @Override
    public void onMessageReceived(Message message) {
        System.out.println("[REMOTEVIEW] Processing message");
        if(message instanceof ActionMessage){
            ActionMessage actionMessage = (ActionMessage) message;
            ClientAction clientAction = actionMessage.getAction();

            Action action;
            if(clientAction.getType() == ActionType.PLACE_WORKER || clientAction.getType() == ActionType.END_TURN){
                action = new Action(null, clientAction.getTo(), clientAction.getType());
            }
            else {
                Worker worker = model.getBoard().getWorker(clientAction.getFrom());
                if (worker == null || !worker.getOwner().equals(model.getPlayer(playerId))) {
                    Message error = new Message();
                    error.setStatus(Message.Status.ERROR);
                    error.setErrorType(Message.ErrorType.MOVE_INVALID);
                    error.setPayload("Worker in selected position does not belong to player");
                    clientConnection.asyncSend(error);
                    return;
                }
                action = new Action(worker, clientAction.getTo(), clientAction.getType());
            }

            boolean res = processAction(action);

            Message clientMess = new Message();
            if(!res){
                clientMess.setStatus(Message.Status.ERROR);
                clientMess.setErrorType(Message.ErrorType.MOVE_INVALID);
                clientMess.setPayload("Move is invalid! Enter a new move.");
            }
            else clientMess.setStatus(Message.Status.OK);
            clientConnection.asyncSend(clientMess);
        }
        else System.out.println(message.toString());
    }
}
