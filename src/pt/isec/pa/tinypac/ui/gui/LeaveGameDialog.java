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

public class LeaveGameDialog extends BorderPane {


    Label lblGameLoad;
    Button btnYes,btnNo;
    HBox hBox;
    VBox vBox;
    Stage popUp;
    Scene scene;

    public LeaveGameDialog() {
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        lblGameLoad = new Label("Queres mesmo sair?");
        btnYes = new Button("SIM");
        btnNo = new Button("NÃO");

        hBox = new HBox(btnYes, btnNo);
        vBox = new VBox(lblGameLoad,hBox);
        popUp = new Stage();
        scene = new Scene(vBox,100,100);

        vBox.setSpacing(20);
        hBox.setSpacing(40);
        hBox.setPadding(new Insets(10));
        btnYes.setPadding(new Insets(5));
        btnNo.setPadding(new Insets(5));
        btnYes.setMinWidth(100);
        btnNo.setMinWidth(100);

        vBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);

        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setTitle("Load");
        popUp.setScene(scene);
        popUp.setMinWidth(300);
        popUp.setMinHeight(200);
        popUp.getIcons().addAll(ImageManager.getImage("pacman_right.png"));
        popUp.show();
    }

    private void registerHandlers() {

        btnNo.setOnAction(actionEvent -> popUp.close());
        btnYes.setOnAction(actionEvent -> Platform.exit());

    }

    private void update() {}


}
