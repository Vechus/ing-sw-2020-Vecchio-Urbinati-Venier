package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.ClientCLI;
import it.polimi.ingsw.client.listeners.ClientStageListener;
import it.polimi.ingsw.connection.Handshake;
import it.polimi.ingsw.connection.Message;
import it.polimi.ingsw.util.ConsoleColor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Scanner;

public class ClientController {
    private ClientStageEnum state;
    private final ClientCLI clientCLI;
    private final RemoteClient remoteClient;
    private int gameID;
    private String playerName;
    private boolean isHost;
    private List<ClientStageListener> stageListeners;

    public ClientController(RemoteClient remoteClient) {
        this.remoteClient = remoteClient;
        this.state = ClientStageEnum.MAIN_MENU;
        this.clientCLI = new ClientCLI(this);
    }

    public void handleIn(Object inputObject) {
        ClientStageEnum s = getState();
        if(s == ClientStageEnum.WAITING_HANDSHAKE) {
            if(!(inputObject instanceof Handshake)) throw new IllegalArgumentException();
            handleWaitHandshake(inputObject);
        } else if(s == ClientStageEnum.LOBBY) {
            handleLobby(inputObject);
        }
    }

    public void addClientStageListener(ClientStageListener listener) {
        stageListeners.add(listener);
    }

    private void executeClientStageListeners(ClientStageEnum stage) {
        for(ClientStageListener csl : stageListeners) {
            csl.onClientStageChange(stage);
        }
    }

    public void constructHandshake(boolean host) {
        Handshake handshake = new Handshake();
        handshake.setType((host ? Handshake.handshakeType.HOST : Handshake.handshakeType.JOIN));
        handshake.setPlayerName(getPlayerName());
        handshake.setGameId(getGameID());
        remoteClient.sendObject(remoteClient.getSocketOut(), handshake);
        System.out.println(ConsoleColor.GREEN + "Waiting handshake from server...");
    }

    public ClientStageEnum getState() {
        return state;
    }

    public void setState(ClientStageEnum stage) {
        this.state = stage;
        executeClientStageListeners(stage);
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public void handleWaitHandshake(Object inputObject) {
        Handshake ans = (Handshake) inputObject;
        if (ans.getStatus() == Message.status.OK) {
            setState(ClientStageEnum.LOBBY);
        } else if (ans.getStatus() == Message.status.ERROR) {
            if (ans.getErrorType() == Message.errorType.GAME_ID_INVALID_ERROR) {
                System.out.println(ConsoleColor.RED_UNDERLINED + "Invalid game ID.");
                setState(ClientStageEnum.NET_MENU);
            } else if (ans.getErrorType() == Message.errorType.LOBBY_FULL_ERROR) {
                System.out.println(ConsoleColor.RED_UNDERLINED + "Lobby full.");
                setState(ClientStageEnum.NET_MENU);
            }
        }
    }

    public void handleLobby(Object inputObject) {
        System.out.println("Arrived packet, TODO: handle this");
    }
}
