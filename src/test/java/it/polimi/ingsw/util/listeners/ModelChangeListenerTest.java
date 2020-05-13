package it.polimi.ingsw.util.listeners;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.util.Vector2;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelChangeListenerTest {
    Model model;
    ModelChangeListener listener;

    @BeforeEach
    void setup(){
        model = new Model();
        listener = new RemoteView(null);
    }

    @Test
    void testModelUpdate(){

    }
}
