package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.ui.gui.RootPane;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.ui.gui.util.ToastMessage;

public class EndGameUI extends BorderPane {
    PacmanManager manager;
    Button btnSave,btnExit;
    public EndGameUI(PacmanManager manager) {
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

        btnSave = new Button("SAVE");
        btnConfig(btnSave);
        btnExit = new Button("EXIT");
        btnConfig(btnExit);

        VBox vBox = new VBox(btnSave,btnExit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);

        this.setCenter(vBox);


    }

    private void registerHandlers() {

        manager.addPropertyChangeListener(evt -> Platform.runLater(this::update));

        btnSave.setOnAction(actionEvent -> {
            manager.saveScore();
            ToastMessage.show(getScene().getWindow(),"Game Saved!");
            btnExit.fire();
        });

        btnExit.setOnAction( event -> {
            Stage stage = (Stage) this.getScene().getWindow();
            RootPane root = new RootPane(new PacmanManager());
            Scene scene = new Scene(root,1000,1000);
            stage.setScene(scene);
            stage.show();
        });

    }

    private void update() {
        if (manager.getState() != PacmanState.ENDGAME) {
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
