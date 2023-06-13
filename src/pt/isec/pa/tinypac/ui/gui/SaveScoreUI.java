package pt.isec.pa.tinypac.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.ui.gui.util.ToastMessage;

public class SaveScoreUI extends BorderPane {

    Button btnOk;
    TextField textField;
    Label label;
    PacmanManager manager;

    public SaveScoreUI(PacmanManager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        label = new Label("Qual é o teu nome?");
        btnOk = new Button("OK");
        textField = new TextField();

        HBox hBox = new HBox(label, textField);
        VBox vBox = new VBox(hBox,btnOk);


        vBox.setSpacing(20);
        hBox.setSpacing(40);
        hBox.setPadding(new Insets(10));
        btnOk.setPadding(new Insets(5));
        btnOk.setMinWidth(100);

        vBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);

        this.setCenter(vBox);

    }

    private void registerHandlers() {

        btnOk.setOnAction(actionEvent -> {
            if (textField.getText().isEmpty()){
                ToastMessage.show(getScene().getWindow(),"Nome inválido!");
            } else {
                manager.saveScore(textField.getText());
                Stage a = (Stage) this.getScene().getWindow();
                a.close();
            }
        });
    }

    private void update() {
    }

}
