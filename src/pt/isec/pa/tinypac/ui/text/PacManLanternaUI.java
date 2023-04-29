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
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.elements.ghosts.*;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.utils.PAInput;

import java.io.IOException;
import java.util.ArrayList;

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
        show(0);
    }

    private void show(int i) throws IOException {
        char[][] env = environmentManager.getMaze();
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
            if ((key != null &&key.getKeyType() == KeyType.Character && key.getCharacter().equals('/'))){
                nextLevel(gameEngine);
            }
            System.out.println("score: "+environmentManager.getScore());
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
        if (!(environmentManager.getDirection() == MazeElement.Directions.NADA)){
            fsm.changeDirection();
        }
        move(key);
    }
    private void endgameState(IGameEngine gameEngine) throws IOException {
        System.out.println("O teu score foi " + environmentManager.getScore());
        fsm.saveScore();
        gameEngine.stop();
        screen.close();
    }
    private void lunchTimeState(KeyStroke key, IGameEngine gameEngine) throws IOException {
        move(key);
        pause(key,gameEngine);
        timesUp();
        ghostsBusted();
    }
    private void movingState(KeyStroke key, IGameEngine gameEngine) throws IOException {
        move(key);
        pause(key,gameEngine);
        eatBigBall();

//        win(gameEngine);
//        nextLevel(gameEngine);
//        bigLose();
//        died();
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
    private void died() {
        if (environmentManager.died()){
            fsm.died();
        }
    }
    private void bigLose() {
        if (environmentManager.bigLose()){
            fsm.gg();
        }
    }
    private void nextLevel(IGameEngine gameEngine) throws IOException {
        if (environmentManager.nextLevel()){
                fsm.nextLevel();
        }
    }
    private void win(IGameEngine gameEngine) throws IOException {
        if (environmentManager.win()){
            fsm.ggwp();
            gameEngine.stop();
            screen.close();
        }
    }
    private void eatBigBall()  {
        if (environmentManager.getSuper()){
            fsm.eatBigBall();
        }
    }
    private void pause(KeyStroke key, IGameEngine gameEngine) throws IOException {
        if (key != null && ((key.getKeyType() == KeyType.Character && key.getCharacter().equals('p')))){
            fsm.pause(fsm.getState());
            pauseState(gameEngine);
        }
    }
    private void move(KeyStroke key) {
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
    }
    private void ghostsBusted() throws IOException {
        if (environmentManager.ghostsBusted() ){
            fsm.ghostsBusted();
            show(0);
        }
    }
    private void timesUp() throws IOException {
        if (environmentManager.timesUp()){
            fsm.timesUp();
            show(0);
        }
    }
    private void timeGhost(){
        if (environmentManager.timeGhost()){
        }
    }
    //___________________________
}
