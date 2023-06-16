package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.top5.Top5;

import java.util.*;

public class Top5UI extends BorderPane {

    Top5 top5;
    TilePane tilePane;
    Button btnBack;
    VBox vTop;

    public Top5UI() {
        this.top5 = new Top5();

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

        btnBack  = new Button("VOLTAR");
        btnBack.setMinWidth(100);
        btnBack.setAlignment(Pos.CENTER);
        vTop = new VBox();

        tilePane = getLine(
                450,
                "Pontos",
                "Nome"
        );
        tilePane.setStyle("""
                -fx-text-alignment: center; -fx-font-size: 30px;-fx-padding: 20px 20px;
                    -fx-spacing: 1.5px;
                    -fx-font-weight: bold;""");
        tilePane.getChildren().get(0).setStyle("-fx-text-fill: FFFFFF");
        tilePane.getChildren().get(1).setStyle("-fx-text-fill: FFFFFF");

        ScrollPane scrollPane = new ScrollPane(vTop);
        scrollPane.setStyle("-fx-background-color: #212121");
        scrollPane.setPrefHeight(200);
        scrollPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY.darker().darker().darker(),CornerRadii.EMPTY, Insets.EMPTY)));

        VBox listWithTitle = new VBox(tilePane,scrollPane);
        listWithTitle.setBorder(new Border(new BorderStroke(Color.DARKGRAY,BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,new BorderWidths(0))));

        VBox all = new VBox(listWithTitle,btnBack);
        all.setFillWidth(false);
        all.setSpacing(10);
        all.setAlignment(Pos.CENTER);
        setAlignment(all,Pos.CENTER);
        this.setCenter(all);
    }

    private void registerHandlers() {

        btnBack.setOnAction(actionEvent -> Platform.runLater(()->{
            try {
                Class<? extends Application> appClass = MainJFX.class;
                Application application = appClass.getDeclaredConstructor().newInstance();
                application.init();
                application.start((Stage) this.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }

    private void update() {
        List<String> scores = top5.scores();
        for (int i = 0; i<5;i++){
            List<String> score = Arrays.asList(scores.get(i).split(","));
            TilePane newTilePane = getLine(450,score.get(0),score.get(1));
            vTop.getChildren().add(newTilePane);
        }
    }

    //preenche o TilePane
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
