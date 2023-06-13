package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class RootPane extends BorderPane {

    PacmanManager manager;
    boolean start;
    Button btnStart, btnExit,btnTop5;
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

       btnStart = new Button("COMEÇAR JOGO");
       btnTop5 = new Button("TOP 5");
       btnExit = new Button("SAIR");

       VBox vBox = new VBox(btnStart,btnTop5,btnExit);
       vBox.setAlignment(Pos.CENTER);
       vBox.setSpacing(15);

       this.getStylesheets().add("pt/isec/pa/tinypac/ui/gui/resources/styles.css");
       this.setCenter(vBox);

   }

   private void registerHandlers() {

       manager.addPropertyChangeListener(evt -> Platform.runLater(this::update));

       btnStart.setOnAction(actionEvent -> {
           File file = new File("files/game.bin");
           if (!file.exists()){
               this.setCenter(new GameUI(manager));
           }
           else {
               ContinueGameDialog continueGameDialog = new ContinueGameDialog(manager,file,this::continueGame);
               this.setCenter(continueGameDialog);
           }
       });

       btnTop5.setOnAction(actionEvent -> {
           this.setCenter(new Top5UI());
       });

       btnExit.setOnAction( event -> {
           new LeaveGameDialog();
       });

   }

    private void update() {

    }

    public void continueGame(boolean continueGame) {
        File file = new File("files/game.bin");
        if (continueGame){
            this.setCenter(new GameUI(manager));
        }else {
            this.setCenter(new GameUI(new PacmanManager(manager.load(file))));
        }
    }
}
