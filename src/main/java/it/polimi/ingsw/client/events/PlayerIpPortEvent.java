package it.polimi.ingsw.client.events;

import javafx.event.EventType;
import javafx.util.Pair;

public class PlayerIpPortEvent extends CustomEvent {
    public static final EventType<CustomEvent> PLAYER_INPUT_EVENT_TYPE = new EventType<>(CUSTOM_EVENT_TYPE, "PlayerInput");

    private final Pair<String, Integer> param;

    public PlayerIpPortEvent(Pair<String, Integer> param) {
        super(PLAYER_INPUT_EVENT_TYPE);
        this.param = param;
    }

    public String getIp() {
        return param.getKey();
    }

    public Integer getPort() {
        return param.getValue();
    }

    @Override
    public void invokeHandler(CustomEventHandler handler) {
        handler.onPlayerIpPortChange(param);
    }
}
