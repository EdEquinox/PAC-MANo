package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.ui.gui.RootPane;
import pt.isec.pa.tinypac.ui.gui.SaveScoreDialog;
import pt.isec.pa.tinypac.ui.gui.resources.CSSManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.ui.gui.util.ToastMessage;

public class EndGameUI extends BorderPane {
    PacmanManager manager;
    Button btnSave,btnExit;
    Label lblScore;
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

        lblScore = new Label();
        btnSave = new Button("SAVE");
        btnExit = new Button("EXIT");
        lblScore.getStyleClass().add("lblscore");
        lblScore.setAlignment(Pos.CENTER);

        CSSManager.applyCSS(this,"styles.css");

        VBox vBox = new VBox(lblScore,btnSave,btnExit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);

        this.setCenter(vBox);


    }

    private void registerHandlers() {

        manager.addPropertyChangeListener(evt -> Platform.runLater(this::update));

        btnSave.setOnAction(actionEvent -> {
            saveScore();
            ToastMessage.show(getScene().getWindow(),"Game Saved!");
            btnExit.fire();
        });

        btnExit.setOnAction( event -> {
            Stage stage = (Stage) this.getScene().getWindow();
            RootPane root = new RootPane(new PacmanManager());
            Scene scene = new Scene(root,800,600);
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
        lblScore.setText("Score: "+manager.getScore());
    }

    private void saveScore() {

        Stage popUp = new Stage();
        Scene scene = new Scene(new SaveScoreDialog(manager),400,100);

        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setTitle("SAVE");
        popUp.setScene(scene);
        popUp.setMinWidth(300);
        popUp.setMinHeight(200);
        popUp.getIcons().addAll(ImageManager.getImage("pacman_right.png"));
        popUp.showAndWait();
    }
}
