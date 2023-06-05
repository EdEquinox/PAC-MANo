package pt.isec.pa.tinypac.ui.gui;

import javafx.scene.layout.VBox;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;

public class Top5UI extends VBox {

    PacmanManager manager;

    public Top5UI(PacmanManager manager) {
        this.manager = manager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {

    }

    private void registerHandlers() {

    }

    private void update() {
        this.setVisible(false);
    }

}
