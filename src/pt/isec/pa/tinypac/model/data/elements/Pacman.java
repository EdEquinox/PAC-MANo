package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.MazeElement;

import java.util.List;

public class Pacman extends MazeElement {

public static final char SYMBOL = 'M';
    private int nLives;
    private MazeElement.Directions currentDirection;

    public Pacman(Environment environment) {
        super(environment);
        this.nLives = 3;
        this.currentDirection = Directions.RIGHT;
    }

    @Override
    public void evolve() {
        Environment.Position myPos = environment.getPositionOf(this);
        if (myPos==null) return;
        switch (currentDirection){
            case RIGHT -> {
                if (environment.canMove(myPos.y(), myPos.x(), Directions.RIGHT)){
                    moveRight(myPos);
                    currentDirection = Directions.RIGHT;
                }
            }
            case LEFT -> {
                if (environment.canMove(myPos.y(), myPos.x(), Directions.LEFT)){
                    moveLeft(myPos);
                    currentDirection = Directions.LEFT;
                }
            }
            case UP -> {
                if (environment.canMove(myPos.y(), myPos.x(), Directions.UP)){
                    moveUp(myPos);
                    currentDirection = Directions.UP;
                }
            }
            case DOWN -> {
                if (environment.canMove(myPos.y(), myPos.x(), Directions.DOWN)){
                    moveDown(myPos);
                    currentDirection = Directions.DOWN;
                }
            }
        }
    }

    public void perdeVida() {
        this.nLives = nLives-1;
    }

    public void setCurrentDirection(MazeElement.Directions currentDirection) {
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
