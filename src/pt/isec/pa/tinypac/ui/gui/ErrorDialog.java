package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

public class ErrorDialog extends BorderPane {

    Label lblError;
    private Button btnOk;
    HBox hBox;
    VBox vBox;
    Stage popUp;
    Scene scene;

    public ErrorDialog() {
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        lblError = new Label("Ocurreu um erro ao iniciar o jogo!");
        btnOk = new Button("Ok");

        vBox = new VBox(lblError,btnOk);
        popUp = new Stage();
        scene = new Scene(vBox,100,100);

        vBox.setSpacing(20);
        btnOk.setPadding(new Insets(5));
        btnOk.setMinWidth(100);

        vBox.setAlignment(Pos.CENTER);

        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setTitle("FATAL ERROR");
        popUp.setScene(scene);
        popUp.setMinWidth(300);
        popUp.setMinHeight(50);
        popUp.getIcons().addAll(ImageManager.getImage("pacman_right.png"));
        popUp.show();

    }

    private void registerHandlers() {
        btnOk.setOnAction(actionEvent -> {
            Platform.exit();
        });
    }

    private void update() {

    }

}
