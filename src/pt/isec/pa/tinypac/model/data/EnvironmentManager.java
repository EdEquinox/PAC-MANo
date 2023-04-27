package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Pinky;

import java.io.*;
import java.util.*;

public class EnvironmentManager implements IGameEngineEvolve {
    public static final String FILE = "files/Level01.txt";
    private int h = 0,w = 0;
    private int numGhosts = 0;
    Environment environment;

    public EnvironmentManager() {
        this.environment = readFile(FILE);
        if (environment!=null) return;
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
            int x=0,y=0;
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
                        default -> throw new IllegalStateException("Unexpected value: " + c);
                    };
                    environment.addElement(element, y, x);
                }
                y++;
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (fr!=null){
                try {
                    fr.close();
                } catch (IOException ignored){
                    ignored.printStackTrace();
                }
            }
        }
        return environment;
    }

    private IMazeElement caverna(Environment environment) {
        if (numGhosts<4) {
            switch (numGhosts){
                case 0 -> {
                    numGhosts ++;
                    return new Blinky(environment);
                }
                case 1 -> {
                    numGhosts ++;
                    return new Clyde(environment);
                }
                case 2 -> {
                    numGhosts ++;
                    return new Inky(environment);
                }
                case 3 -> {
                    numGhosts ++;
                    return new Pinky(environment);
                }
            }
        }
        numGhosts ++;
        return new Cave(environment);
    }

    public char[][] getEnvironment() {
        return environment.getEnvironment();
    }

    public void changeDirection(MazeElement.Directions directions){
        environment.getPacman().setCurrentDirection(directions);
    }
    public Pacman.Directions getDirection(){
       return environment.getPacman().getCurrentDirection();
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        if (environment == null) return;
        environment.evolve();
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
}
