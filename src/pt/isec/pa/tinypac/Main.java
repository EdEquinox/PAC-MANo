package pt.isec.pa.tinypac;

import javafx.application.Application;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.ui.gui.MainJFX;

import java.io.IOException;

public class Main {

    public static PacmanManager manager;
    public static void main(String[] args) throws IOException {
        manager = new PacmanManager();
        Application.launch(MainJFX.class,args);
    }
}