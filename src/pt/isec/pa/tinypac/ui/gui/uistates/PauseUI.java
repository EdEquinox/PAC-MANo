package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.ui.gui.GameUI;
import pt.isec.pa.tinypac.ui.gui.Top5UI;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

public class PauseUI extends BorderPane {
    PacmanManager manager;
    Button btnSave,btnResume,btnExit;

    public PauseUI(PacmanManager manager) {
        this.manager = manager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        this.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("background.png"),
                                BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(1,1,true,true,false,false)
                        )
                )
        );

        btnResume = new Button("RESUME");
        btnConfig(btnResume);
        btnSave = new Button("SAVE");
        btnConfig(btnSave);
        btnExit = new Button("EXIT");
        btnConfig(btnExit);

        VBox vBox = new VBox(btnResume,btnSave,btnExit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);

        this.setCenter(vBox);

    }

    private void registerHandlers() {

        manager.addPropertyChangeListener(evt -> Platform.runLater(this::update));

        btnResume.setOnAction(actionEvent -> {
            System.out.println("resume");
        });

        btnSave.setOnAction(actionEvent -> {
            System.out.println("save");
        });

        btnExit.setOnAction( event -> {
            System.out.println("exit");
        });

    }

    private void update() {
        if (manager.getState() != PacmanState.PAUSE) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }

    private void btnConfig(Button btn){
        btn.setMinWidth(200);
        btn.setMinHeight(50);
        btn.setStyle("-fx-background-color: #b38b0f; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-pref-width: 120px; -fx-pref-height: 40px; -fx-background-radius: 20px; -fx-font-family: 'Comic Sans MS'; -fx-font-weight: bold");
    }
}
