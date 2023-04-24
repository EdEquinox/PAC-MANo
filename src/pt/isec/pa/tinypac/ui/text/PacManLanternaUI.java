package pt.isec.pa.tinypac.ui.text;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import com.googlecode.lanterna.screen.Screen;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.data.elements.Cave;
import pt.isec.pa.tinypac.model.data.elements.Coin;
import pt.isec.pa.tinypac.model.data.elements.*;

import javax.swing.*;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;

public class PacManLanternaUI implements IGameEngineEvolve {
    Screen screen;
    EnvironmentManager environmentManager;

    public PacManLanternaUI(EnvironmentManager environmentManager) throws IOException {
        this.environmentManager = environmentManager;
        this.screen = new DefaultTerminalFactory().createScreen();
        screen.setCursorPosition(null);
        show();
    }

    private void show() throws IOException {
        char[][] env = environmentManager.getEnvironment();
        screen.startScreen();
        for (int y = 0; y < env.length; y++) {
            for (int x = 0; x < env[0].length; x++) {
                TextColor tc = switch(env[y][x]) {
                    case Cave.SYMBOL -> TextColor.ANSI.BLACK;
                    case Coin.SYMBOL -> TextColor.ANSI.BLACK;
                    case Fruit.SYMBOL -> TextColor.ANSI.BLACK;
                    case PacmanData.SYMBOL -> TextColor.ANSI.BLACK;
                    case Portal.SYMBOL -> TextColor.ANSI.BLACK;
                    case Wall.SYMBOL -> TextColor.ANSI.BLACK;
                    case Warp.SYMBOL -> TextColor.ANSI.BLACK;
                    default -> TextColor.ANSI.BLACK;
                };
                TextColor bc = switch(env[y][x]) {
                    case Cave.SYMBOL -> TextColor.ANSI.BLUE;
                    case Coin.SYMBOL -> TextColor.ANSI.YELLOW;
                    case Fruit.SYMBOL -> TextColor.ANSI.RED;
                    case PacmanData.SYMBOL -> TextColor.ANSI.YELLOW_BRIGHT;
                    case Portal.SYMBOL -> TextColor.ANSI.BLUE_BRIGHT;
                    case Wall.SYMBOL -> TextColor.ANSI.BLACK;
                    case Warp.SYMBOL -> TextColor.ANSI.BLACK_BRIGHT;
                    default -> TextColor.ANSI.WHITE;
                };
                screen.setCharacter(x,y, TextCharacter.fromCharacter(env[y][x],tc,bc)[0]);
            }
        }
        screen.refresh();
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        try {
            show();
            KeyStroke key = screen.pollInput();
            if (key != null && (key.getKeyType() == KeyType.Escape ||
                    (key.getKeyType() == KeyType.Character && key.getCharacter().equals('q')))){
                gameEngine.stop();
                screen.close();
            }
        } catch (IOException e) {

        }
    }


}
