package pt.isec.pa.tinypac.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.ui.gui.resources.ImageManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Top5UI extends BorderPane {

    PacmanManager manager;
    TilePane tilePane;
    Button btnSee, btnBack;
    VBox vTop;

    public Top5UI(PacmanManager manager) {
        this.manager = manager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        btnSee = new Button("VER");
        btnSee.setMinWidth(100);
        btnBack  = new Button("VOLTAR");
        btnBack.setMinWidth(100);
        HBox hBox = new HBox(btnSee,btnBack);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        tilePane = getLine(
                450,
                "Pontos",
                "Nome"
        );
        tilePane.setBackground(new Background(new BackgroundImage(
                        ImageManager.getImage("background.png"),
                        BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(1,1,true,true,false,false)
                )
        ));
        vTop = new VBox();
        ScrollPane scrollPane = new ScrollPane(vTop);
        scrollPane.setStyle("-fx-background-color: #212121");
        scrollPane.setPrefHeight(200);

        VBox listWithTitle = new VBox(tilePane,scrollPane);
        listWithTitle.setBorder(new Border(new BorderStroke(Color.DARKGRAY,BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,new BorderWidths(2))));
        VBox all = new VBox(hBox,listWithTitle);
        all.setFillWidth(false);
        all.setSpacing(10);
        all.setAlignment(Pos.CENTER);
        setAlignment(all,Pos.CENTER);
        this.setCenter(all);
    }

    private void registerHandlers() {

        btnSee.setOnAction(actionEvent -> {

        });

        btnBack.setOnAction(actionEvent -> {
            this.setVisible(false);
            Stage stage = (Stage) this.getScene().getWindow();
            RootPane root = new RootPane(new PacmanManager());
            Scene scene = new Scene(root,1000,1000);
            stage.setScene(scene);
            stage.show();
        });
    }

    private void update() {

        List<String> scores = new ArrayList<>();
        try {
            scores = Files.readAllLines(Path.of("files/scores.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scores.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Character.compare(o2.charAt(0), o1.charAt(0));
            }
        });
        for (int i = 0; i<5;i++){
            List<String> score = Arrays.asList(scores.get(i).split(","));
            TilePane newTilePane = getLine(450,score.get(0),score.get(1));
            vTop.getChildren().add(newTilePane);
        }
    }


    private TilePane getLine(double width, String... strings) {
        TilePane tilePane = new TilePane();
        for (int i = 0; i <strings.length; i++) {
            Label label = new Label(strings[i]);
            tilePane.getChildren().add(label);
        }
        tilePane.setPrefWidth(width);
        tilePane.setPrefTileWidth(width/strings.length);
        return tilePane;
    }
}
