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
    Button btnStart, btnExit, btnYes,btnNo,btnTop5;
    Label lblGameLoad;
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
       //btnConfig(btnStart);
       btnTop5 = new Button("TOP 5");
       //btnConfig(btnTop5);
       btnExit = new Button("SAIR");
       //btnConfig(btnExit);

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
               newOrLoad(file);
           }
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
        lblGameLoad = new Label("Queres mesmo sair?");
        btnYes = new Button("SIM");
        btnNo = new Button("NÃO");

        HBox hBox = new HBox(btnYes, btnNo);
        VBox vBox = new VBox(lblGameLoad,hBox);
        Stage popUp = new Stage();
        Scene scene = new Scene(vBox,100,100);

        vBox.setSpacing(20);
        hBox.setSpacing(40);
        hBox.setPadding(new Insets(10));
        btnYes.setPadding(new Insets(5));
        btnNo.setPadding(new Insets(5));
        btnYes.setMinWidth(100);
        btnNo.setMinWidth(100);

        vBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);

        btnNo.setOnAction(actionEvent -> {
            popUp.close();

        });
        btnYes.setOnAction(actionEvent -> {
            Platform.exit();
        });


        //popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setTitle("Load");
        popUp.setScene(scene);
        popUp.setMinWidth(300);
        popUp.setMinHeight(200);
        popUp.getIcons().addAll(ImageManager.getImage("pacman_right.png"));
        popUp.show();
    }


    private void newOrLoad(File file) {

        lblGameLoad = new Label("Queres continuar o jogo anterior?");
        btnYes = new Button("SIM");
        btnNo = new Button("NÃO");

        HBox hBox = new HBox(btnYes, btnNo);
        VBox vBox = new VBox(lblGameLoad,hBox);
        Stage popUp = new Stage();
        Scene scene = new Scene(vBox,100,100);

        vBox.setSpacing(20);
        hBox.setSpacing(40);
        hBox.setPadding(new Insets(10));
        btnYes.setPadding(new Insets(5));
        btnNo.setPadding(new Insets(5));
        btnYes.setMinWidth(100);
        btnNo.setMinWidth(100);

        vBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);

        btnNo.setOnAction(actionEvent -> {
            popUp.close();
            this.setCenter(new GameUI(manager));
        });
        btnYes.setOnAction(actionEvent -> {
            load(file);
            popUp.close();
        });


        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setTitle("Load");
        popUp.setScene(scene);
        popUp.setMinWidth(300);
        popUp.setMinHeight(200);
        popUp.getIcons().addAll(ImageManager.getImage("pacman_right.png"));
        popUp.show();
    }

    public void load(File file){
        try(FileInputStream fs = new FileInputStream(file);
            ObjectInputStream ds = new ObjectInputStream(fs);){

            Environment environment = (Environment) ds.readObject();
            this.setCenter(new GameUI(new PacmanManager(environment)));

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("erro a loadar"
                    + "A criar um jogo novo");
            this.setCenter(new GameUI(manager));
        } finally {
            file.delete();
        }
    }
}
