package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.Main;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

public class MainJFX extends Application {
    PacmanManager manager;
    @Override
    public void init() throws Exception {
        super.init();
        manager = Main.manager;
    }

    @Override
    public void start(Stage stage) throws Exception {
        RootPane root = new RootPane(manager);
        Scene scene = new Scene(root,1000,1000);
        stage.setScene(scene);
        stage.getIcons().addAll(ImageManager.getImage("pacman_open.png"));
        stage.setTitle("Pacman");
        stage.setMinWidth(700);
        stage.setMinHeight(400);
        stage.show();
    }

}