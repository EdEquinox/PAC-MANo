package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Pinky;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

import java.io.File;
import java.util.ArrayList;

public class LunchTimeUI extends BorderPane {
    PacmanManager manager;
    GridPane grid;
    Media media;
    MediaPlayer mediaPlayer;
    public LunchTimeUI(PacmanManager manager) {
        this.manager = manager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
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

        File audioFile = new File("src/pt/isec/pa/tinypac/ui/gui/resources/sounds/pacman.mp3");
        media = new Media(audioFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.02);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.W){
                manager.changeDirection(MazeElement.Directions.UP);
            } else if (event.getCode() == KeyCode.A) {
                manager.changeDirection(MazeElement.Directions.LEFT);
            }else if (event.getCode() == KeyCode.D) {
                manager.changeDirection(MazeElement.Directions.RIGHT);
            }else if (event.getCode() == KeyCode.S) {
                manager.changeDirection(MazeElement.Directions.DOWN);
            }else if (event.getCode() == KeyCode.P) {
                manager.pause();
                mediaPlayer.stop();
            }
        });
    }

    private void update() {
        if (manager.getState() != PacmanState.LUNCH_TIME) {
            this.setVisible(false);
            mediaPlayer.stop();
            return;
        }
        maze();
        mediaPlayer.play();
        this.requestFocus();
        this.setVisible(true);
    }

    private void maze(){
        grid.getChildren().clear();
        for(int i=0; i<manager.getMaze().length;i++){
            for(int j=0; j<manager.getMaze()[i].length;j++){
                char a = manager.getMaze()[i][j];
                ImageView cell=null;
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
                        ArrayList<IMazeElement> element = manager.getFsm().getListElement(Pacman.class);
                        Pacman pacman = (Pacman) element.get(0);
                        switch (pacman.getCurrentDirection()){
                            case UP -> {
                                cell = new ImageView(ImageManager.getImage("pacman_up.gif"));
                            }
                            case DOWN -> {
                                cell = new ImageView(ImageManager.getImage("pacman_down.gif"));
                            }
                            case RIGHT, NADA -> {
                                cell = new ImageView(ImageManager.getImage("pacman_right.gif"));
                            }
                            case LEFT -> {
                                cell = new ImageView(ImageManager.getImage("pacman_left.gif"));
                            }
                        }
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
                        cell = new ImageView(ImageManager.getImage("ghost.png"));
                    }
                    case EmptyCell.SYMBOL -> {
                        cell = new ImageView(ImageManager.getImage("empty.png"));
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
