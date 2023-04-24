package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.MazeElement;

import java.util.List;

public class PacmanData extends MazeElement {

    public static final char SYMBOL = 'M';
    public enum Directions{UP,DOWN,RIGHT,LEFT}
    private int nLives;
    private Directions currentDirection;

    public PacmanData(Environment environment) {
        super(environment);
        this.nLives = 3;
        this.currentDirection = Directions.RIGHT;
    }

    @Override
    public void evolve() {
        Environment.Position myPos = environment.getPositionOf(this);
        if (myPos==null) return;
        List<Environment.Position> lst = environment.getElementNeighbors(myPos.y(), myPos.x(), IMazeElement.class);
        if (lst.isEmpty()) return;
        switch (currentDirection){
            case RIGHT -> {}
            case LEFT -> {}
            case DOWN -> {}
            case UP -> {}
        }
    }

    public void perdeVida() {
        this.nLives = nLives-1;
    }

    public void setCurrentDirection(Directions currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Directions getCurrentDirection() {
        return currentDirection;
    }

    public int getnLives() {
        return nLives;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
