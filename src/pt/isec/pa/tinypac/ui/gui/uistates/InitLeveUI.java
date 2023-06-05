package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Pinky;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

public class InitLeveUI extends BorderPane{
    PacmanManager manager;
    GridPane grid;
    public InitLeveUI(PacmanManager manager) {
        this.manager = manager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        grid = new GridPane();

        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.setBackground(
                new Background(
                        new BackgroundFill(Paint.valueOf("#000000"), CornerRadii.EMPTY,Insets.EMPTY)
                )
        );
        grid.alignmentProperty().set(Pos.CENTER);
        maze();
        this.setCenter(grid);


    }

    private void registerHandlers() {

        manager.addPropertyChangeListener(evt -> Platform.runLater(this::update));

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.W){
                manager.changeDirection(MazeElement.Directions.UP);
            } else if (event.getCode() == KeyCode.A) {
                manager.changeDirection(MazeElement.Directions.LEFT);
            }else if (event.getCode() == KeyCode.D) {
                manager.changeDirection(MazeElement.Directions.RIGHT);
            }else if (event.getCode() == KeyCode.S) {
                manager.changeDirection(MazeElement.Directions.DOWN);
            }
        });

    }

    private void update() {
        if (manager.getState() != PacmanState.INIT_LEVEL) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
        this.requestFocus();
    }


    private void maze(){
        for(int i=0; i<manager.getMaze().length;i++){
            for(int j=0; j<manager.getMaze()[i].length;j++){
                char a = manager.getMaze()[i][j];
                ImageView cell;
                switch (a){
                    case Cave.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage(""));
                    }
                    case Coin.SYMBOL -> {
                        Image image = ImageManager.getImage("coin.png");
                        cell = new ImageView(image);
                    }
                    case Fruit.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("fruit.png"));
                    }
                    case Pacman.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("pacman_open.png"));
                    }
                    case SuperCoin.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("super-coin.png"));
                    }
                    case Portal.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("portal.png"));
                    }
                    case Wall.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("wall.png"));
                    }
                    case Warp.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("warp.png"));
                    }
                    case Blinky.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("red.png"));
                    }
                    case Clyde.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("blue.png"));
                    }
                    case Inky.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("yellow.png"));
                    }
                    case Pinky.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("pink.png"));
                    }
                    case EmptyCell.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("empty.png"));
                    }
                    default -> cell = new ImageView(ImageManager.getImage("empty.png"));
                }
                cell.setFitWidth(20);
                cell.setFitHeight(20);
                grid.add(cell,j,i);
                GridPane.setColumnIndex(cell, j);
                GridPane.setRowIndex(cell, i);
            }
        }
    }
}
