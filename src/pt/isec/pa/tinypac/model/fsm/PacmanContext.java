package pt.isec.pa.tinypac.model.fsm;


//so importa data
import pt.isec.pa.tinypac.gameengine.GameEngineState;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Pinky;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PacmanContext {
    private IPacmanState state;
    IGameEngine gameEngine;
    private Environment data;
    public String FILE = "files/Level01.txt";
    private int h = 0,w = 0;

    public PacmanContext() {
        data = readFile(FILE);
        state = PacmanState.createState(PacmanState.INIT_LEVEL,this,data);
    }

    public PacmanContext(Environment environment) {
        data = environment;
        state = PacmanState.createState(PacmanState.MOVING,this,data);
    }

    void changeState(IPacmanState newState) {
        state = newState;
    }

    //region TRANSITIONS
    public void evolve(long currentTime) {
        if (data == null) return;
        data.evolve();
        state.evolve();
    }
    public void changeDirection(MazeElement.Directions directions){
        state.changeDirection();
        data.getPacman().setCurrentDirection(directions);
    }
    public void leaveGame(){
        state.leaveGame();
    }

    public void pause(PacmanState currentState) {
        state.pause(currentState);
        gameEngine.pause();
    }
    public void resume(){
        state.resume();
        gameEngine.resume();
    }
    public void saveGame(){
        state.saveGame();
        data.saveGame();
    }
    public void saveScore(String username){
        data.saveScore(username);
        state.saveScore();
    }

    //endregion
    //region GETTERS
    public PacmanState getState() {
        return state.getState();
    }
    public int getScore() {
        return data.getScore();
    }
    public char[][] getMaze() {
        return data.getMaze();
    }
    public boolean getSuper() {
        return data.getSuper();
    }
    public int getNLives(){
        return data.getnLives();
    }
    public Pacman.Directions getDirection(){
        return data.getPacman().getCurrentDirection();
    }

    //endregion
    //region FUNCTIONS
    private Environment readFile(String filePath) {             //mandar para o data
        Environment environment = null;
        FileReader fr = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) return null;
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            count(file);
            int x,y=0;
            environment = new Environment(h,w);
            String line;
            while (y < h && (line = br.readLine()) != null) {
                char[] chars = line.toCharArray();
                for (x = 0; x < chars.length; x++) {
                    char c = chars[x];
                    IMazeElement element = switch (c) {
                        case Pacman.SYMBOL -> new Pacman(environment);
                        case Wall.SYMBOL -> new Wall(environment);
                        case Coin.SYMBOL -> new Coin(environment);
                        case SuperCoin.SYMBOL -> new SuperCoin(environment);
                        case Warp.SYMBOL -> new Warp(environment);
                        case Cave.SYMBOL -> caverna(environment);
                        case Portal.SYMBOL -> new Portal(environment);
                        case Fruit.SYMBOL -> new Fruit(environment);
                        case EmptyCell.SYMBOL -> new EmptyCell(environment);
                        default -> throw new IllegalStateException("Unexpected value: " + c);
                    };
                    environment.addElement(element, y, x);
                }
                y++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado, a carregar o nivel 1");
            readFile(FILE);

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (fr!=null){
                try {
                    fr.close();
                } catch (IOException exception){
                    exception.printStackTrace();
                }
            }
        }

        return environment;
    }
    public void count(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()){
            String[] columns = scanner.nextLine().split("");
            this.h++;
            this.w = Math.max(this.w, columns.length);
        }
        scanner.close();
    }
    private IMazeElement caverna(Environment environment) {
        if (environment.getNumGhosts()<4) {
            switch (environment.getNumGhosts()){
                case 0 -> {
                    environment.addGhost();
                    return new Blinky(environment);
                }
                case 1 -> {
                    environment.addGhost();
                    return new Clyde(environment);
                }
                case 2 -> {
                    environment.addGhost();
                    return new Inky(environment);
                }
                case 3 -> {
                    environment.addGhost();
                    return new Pinky(environment);
                }
            }
        }
        environment.addGhost();
        return new Cave(environment);
    }

    //endregion
    //region SETTERS
//    public void superChange() {
//        data.superChange();
//        data.getPacman().superChange();
//    }



    public boolean timesIsUp() {
        return data.timesUp();
    }
    public boolean timeGhost(){
        return data.timeGhost();
    }

    public int getTime() {
        return data.getTimeSuper();
    }

    public boolean win() {
        return data.win();
    }

    public boolean newLevel() {
        //gameEngine.stop();
        FILE = "Level20.txt";
        System.out.println("mudou de nome");
        this.data = readFile(FILE);
        System.out.println("leu");
        return true;
    }
    public boolean bigLose() {
        return data.bigLose();
    }
    public <T extends IMazeElement> ArrayList<IMazeElement> getListElement(Class<T> type){
        return data.getListElement(type);
    }


    public void save() {
        try(FileOutputStream fs = new FileOutputStream("files/game.bin");
            ObjectOutputStream os = new ObjectOutputStream(fs)){
            os.writeObject(data);


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("erro a gravar");
        }
    }

    public void died() {
        //gameEngine.stop();
    }

    public void continueGE() {
        if ( !gameEngine.getCurrentState().equals(GameEngineState.RUNNING))
            gameEngine.resume();
    }

    //mudanças de dados só em transiçoes
    //context so chama state.qqcoisa()


}
