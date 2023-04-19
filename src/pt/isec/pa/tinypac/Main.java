package pt.isec.pa.tinypac;

import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.ui.text.PacManTUI;

public class Main {
    public static void main(String[] args) {
        PacmanContext fsm = new PacmanContext();
        PacManTUI pacManTUI = new PacManTUI(fsm);
        pacManTUI.start();
    }
}