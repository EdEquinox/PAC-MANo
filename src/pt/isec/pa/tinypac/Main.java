package pt.isec.pa.tinypac;

import javafx.application.Application;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.ui.gui.MainJFX;

import java.io.IOException;
/**
 Main class
 * <p>
 * The first class of this project
 *
 * @author Jose Marques
 * @version 1.0.0
 *
 */
public class Main {


    public static PacmanManager manager = null;
    public static void main(String[] args) throws IOException {
        manager = new PacmanManager();
        Application.launch(MainJFX.class,args);
    }
}