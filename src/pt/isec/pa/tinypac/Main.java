package pt.isec.pa.tinypac;

import javafx.application.Application;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.ui.gui.MainJFX;
import pt.isec.pa.tinypac.ui.text.PacManTUI;

import java.io.IOException;

public class Main {

    public static PacmanManager manager;
    static PacmanContext fsm;

    static {
        manager = new PacmanManager();
    }
    public static void main(String[] args) throws IOException {
//        fsm = new PacmanContext();
//        PacManTUI pacManTUI = new PacManTUI(fsm);
//        pacManTUI.start();

        Application.launch(MainJFX.class,args);

    }
}