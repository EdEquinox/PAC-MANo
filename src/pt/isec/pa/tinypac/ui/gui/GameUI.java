package pt.isec.pa.tinypac.ui.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.ui.gui.uistates.*;

import java.io.File;

public class GameUI extends BorderPane {
    StackPane estadosSobrepostos;
    PacmanManager manager;
    GameEngine gameEngine;
    public GameUI(PacmanManager manager) {
        this.manager = manager;
        gameEngine = new GameEngine();
        gameEngine.registerClient((g,t)->manager.evolve());
        gameEngine.start(300);


        createViews();
        registerHandlers();
        update();

        //gameEngine.waitForTheEnd();
    }

    private void createViews() {

        estadosSobrepostos = new StackPane(
                new InitLeveUI(manager),
                new EndGameUI(manager),
                new LunchTimeUI(manager),
                new MovingUI(manager),
                new PauseUI(manager)
        );

        this.setCenter(estadosSobrepostos);

        this.setTop(new StatusUI(manager));
    }

    private void registerHandlers() {

    }

    private void update() {

        //if (manager.getState()== PacmanState.INIT_LEVEL) gameEngine.stop();

    }

    //TODO como é que paro o game engine porque quando mudo de nivel dá raia

}
