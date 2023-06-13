package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.ui.gui.RootPane;
import pt.isec.pa.tinypac.ui.gui.SaveScoreUI;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.ui.gui.util.ToastMessage;

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

        btnResume = new Button("VOLTAR");
        btnSave = new Button("GUARDAR");
        btnExit = new Button("SAIR");
        this.getStylesheets().add("pt/isec/pa/tinypac/ui/gui/resources/styles.css");

        VBox vBox = new VBox(btnResume,btnSave,btnExit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);

        this.setCenter(vBox);

    }

    private void registerHandlers() {

        manager.addPropertyChangeListener(evt -> Platform.runLater(this::update));

        btnResume.setOnAction(actionEvent -> {
            manager.resume();
        });

        btnSave.setOnAction(actionEvent -> {
            manager.save();
            ToastMessage.show(getScene().getWindow(),"Game Saved!");
        });

        btnExit.setOnAction( event -> {
            saveScore();
            Stage stage = (Stage) this.getScene().getWindow();
            RootPane root = new RootPane(new PacmanManager());
            Scene scene = new Scene(root,1000,1000);
            stage.setScene(scene);
            stage.getIcons().addAll(ImageManager.getImage("pacman_right.png"));
            stage.setTitle("Pacman");
            stage.setMinWidth(700);
            stage.setMinHeight(400);
            stage.show();
        });

    }

    private void saveScore() {

        Stage popUp = new Stage();
        Scene scene = new Scene(new SaveScoreUI(manager),400,100);

        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setTitle("SAVE");
        popUp.setScene(scene);
        popUp.setMinWidth(300);
        popUp.setMinHeight(200);
        popUp.getIcons().addAll(ImageManager.getImage("pacman_right.png"));
        popUp.showAndWait();
    }

    private void update() {
        if (manager.getState() != PacmanState.PAUSE) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);


    }
}
