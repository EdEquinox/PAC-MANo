package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.ui.text.PacManLanternaUI;
import pt.isec.pa.tinypac.ui.text.PacManTUI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        EnvironmentManager environment = new EnvironmentManager();
        PacmanContext fsm = new PacmanContext();
        PacManTUI pacManTUI = new PacManTUI(fsm,environment);
        pacManTUI.start();

    }
}