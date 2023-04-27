package pt.isec.pa.tinypac.ui.text;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import pt.isec.pa.tinypac.gameengine.GameEngineState;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import com.googlecode.lanterna.screen.Screen;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.data.elements.Cave;
import pt.isec.pa.tinypac.model.data.elements.Coin;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.elements.ghosts.*;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.utils.PAInput;

import java.io.IOException;

public class PacManLanternaUI implements IGameEngineEvolve {
    Screen screen;
    PacmanContext fsm;
    EnvironmentManager environmentManager;

    public PacManLanternaUI(PacmanContext fsm,EnvironmentManager environmentManager) throws IOException {
        this.fsm = fsm;
        this.environmentManager = environmentManager;
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(
                new TerminalSize(60,40)).createTerminal();
        this.screen = new TerminalScreen(terminal);
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
                    case Pacman.SYMBOL -> TextColor.ANSI.BLACK;
                    case Portal.SYMBOL -> TextColor.ANSI.BLACK;
                    case Wall.SYMBOL -> TextColor.ANSI.BLACK;
                    case Warp.SYMBOL -> TextColor.ANSI.BLACK;
                    case Blinky.SYMBOL -> TextColor.ANSI.BLACK;
                    case Clyde.SYMBOL -> TextColor.ANSI.BLACK;
                    case Inky.SYMBOL -> TextColor.ANSI.BLACK;
                    case Pinky.SYMBOL -> TextColor.ANSI.BLACK;
                    default -> TextColor.ANSI.BLACK;
                };
                TextColor bc = switch(env[y][x]) {
                    case Cave.SYMBOL -> TextColor.ANSI.BLUE;
                    case Coin.SYMBOL -> TextColor.ANSI.YELLOW;
                    case Fruit.SYMBOL -> TextColor.ANSI.RED;
                    case Pacman.SYMBOL -> TextColor.ANSI.YELLOW_BRIGHT;
                    case Portal.SYMBOL -> TextColor.ANSI.BLUE_BRIGHT;
                    case Wall.SYMBOL -> TextColor.ANSI.BLACK;
                    case Warp.SYMBOL -> TextColor.ANSI.BLACK_BRIGHT;
                    case Blinky.SYMBOL -> TextColor.ANSI.WHITE;
                    case Clyde.SYMBOL -> TextColor.ANSI.CYAN;
                    case Inky.SYMBOL -> TextColor.ANSI.MAGENTA_BRIGHT;
                    case Pinky.SYMBOL -> TextColor.ANSI.MAGENTA;
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
            if (key != null && (key.getKeyType() == KeyType.ArrowUp)){
                environmentManager.changeDirection(Pacman.Directions.UP);
            }
            if (key != null && (key.getKeyType() == KeyType.ArrowDown)){
                environmentManager.changeDirection(Pacman.Directions.DOWN);
            }
            if (key != null && (key.getKeyType() == KeyType.ArrowLeft)){
                environmentManager.changeDirection(Pacman.Directions.LEFT);
            }
            if (key != null && (key.getKeyType() == KeyType.ArrowRight)){
                environmentManager.changeDirection(Pacman.Directions.RIGHT);
            }
            if (key != null && ((key.getKeyType() == KeyType.Character && key.getCharacter().equals('p')))){
                gameEngine.pause();
                switch (PAInput.chooseOption(" AÇÕES ",
                        "VOLTAR", "GUARDAR SCORE", "ABANDONAR JOGO")){
                    case 1 -> {
                        fsm.resume();
                        gameEngine.resume();
                    }
                    case 2-> fsm.saveGame();
                    case 3-> fsm.leaveGame();
                }
            }
        } catch (IOException e) {

        }

    }


}
