package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Pinky;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

import java.util.ArrayList;

public class LunchTimeUI extends BorderPane {
    PacmanManager manager;
    GridPane grid;
    private ImageView imageView;
    private Image[] images;
    private int index;
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
                manager.changeDirection(MazeElement.Directions.RIGHT);
            }else if (event.getCode() == KeyCode.S) {
                manager.changeDirection(MazeElement.Directions.DOWN);
            }else if (event.getCode() == KeyCode.P) {
                manager.pause();
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
                                cell = pacmanMovement(ImageManager.getImage("pacman_up.png"),ImageManager.getImage("pacman_close.png"));
                            }
                            case DOWN -> {
                                cell = pacmanMovement(ImageManager.getImage("pacman_down.png"),ImageManager.getImage("pacman_close.png"));
                            }
                            case RIGHT, NADA -> {
                                cell = pacmanMovement(ImageManager.getImage("pacman_right.png"),ImageManager.getImage("pacman_close.png"));
                            }
                            case LEFT -> {
                                cell = pacmanMovement(ImageManager.getImage("pacman_left.png"),ImageManager.getImage("pacman_close.png"));
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
                cell.setFitWidth(20);
                cell.setFitHeight(20);
                grid.add(cell,j,i);
                GridPane.setColumnIndex(cell, j);
                GridPane.setRowIndex(cell, i);
            }
        }
    }
    private ImageView pacmanMovement(Image img1, Image img2){

        imageView = new ImageView();;
        images = new Image[]{img1, img2};

        index = 0;
        imageView.setImage(images[index]);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            index = (index + 1) % images.length;
            imageView.setImage(images[index]);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        return imageView;
    }
}
