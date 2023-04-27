package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.elements.Pacman;
import pt.isec.pa.tinypac.model.data.elements.Portal;
import pt.isec.pa.tinypac.model.data.elements.Wall;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Ghost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Environment {


    public record Position(int y, int x){}

    int time=0;
    int height, width;
    Maze maze;

    public Environment(int height, int width) {
        this.height = height;
        this.width = width;
        this.maze = new Maze(height,width);
    }

    public void addElement(IMazeElement ghost, int y, int x) {
        maze.set(y,x,ghost);
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

    public Position getPositionOf(IMazeElement element) {
        for(int y = 0; y < height;y++)
            for(int x = 0;x < width; x++)
                if (maze.get(y,x) == element)
                    return new Position(y,x);
        return null;
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
        time++;
        List<MazeElement> lst = new ArrayList<>();
        for(int y = 0; y < height;y++)
            for(int x = 0;x < width; x++)
                if (maze.get(y,x) instanceof MazeElement element) {
                    lst.add(element);
                }

        for(var organism : lst){
            organism.evolve();
        }
        return true;
    }

    public char [][] getEnvironment() {
        return maze.getMaze();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Maze getMaze() {
        return maze;
    }
}
