package pt.isec.pa.tinypac;

import javafx.application.Application;
import pt.isec.pa.tinypac.model.PacmanManager;
import pt.isec.pa.tinypac.ui.gui.MainJFX;

import java.io.IOException;
/**
 Main class
 *
 * The first class of this project
 *
 * @author Jose Marques
 * @version 1.0.0
 *
 */
public class Main {


    public static PacmanManager manager;
    public static void main(String[] args) throws IOException {
        manager = new PacmanManager();
        Application.launch(MainJFX.class,args);
    }

    //TODO como é que paro o game engine porque quando mudo de nivel dá raia - mete o pacman no sitio inicial
    //TODO pacman para no lunch - rever isto
    //TODO testes fazem se no context? - sim
    //todo o que por no javadoc - manager, transiçoes
    //todo singleton da mais pontos? - nao mas convem explicar
    //TODO tests
    //TODO doc
    //todo tem ficheiro e delete ficheiro no manager
}