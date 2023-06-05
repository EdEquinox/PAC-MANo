package pt.isec.pa.tinypac.model.fsm;

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

public class PacmanContext implements IGameEngineEvolve {
    private IPacmanState state;
    private Environment data;
    public String FILE = "files/Level01.txt";
    private int h = 0,w = 0;

    public PacmanContext() {
        data = readFile(FILE);
        state = PacmanState.createState(PacmanState.INIT_LEVEL,this,data);
    }

    void changeState(IPacmanState newState) {
        state = newState;
    }
    public boolean changeDirection(){
        return state.changeDirection();
    }
    public boolean evolve(){
        return state.evolve();
    }
    public boolean pause(PacmanState currentState) {
        return state.pause(currentState);
    }
    public boolean resume(){
        return state.resume();
    }
    public boolean saveGame(){
        return state.saveGame();
    }
    public boolean leaveGame(){
        return state.leaveGame();
    }

    //getters

    public PacmanState getState() {
        return state.getState();
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        if (data == null) return;
        data.evolve();
        evolve();
    }
    public void changeDirection(MazeElement.Directions directions){
        changeDirection();
        data.getPacman().setCurrentDirection(directions);
    }
    private Environment readFile(String filePath) {
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

    public char[][] getMaze() {
        return data.getMaze();
    }

    public Pacman.Directions getDirection(){
        return data.getPacman().getCurrentDirection();
    }

    public int getScore() {
        return data.getScore();
    }

    public void superChange() {
        data.superChange();
        data.getPacman().superChange();
    }

    public boolean getSuper() {
        return data.getSuper();
    }

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
        FILE = data.nextLevel();
        return true;
    }
    public boolean bigLose() {
        return data.bigLose();
    }
    public <T extends IMazeElement> ArrayList<IMazeElement> getListElement(Class<T> type){
        return data.getListElement(type);
    }
    public int getNLives(){
        return data.getnLives();
    }
}
