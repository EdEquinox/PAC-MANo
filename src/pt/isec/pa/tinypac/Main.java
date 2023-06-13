package pt.isec.pa.tinypac;

import javafx.application.Application;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.ui.gui.MainJFX;

import java.io.IOException;

public class Main {

    public static PacmanManager manager;

    static {
        manager = new PacmanManager();
    }
    public static void main(String[] args) throws IOException {
        Application.launch(MainJFX.class,args);
    }
}