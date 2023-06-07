package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.ui.gui.uistates.*;

public class RootPane extends BorderPane {

    PacmanManager manager;
    boolean start;
    Button btnStart, btnExit, btnYes,btnNo,btnTop5;

    Label lblSure;
   public RootPane(PacmanManager manager) {
       this.manager = manager;
       start = false;
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

       btnStart = new Button("START NEW GAME");
       btnConfig(btnStart);
       btnTop5 = new Button("TOP 5");
       btnConfig(btnTop5);
       btnExit = new Button("EXIT");
       btnConfig(btnExit);

       VBox vBox = new VBox(btnStart,btnTop5,btnExit);
       vBox.setAlignment(Pos.CENTER);
       vBox.setSpacing(15);

       this.setCenter(vBox);

   }

   private void registerHandlers() {

       manager.addPropertyChangeListener(evt -> Platform.runLater(this::update));

       btnStart.setOnAction(actionEvent -> {
           this.setCenter(new GameUI(manager));
       });

       btnTop5.setOnAction(actionEvent -> {
           this.setCenter(new Top5UI(manager));
       });

       btnExit.setOnAction( event -> {
           sair();
       });

   }

    private void update() {

    }

    private void sair() {
       Platform.exit();
    }

    private void btnConfig(Button btn){
        btn.setMinWidth(200);
        btn.setMinHeight(50);
        btn.setStyle("-fx-background-color: #b38b0f; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-pref-width: 120px; -fx-pref-height: 40px; -fx-background-radius: 20px; -fx-font-family: 'Comic Sans MS'; -fx-font-weight: bold");
    }

}
