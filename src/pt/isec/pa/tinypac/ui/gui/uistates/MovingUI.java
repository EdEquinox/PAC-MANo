package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.data.MazeElement.Directions;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Pinky;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;
import pt.isec.pa.tinypac.ui.gui.resources.SoundManager;

public class MovingUI extends BorderPane {
    PacmanManager manager;
    GridPane grid;
    MediaPlayer mp;

    public MovingUI(PacmanManager manager) {
        this.manager = manager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        mp = SoundManager.play("waka.mp3");
        grid = new GridPane();

        grid.setPadding(new Insets(2));
        grid.setHgap(2);
        grid.setVgap(2);

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
                manager.changeDirection(Directions.UP);
            } else if (event.getCode() == KeyCode.A) {
                manager.changeDirection(Directions.LEFT);
            }else if (event.getCode() == KeyCode.D) {
                manager.changeDirection(Directions.RIGHT);
            }else if (event.getCode() == KeyCode.S) {
                manager.changeDirection(Directions.DOWN);
            } else if (event.getCode() == KeyCode.P) {
                manager.pause();
            }
        });
    }

    private void update() {
        if (manager.getState() != PacmanState.MOVING) {
            mp.stop();
            this.setVisible(false);
            return;
        }
        mp.play();
        this.setVisible(true);
        this.requestFocus();
        evolve();
    }

    private void evolve() {
        maze();
    }

    private void maze(){
        grid.getChildren().clear();
        for(int i=0; i<manager.getMaze().length;i++){
            for(int j=0; j<manager.getMaze()[i].length;j++){
                char a = manager.getMaze()[i][j];
                ImageView cell;
                switch (a){
                    case 'y' -> cell = new ImageView(ImageManager.getImage(""));
                    case 'o' -> cell = new ImageView(ImageManager.getImage("coin.png"));
                    case 'F' -> cell = new ImageView(ImageManager.getImage("fruit.png"));
                    case 'M' -> {
                        cell = new ImageView(ImageManager.getImage("empty.png"));
                        switch (manager.getCurrentDirection()){
                            case UP -> cell = new ImageView(ImageManager.getImage("pacman_up.gif"));
                            case DOWN -> cell = new ImageView(ImageManager.getImage("pacman_down.gif"));
                            case RIGHT, NADA -> cell = new ImageView(ImageManager.getImage("pacman_right.gif"));
                            case LEFT -> cell = new ImageView(ImageManager.getImage("pacman_left.gif"));
                        }
                    }
                    case 'Y' -> cell = new ImageView(ImageManager.getImage("portal.png"));
                    case Wall.SYMBOL -> cell = new ImageView(ImageManager.getImage("wall.png"));
                    case Warp.SYMBOL -> cell = new ImageView(ImageManager.getImage("warp.png"));
                    case SuperCoin.SYMBOL -> cell = new ImageView(ImageManager.getImage("super-coin.png"));
                    case Blinky.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("empty.png"));
                        switch (manager.getCurrentDirectionBlinky()){

                            case UP, NADA, DOWN, RIGHT -> cell = new ImageView(ImageManager.getImage("red_right.png"));
                            case LEFT -> cell = new ImageView(ImageManager.getImage("red_left.png"));
                        }

                    }
                    case Clyde.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("empty.png"));
                        switch (manager.getCurrentDirectionClyde()){
                            case UP, NADA, DOWN, RIGHT -> cell = new ImageView(ImageManager.getImage("blue_right.png"));
                            case LEFT -> cell = new ImageView(ImageManager.getImage("blue_left.png"));
                        }
                    }
                    case Inky.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("empty.png"));
                        switch (manager.getCurrentDirectionInky()){
                            case UP, NADA, DOWN, RIGHT -> cell = new ImageView(ImageManager.getImage("yellow_right.png"));
                            case LEFT -> cell = new ImageView(ImageManager.getImage("yellow_left.png"));
                        }
                    }
                    case Pinky.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("empty.png"));
                        switch (manager.getCurrentDirectionPinky()){
                            case UP, NADA, DOWN, RIGHT -> cell = new ImageView(ImageManager.getImage("pink_right.png"));
                            case LEFT -> cell = new ImageView(ImageManager.getImage("pink_left.png"));
                        }
                    }
                    default -> cell = new ImageView(ImageManager.getImage("empty.png"));
                }
                cell.setFitWidth(15);
                cell.setFitHeight(15);
                grid.add(cell,j,i);
                GridPane.setColumnIndex(cell, j);
                GridPane.setRowIndex(cell, i);
            }
        }
    }

}
