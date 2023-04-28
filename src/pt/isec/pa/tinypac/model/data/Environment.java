package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.elements.*;

import java.util.ArrayList;
import java.util.List;

public class Environment {

    public void die() {
        nLives--;
    }

    public record Position(int y, int x){}

    int timeSuper =0;
    int timeGhost=0;
    private static final int TIME_UP = 20;
    private static final int TIME_UP_GHOST=5;
    private int nLives=3;
    int height, width;
    Maze maze;
    int level=0;
    int nCoins;
    public static final String FILE = "files/Level01.txt";
    private int numGhosts = 0;
    private int score;
    private boolean isSuper = false;

    public Environment(int height, int width) {
        this.height = height;
        this.width = width;
        this.maze = new Maze(height,width);
        this.nLives = 3;
    }

    public void addElement(IMazeElement element, int y, int x) {
        maze.set(y,x,element);
    }

    public MazeElement getElement(int y,int x) {
        IMazeElement element = maze.get(y,x);
        if (element instanceof MazeElement mazeElement)
            return mazeElement;
        return null;
    }
    public Pacman getPacman() {
        for(int y = 0; y < height;y++)
            for(int x = 0;x < width; x++)
                if (maze.get(y,x) instanceof Pacman pacman)
                    return pacman;
        return null;
    }
    public <T extends IMazeElement> ArrayList<IMazeElement> getListElement(Class<T> type){
        ArrayList<IMazeElement> list = new ArrayList<>();
        for(int y = 0; y < height;y++)
            for(int x = 0;x < width; x++)
                if (x != 0 || y != 0) {
                var element = (IMazeElement) maze.get(y,x);
                list.add(element);}
        return list;
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
    public Position getPositionOf(IMazeElement element) {
        for(int y = 0; y < height;y++)
            for(int x = 0;x < width; x++)
                if (maze.get(y,x) == element)
                    return new Position(y,x);
        return null;
    }
    public boolean canMove(int y, int x, MazeElement.Directions directions){
        int newRow = y;
        int newCol = x;

        switch (directions) {
            case UP -> newRow--;
            case DOWN -> newRow++;
            case LEFT -> newCol--;
            case RIGHT -> newCol++;
        }

        if (newRow < 0 || newRow >= maze.getMaze().length || newCol < 0 || newCol >= maze.getMaze()[0].length) {
            return false;
        }
        else if (this.getElement(newRow,newCol) instanceof Wall) {
            return false;
        }
        else if (this.getElement(newRow,newCol) instanceof Portal) {
            return false;
        }else return true;

    }
    public List<Position> getAdjacentEmptyCells(int yo, int xo) {
        List<Position> lst = new ArrayList<>();
        for (int y = Math.max(0,yo-1); y <= Math.min(height-1,yo+1); y++)
            for (int x = Math.max(0,xo-1); x <= Math.min(width-1,xo+1); x++)
                if ((y != yo || x != xo) && maze.get(y, x) == null)
                    lst.add(new Position(y , x));
        return lst;
    }

    public boolean evolve() {
        System.out.println("vidas:"+nLives);
        List<MazeElement> lst = new ArrayList<>();
        for(int y = 0; y < height;y++)
            for(int x = 0;x < width; x++)
                if (maze.get(y,x) instanceof MazeElement element) {
                    lst.add(element);
                }

        for(var organism : lst){
            organism.evolve();
        }
        if (getPacman().getCurrentDirection()!= MazeElement.Directions.NADA){
            timeGhost++;}
        return true;
    }
    public void superChange() {
        isSuper = !isSuper;
    }
    public void addGhost() {
        numGhosts++;
    }
    public void scoreUp(){
        score++;
    }
    public boolean timesUp() {
        timeSuper++;
        System.out.println("tempo super: "+this.timeSuper);
        if (timeSuper > TIME_UP && isSuper){
            superChange();
            timeSuper =0;
            return true;
        }
        return false;
    }
    public boolean timeGhost(){
        if (getPacman().getCurrentDirection()!= MazeElement.Directions.NADA){
            return timeGhost > TIME_UP_GHOST;
        }
        return false;
    }

    public char [][] getMaze() {
        return maze.getMaze();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    public String getFilename() {
        return FILE;
    }

    public int getNumGhosts() {
        return numGhosts;
    }

    public int getScore() {
        return score;
    }

    public boolean getSuper() {
        return isSuper;
    }

    public int getTimeSuper() {
        return timeSuper;
    }

    public boolean ghostsBusted() {
        return numGhosts == 0;
    }

    public boolean win() {
        return nCoins == 0 && level > 20;
    }

    public boolean died() {
        //tratar colisao
        nLives--;
        return true;
    }

    public boolean bigLose() {
        if (nLives==0) return true;
        return false;
    }

//    public boolean nextLevel() {
//        Environment newEnv = this.
//        return true;
//    }
}
