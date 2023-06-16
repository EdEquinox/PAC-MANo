package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.ui.gui.resources.CSSManager;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.ui.gui.resources.SoundManager;

import java.util.Objects;

public class RootPane extends BorderPane {

    PacmanManager manager;
    ImageView imageView;
    Button btnStart, btnExit,btnTop5;
    MediaPlayer mp;


   public RootPane(PacmanManager manager) {
       this.manager = manager;
       createViews();
       registerHandlers();
       update();
   }

   private void createViews() {

       mp = SoundManager.play("start.mp3");
       this.setBackground(
               new Background(
                       new BackgroundImage(
                               Objects.requireNonNull(ImageManager.getImage("background.png")),
                               BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,
                               BackgroundPosition.CENTER,
                               new BackgroundSize(1,1,true,true,false,false)
                       )
               )
       );

       btnStart = new Button("COMEÇAR JOGO");
       btnTop5 = new Button("TOP 5");
       btnExit = new Button("SAIR");
       imageView = new ImageView(ImageManager.getImage("logo.png"));

       VBox vBox = new VBox(btnStart,btnTop5,btnExit);
       vBox.setAlignment(Pos.CENTER);
       vBox.setSpacing(15);

       VBox vBoxGeral = new VBox(imageView,vBox);
       vBoxGeral.setAlignment(Pos.CENTER);
       vBoxGeral.setSpacing(15);

       CSSManager.applyCSS(this,"styles.css");
       this.setCenter(vBoxGeral);

   }

    private void registerHandlers() {
        manager.addPropertyChangeListener(evt -> Platform.runLater(this::update));



       btnStart.setOnAction(actionEvent -> {
           mp.stop();
           if (!manager.checkFile("files/game.bin")){
               if (manager.checkEnv()){
                   new ErrorDialog();
                   this.getScene().getWindow().hide();
               }
               this.setCenter(new GameUI(new PacmanManager()));
           }
           else {
               ContinueGameDialog continueGameDialog = new ContinueGameDialog(manager,"files/game.bin",this::continueGame);
               this.setCenter(continueGameDialog);
           }
       });

       btnTop5.setOnAction(actionEvent -> this.setCenter(new Top5UI()));

       btnExit.setOnAction( event -> new LeaveGameDialog());

   }

    private void update() {}

    //função de callback
    public void continueGame(boolean continueGame) {
        if (continueGame){
            this.setCenter(new GameUI(manager));
        }else {
            this.setCenter(new GameUI(new PacmanManager(manager.load("files/game.bin"))));
        }
    }
}
