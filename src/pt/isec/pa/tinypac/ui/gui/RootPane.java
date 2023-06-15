package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.ui.gui.resources.CSSManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class RootPane extends BorderPane {

    PacmanManager manager;
    Button btnStart, btnExit,btnTop5;
    Media media;
    MediaPlayer mediaPlayer;

   public RootPane(PacmanManager manager) {
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

       btnStart = new Button("COMEÇAR JOGO");
       btnTop5 = new Button("TOP 5");
       btnExit = new Button("SAIR");

       VBox vBox = new VBox(btnStart,btnTop5,btnExit);
       vBox.setAlignment(Pos.CENTER);
       vBox.setSpacing(15);

       CSSManager.applyCSS(this,"styles.css");
       this.setCenter(vBox);

   }

    private void registerHandlers() {
        manager.addPropertyChangeListener(evt -> Platform.runLater(this::update));

        File audioFile = new File("src/pt/isec/pa/tinypac/ui/gui/resources/sounds/intro.mp3");
        media = new Media(audioFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.04);
        mediaPlayer.play();

       btnStart.setOnAction(actionEvent -> {
           mediaPlayer.stop();
           File file = new File("files/game.bin");
           if (!file.exists()){
               if (manager.checkEnv()){
                   new ErrorDialog();
                   this.getScene().getWindow().hide();
               }
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
