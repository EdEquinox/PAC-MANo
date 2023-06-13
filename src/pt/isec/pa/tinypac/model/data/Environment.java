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
    private static final int TIME_UP = 5;
    private static final int TIME_UP_GHOST = 5;
    private int nLives = 1;
    //endregion

    //region maze properties
    private final Maze maze;
    private int height;
    private int width;
    //endregion

    // region flags
    private boolean isDead = false;
    private boolean isEmpty = false;
    private boolean isSuper = false;

    //endregion

    //region counters
    private int timeSuper = 0;
    private int timeGhost = 0;
    private int nCoins = 0;
    private int numGhosts = 0;
    private int score = 0;
    //endregion

    //region run properties
    private String username;
    private String level = String.valueOf(19);
    public final String FILE = "files/Level" + level + ".txt";
    //endregion

    public record Position(int y, int x) implements Serializable{
    }

    //region contructors
    public Environment(int height, int width) {
        this.height = height;
        this.width = width;
        this.maze = new Maze(height, width);
    }
    public Environment() {
        this.height = 0;
        this.width = 0;
        this.maze = new Maze(0, 0);
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
    public String getFilename() {
        return FILE;
    }// get nome do ficheiro
    public int getNumGhosts() {
        return numGhosts;
    }// get numero de fantasmas
    public int getScore() {
        return score;
    } //get score
    public int getTimeSuper() {
        return timeSuper;
    } //get tempo de super
    private String getUsername() {
        return username;
    }//get nome do utilizador
    public int getTimeGhost() {
        return timeGhost;
    }//get tempo de spawn dos fantasmas
    public int getnLives() {
        return nLives;
    }//get vidas
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
        } else if (this.getElement(newRow, newCol) instanceof Portal) {
            return false;
        } else return true;
    }// elemento em x, y pode mover-se na direção direction

    //endregion

    //region setters
    public void addElement(IMazeElement element, int y, int x) {
        maze.set(y, x, element);
    } //adiciona elemento a y,x
    public void resetCoins() {
        isEmpty = false;
    } //dá reset ao contador de moedas
    public void revive() {
        if (isDead) {
            isDead = false;
        }
    } //pacman revive
    public void addGhost() {
        numGhosts++;
    }//adiciona fantasma ao contador
    public void resetTimeGhost() {
        this.timeGhost = 0;
    }//dá reset ao tempo de super
    public void setUsername(String username){
        this.username = username;
    } //nome de utilizador para guardar score
    //endregion

    //region helpers
    public void countCoins(){
        isEmpty = getElement(Coin.class) == null;
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
    public Environment readFile(String filePath) {
        Environment environment = null;
        FileReader fr = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) return null;
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            count(file);
            int x,y=0;
            environment = new Environment(height,width);
            String line;
            while (y < height && (line = br.readLine()) != null) {
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
    }//le o ficheiro

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
        countCoins();
        return true;
    }//evolve
    //moving
    public boolean gameWin() {
        if ((Integer.parseInt(level)==20) && isEmpty){
            return true;
        }
        return false;
        //return nCoins == 0 && Integer.parseInt(level) > 20;
    } //ganhou o jogo depois de nivel 20
    public boolean gameLost() {
        return getnLives() == 0;
    }//perdeu o jogo
    public boolean nextLvl() {
        if (isEmpty) {
            int i = (Integer.parseInt(level));
            System.out.println(i);
            this.level = String.valueOf(i + 1);
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
            resetTimeGhost();
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

    //region NOT NEEDED
    public List<Position> getAdjacentEmptyCells(int yo, int xo) {
        List<Position> lst = new ArrayList<>();
        for (int y = Math.max(0, yo - 1); y <= Math.min(height - 1, yo + 1); y++)
            for (int x = Math.max(0, xo - 1); x <= Math.min(width - 1, xo + 1); x++)
                if ((y != yo || x != xo) && maze.get(y, x) == null)
                    lst.add(new Position(y, x));
        return lst;
    }
    public <T extends IMazeElement> List<Position> getElementNeighbors(int y, int x, Class<T> type) {
        List<Position> lst = new ArrayList<>();
        for (int yd = -1; yd <= 1; yd++) {
            for (int xd = -1; xd <= 1; xd++) {
                if (yd != 0 || xd != 0) {
                    var organism = maze.get(y + yd, x + xd);
                    if (type.isInstance(organism)) {
                        lst.add(new Position(y + yd, x + xd));
                    }
                }
            }
        }
        return lst;
    }
    //endregion

}