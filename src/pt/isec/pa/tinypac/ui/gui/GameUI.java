package pt.isec.pa.tinypac.ui.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.ui.gui.uistates.*;

public class GameUI extends BorderPane {
    StackPane estadosSobrepostos;
    PacmanManager manager;
    public GameUI(PacmanManager manager) {
        this.manager = manager;

        GameEngine gameEngine = new GameEngine();
        gameEngine.registerClient(manager);
        gameEngine.start(500);


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

    }
}
