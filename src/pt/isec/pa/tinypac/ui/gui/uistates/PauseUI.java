package pt.isec.pa.tinypac.ui.gui.uistates;

import javafx.scene.layout.BorderPane;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;

public class PauseUI extends BorderPane {
    PacmanManager manager;
    public PauseUI(PacmanManager manager) {
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
        if (manager.getState() != PacmanState.PAUSE) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
        System.out.println("ola");
    }
}
