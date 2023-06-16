package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Pinky;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Environment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    //region game properties
    private static final int TIME_UP = 50;
    private static final int TIME_UP_GHOST = 3;
    private int nLives = 1;
    //endregion

    //region maze properties
    private Maze maze;
    private int height;
    private int width;
    //endregion

    // region flags
    private boolean isDead = false;
    private boolean isSuper = false;

    //endregion

    //region counters
    private int timeSuper = 0;
    private int timeGhost = 0;
    private int nCoins = 0;
    private int numGhosts = 0;
    private int score = 0;
    //endregion

    private int level = 1;
    public String FILE = "files/Level" + level + ".txt";

    //endregion

    public record Position(int y, int x) implements Serializable{
    }

    //region contructors

    public Environment() {
        this.maze = readFileMaze(FILE);
    }

    private Maze readFileMaze(String filepath) {
        Maze nMaze = null;
        FileReader fr = null;
        try {
            File file = new File(filepath);
            //if (!file.exists()) readFileMaze("Level1.txt");
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            count(file);
            int x,y=0;
            nMaze = new Maze(height,width);
            String line;
            while (y < height && (line = br.readLine()) != null) {
                char[] chars = line.toCharArray();
                for (x = 0; x < chars.length; x++) {
                    char c = chars[x];
                    IMazeElement element = switch (c) {
                        case Pacman.SYMBOL -> new Pacman(this);
                        case Wall.SYMBOL -> new Wall(this);
                        case Coin.SYMBOL -> new Coin(this);
                        case SuperCoin.SYMBOL -> new SuperCoin(this);
                        case Warp.SYMBOL -> new Warp(this);
                        case Cave.SYMBOL -> caverna(this);
                        case Portal.SYMBOL -> new Portal(this);
                        case Fruit.SYMBOL -> new Fruit(this);
                        case EmptyCell.SYMBOL -> new EmptyCell(this);
                        default -> throw new IllegalStateException("Unexpected value: " + c);
                    };
                    nMaze.set(y, x,element);
                }
                y++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado, a carregar o nivel 1");
            readFileMaze("files/Level1.txt");

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
        return nMaze;
    }
    //endregion


    //region getters
    public MazeElement getElement(int y, int x) {
        IMazeElement element = maze.get(y, x);
        if (element instanceof MazeElement mazeElement)
            return mazeElement;
        return null;
    } //get by position
    public <T extends IMazeElement> IMazeElement getElement(Class<T> type) {
        IMazeElement element;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x != 0 || y != 0) {
                    element = maze.get(y, x);
                    if (element != null && element.getClass() == type) {
                        return element;
                    }
                }
            }
        }
        return null;
    } //get by type
    public boolean checkEnv() {
        return getListElement(Pacman.class).size() != 1;
    }//get se o env é valido
    public Pacman getPacman() {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (maze.get(y, x) instanceof Pacman pacman)
                    return pacman;
        return null;
    }//get pacman
    public <T extends IMazeElement> ArrayList<IMazeElement> getListElement(Class<T> type) {
        ArrayList<IMazeElement> list = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x != 0 || y != 0) {
                    IMazeElement element = maze.get(y, x);
                    if (element != null && element.getClass() == type) {
                        list.add(element);
                    }
                }
            }
        }
        return list;
    }//get lista de elementos do tipo T
    public Position getPositionOf(IMazeElement element) {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (maze.get(y, x) == element)
                    return new Position(y, x);
        return null;
    }//get posição do elemento pedido
    public char[][] getMaze() {
        return maze.getMaze();
    }// get maze
    public int getHeight() {
        return height;
    }//get altura do maze
    public int getWidth() {
        return width;
    }//get largura do maze
    public int getNumGhosts() {
        return numGhosts;
    }// get numero de fantasmas
    public int getScore() {
        return score;
    } //get score
    public int getTimeSuper() {
        return timeSuper;
    } //get tempo de super
    public int getTimeGhost() {
        return timeGhost;
    }//get tempo de spawn dos fantasmas
    public int getnLives() {
        return nLives;
    }//get vidas
    public int getLevel() {
        return level;
    } //retorna o nivel
    public String getCoins() {
        return String.valueOf(nCoins);
    }
    //endregion

    //region flags
    public boolean canMove(int y, int x, MazeElement.Directions direction) {
        int newRow = y;
        int newCol = x;

        switch (direction) {
            case UP -> newRow--;
            case DOWN -> newRow++;
            case LEFT -> newCol--;
            case RIGHT -> newCol++;
        }

        if (newRow < 0 || newRow >= maze.getMaze().length || newCol < 0 || newCol >= maze.getMaze()[0].length) {
            return false;
        } else if (this.getElement(newRow, newCol) instanceof Wall) {
            return false;
        } else return !(this.getElement(newRow, newCol) instanceof Portal);
    }// elemento em x, y pode mover-se na direção direction

    //endregion

    //region setters
    public void addElement(IMazeElement element, int y, int x) {
        maze.set(y, x, element);
    } //adiciona elemento a y,x
    public void resetCoins() {
    } //dá reset ao contador de moedas

    public void resetTimeGhosts() {
        timeGhost = 0;
    }

    public void setLives(int i) {
        nLives = i;
    }

    public void revive() {
        if (isDead) {
            isDead = false;
        }
        //getPacman().resetPosition();
    } //pacman revive
    public void addGhost() {
        numGhosts++;
    }//adiciona fantasma ao contador
    public void resetTime() {
        this.timeSuper = 0;
    }//dá reset ao tempo de super
    //endregion

    //region helpers
    public boolean countCoins(){
        ArrayList <IMazeElement> list = getListElement(Coin.class);
        nCoins = list.size();
        return nCoins != 0;
    }//conta as moedas
    public boolean timeGhost() {
        if (getPacman().getCurrentDirection() != MazeElement.Directions.NADA) {
            return timeGhost > TIME_UP_GHOST;
        }
        return false;
    } //contagem para os fantasmas sairem da toca
    public void count(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()){
            String[] columns = scanner.nextLine().split("");
            this.height++;
            this.width = Math.max(this.width, columns.length);
        }
        scanner.close();
    }//conta altura e largura
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
    }//popula a caverna
    //endregion

    //region pacman helpers
    public void loseLife() {
        nLives--;
    }//pacman perde uma vida
    public void die(){
        isDead = true;
    } //pacman morre
    public void scoreUp() {
        score++;
    }//pacman ganha +1 de score
    public void superChange() {
        isSuper = !isSuper;
    } //pacman muda para super
    public void hpUP() {
        nLives++;
    }// pacman ganha uma vida
    //endregion

    //region transition helpers
    //initleven
    public void changeDirection(MazeElement.Directions direction){
        getPacman().setCurrentDirection(direction);
    }// muda direção
    public boolean evolve() {
        List<MazeElement> lst = new ArrayList<>();
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (maze.get(y, x) instanceof MazeElement element) {
                    lst.add(element);
                }

        for (var organism : lst) {
            organism.evolve();
        }
        if (getPacman().getCurrentDirection() != MazeElement.Directions.NADA) {
            timeGhost++;
        }
        return true;
    }//evolve
    //moving
    public boolean gameWin() {
        return level > 20;
    } //ganhou o jogo depois de nivel 20
    public boolean gameLost() {
        return getnLives() == 0;
    }//perdeu o jogo
    public boolean nextLvl() {
        if (!countCoins()) {
            int i = level;
            this.level = (i + 1);
            FILE = "files/Level" + level + ".txt";
            this.timeGhost = 0;
            if (level<21)
                this.maze = readFileMaze(FILE);
            return true;
        }
        return false;
    } //acabaram as moedas
    public boolean isSuper() {
        return isSuper;
    }//pacman está super?
    public boolean isDead() {
        return isDead;
    }//pacman está morto?
    //lunch_time
    public boolean ghostsBusted() {
        return false;
    }//os fantasmas foram todos apanhados?
    public boolean timesUp() {
        timeSuper++;
        if (timeSuper > TIME_UP && isSuper) {
            resetTime();
            superChange();
            return true;
        }
        return false;
    } //acabou o tempo de super
    //endgame
    public void saveScore(String username) {
        String score = getScore()+","+username;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("files/scores.txt",true))){
            writer.write(score);
            writer.newLine();
        } catch (Exception e){
            e.printStackTrace();
        }
    }//guarda score para top5
    //endregion

}