package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Pinky;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

public class LunchTimeUI extends BorderPane {
    PacmanManager manager;
    GridPane grid;
    public LunchTimeUI(PacmanManager manager) {
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

        grid.alignmentProperty().set(Pos.CENTER);

        grid.setBackground(
                new Background(
                        new BackgroundFill(Paint.valueOf("#000000"), CornerRadii.EMPTY,Insets.EMPTY)
                )
        );

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
                manager.changeDirection(MazeElement.Directions.LEFT);
            }else if (event.getCode() == KeyCode.S) {
                manager.changeDirection(MazeElement.Directions.DOWN);
            }
        });
    }

    private void update() {
        if (manager.getState() != PacmanState.LUNCH_TIME) {
            this.setVisible(false);
            return;
        }
        maze();
        this.requestFocus();
        this.setVisible(true);
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
                    case Portal.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("portal.png"));
                    }
                    case Wall.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("wall.png"));
                    }
                    case Warp.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("warp.png"));
                    }
                    case SuperCoin.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("super-coin.png"));
                    }
                    case Blinky.SYMBOL, Pinky.SYMBOL, Inky.SYMBOL, Clyde.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("ghost_chasing.png"));
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