package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

public class StatusUI extends HBox {
    PacmanManager manager;
    Label lblPoints, lblLives, lblState, lblTime, lblLevel,lblCoins;

    public StatusUI(PacmanManager manager) {
        this.manager = manager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));
        setBackground(
                new Background(
                        new BackgroundImage(ImageManager.getImage("background.png"),
                                BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(1,1,true,true,false,false))
                )
        );

        lblLives = new Label();
        lblPoints = new Label();
        lblState = new Label();
        lblTime = new Label();
        lblLevel = new Label();
        lblCoins = new Label();

        lblLives.setStyle("-fx-text-fill: white");
        lblPoints.setStyle("-fx-text-fill: white");
        lblState.setStyle("-fx-text-fill: white");
        lblTime.setStyle("-fx-text-fill: white");
        lblLevel.setStyle("-fx-text-fill: white");
        lblCoins.setStyle("-fx-text-fill: white");
        lblCoins.setPadding(new Insets(10));
        lblLevel.setPadding(new Insets(10));
        lblTime.setPadding(new Insets(10));
        lblLives.setPadding(new Insets(10));
        lblPoints.setPadding(new Insets(10));
        lblState.setPadding(new Insets(10));

        this.getChildren().addAll(lblLives,lblPoints,lblState,lblTime,lblLevel,lblCoins);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(evt -> Platform.runLater(this::update));
    }

    private void update() {

        if ((manager.getState() != PacmanState.MOVING) && (manager.getState() != PacmanState.LUNCH_TIME)&& (manager.getState() != PacmanState.INIT_LEVEL)) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);

        lblLives.setText("Vidas: "+manager.getNLives());
//        lblPoints.setText("Pontos: " + manager.getScore());
        lblPoints.setText("Pontos: " + manager.getPacman().getInitialPosition());
        lblState.setText("Estado:  "+manager.getPacman().getCurrentDirection().toString());
        lblLevel.setText("Nivel:  "+manager.getLevel());
        lblCoins.setText("Moedas:  "+manager.getCoins().toString());
        if (manager.getState()==PacmanState.LUNCH_TIME)lblTime.setText("Tempo super: " + manager.getTime());

    }
}
