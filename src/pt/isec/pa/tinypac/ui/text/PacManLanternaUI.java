package pt.isec.pa.tinypac.ui.text;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Pinky;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.ui.text.utils.PAInput;

import java.io.IOException;

public class PacManLanternaUI implements IGameEngineEvolve {
    Screen screen;
    PacmanContext fsm;

    public PacManLanternaUI(PacmanContext fsm) throws IOException {
        this.fsm = fsm;
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(
                new TerminalSize(60,40)).createTerminal();
        this.screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        show(0);
    }

    private void show(int i) throws IOException {
        char[][] env = fsm.getMaze();
        screen.startScreen();
        if (i == 0){
            normalMaze(env);
        }
        else superMaze(env);
        screen.refresh();
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        try {
            System.out.println("estado: "+fsm.getState().toString());
            KeyStroke key = screen.pollInput();
            switch (fsm.getState()){
                case INIT_LEVEL -> initGameState(key);
                case PAUSE -> pauseState(gameEngine);
                case MOVING -> {
                    movingState(key,gameEngine);
                    show(0);
                }
                case LUNCH_TIME -> {
                    lunchTimeState(key, gameEngine);
                    show(1);
                }
                case ENDGAME -> endgameState(gameEngine);
            }
            if (key != null && (key.getKeyType() == KeyType.Escape || (key.getKeyType() == KeyType.Character && key.getCharacter().equals('q')))){
                gameEngine.stop();
                screen.close();
            }

            System.out.println("score: "+fsm.getScore());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro a criar o maze");
        }
    }
    //MAZES
    private void superMaze(char[][] env){
        for (int y = 0; y < env.length; y++) {
            for (int x = 0; x < env[0].length; x++) {
                TextColor tc = TextColor.ANSI.BLACK;
                TextColor bc = switch(env[y][x]) {
                    case Cave.SYMBOL -> TextColor.ANSI.BLUE;
                    case Coin.SYMBOL -> TextColor.ANSI.YELLOW;
                    case Fruit.SYMBOL -> TextColor.ANSI.RED;
                    case Pacman.SYMBOL -> TextColor.ANSI.YELLOW_BRIGHT;
                    case Portal.SYMBOL -> TextColor.ANSI.BLUE_BRIGHT;
                    case Wall.SYMBOL -> TextColor.ANSI.BLACK;
                    case Warp.SYMBOL -> TextColor.ANSI.BLACK_BRIGHT;
                    case Blinky.SYMBOL -> TextColor.ANSI.GREEN;
                    case Clyde.SYMBOL -> TextColor.ANSI.GREEN;
                    case Inky.SYMBOL -> TextColor.ANSI.GREEN;
                    case Pinky.SYMBOL -> TextColor.ANSI.GREEN;
                    case EmptyCell.SYMBOL -> TextColor.ANSI.YELLOW;
                    default -> TextColor.ANSI.WHITE;
                };
                screen.setCharacter(x,y, TextCharacter.fromCharacter(env[y][x],tc,bc)[0]);
            }
        }
    }
    private void normalMaze(char[][] env){
        for (int y = 0; y < env.length; y++) {
            for (int x = 0; x < env[0].length; x++) {
                TextColor tc = TextColor.ANSI.BLACK;
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
                    case EmptyCell.SYMBOL -> TextColor.ANSI.YELLOW;
                    default -> TextColor.ANSI.WHITE;
                };
                screen.setCharacter(x,y, TextCharacter.fromCharacter(env[y][x],tc,bc)[0]);
            }
        }
    }
    //_______________________
    //STATES
    private void initGameState(KeyStroke key){
        if (!(fsm.getDirection() == MazeElement.Directions.NADA)){
            fsm.changeDirection();
        }
        move(key);
    }
    private void endgameState(IGameEngine gameEngine) throws IOException {
        System.out.println("O teu score foi " + fsm.getScore());
        gameEngine.stop();
        screen.close();
    }
    private void lunchTimeState(KeyStroke key, IGameEngine gameEngine) throws IOException {
        move(key);
        pause(key,gameEngine);
    }
    private void movingState(KeyStroke key, IGameEngine gameEngine) throws IOException {
        move(key);
        pause(key,gameEngine);
        //win(gameEngine);
        //nextLevel(gameEngine);
    }
    private void pauseState(IGameEngine gameEngine) throws IOException {
        gameEngine.pause();
        switch (PAInput.chooseOption(" AÇÕES ",
                "VOLTAR", "GUARDAR SCORE", "ABANDONAR JOGO")){
            case 1 -> {
                fsm.resume();
                gameEngine.resume();
            }
            case 2-> fsm.saveGame();
            case 3-> {
                fsm.leaveGame();
                gameEngine.stop();
                screen.close();
            }
        }
    }
    //________________________
    //TRANSITIONS

    private void pause(KeyStroke key, IGameEngine gameEngine) throws IOException {
        if (key != null && ((key.getKeyType() == KeyType.Character && key.getCharacter().equals('p')))){
            fsm.pause(fsm.getState());
            pauseState(gameEngine);
        }
    }
    private void move(KeyStroke key) {
        if (key != null && (key.getKeyType() == KeyType.ArrowUp)){
            fsm.changeDirection(Pacman.Directions.UP);
        }
        if (key != null && (key.getKeyType() == KeyType.ArrowDown)){
            fsm.changeDirection(Pacman.Directions.DOWN);
        }
        if (key != null && (key.getKeyType() == KeyType.ArrowLeft)){
            fsm.changeDirection(Pacman.Directions.LEFT);
        }
        if (key != null && (key.getKeyType() == KeyType.ArrowRight)){
            fsm.changeDirection(Pacman.Directions.RIGHT);
        }
    }

    //___________________________
}
