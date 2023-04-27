package pt.isec.pa.tinypac.model.data.elements.ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;

import java.util.Random;

public class Clyde extends Ghost{
    public static final char SYMBOL = 'c';
    private MazeElement.Directions currentDirection;
    public Clyde(Environment environment) {
        super(environment);
        this.environment = environment;
        currentDirection = Directions.RIGHT;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }

    @Override
    public void evolve() {
        Environment.Position myPos = environment.getPositionOf(this);
        if (myPos == null) return;
        int randomNum = new Random().nextInt(2);
        switch (currentDirection){
            case LEFT -> {
                if(environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.LEFT)){
                    moveLeft(myPos);
                    currentDirection = Directions.LEFT;
                } else if(randomNum == 0 && environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.UP)){
                    moveUp(myPos);
                    currentDirection = Directions.UP;
                } else if (randomNum == 1 && environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.DOWN)) {
                    moveDown(myPos);
                    currentDirection = Directions.DOWN;
                } else if (environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.RIGHT)) {
                    moveRight(myPos);
                    currentDirection = Directions.RIGHT;
                }
            }
            case UP -> {
                if(environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.UP)){
                    moveUp(myPos);
                    currentDirection = Directions.UP;
                } else if(randomNum == 0 && environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.RIGHT)){
                    moveRight(myPos);
                    currentDirection = Directions.RIGHT;
                } else if (randomNum == 1 && environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.LEFT)) {
                    moveLeft(myPos);
                    currentDirection = Directions.LEFT;
                } else if (environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.DOWN)) {
                    moveDown(myPos);
                    currentDirection = Directions.DOWN;
                }
            }
            case DOWN -> {
                if(environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.DOWN)){
                    moveDown(myPos);
                    currentDirection = Directions.DOWN;
                } else if(randomNum == 0 && environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.RIGHT)){
                    moveRight(myPos);
                    currentDirection = Directions.RIGHT;
                } else if (randomNum == 1 && environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.LEFT)) {
                    moveLeft(myPos);
                    currentDirection = Directions.LEFT;
                } else if (environment.canMove(myPos.y(), myPos.x(), Directions.UP)) {
                    moveUp(myPos);
                    currentDirection = Directions.UP;
                }
            }
            case RIGHT -> {
                if(environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.RIGHT)){
                    moveRight(myPos);
                    currentDirection = Directions.RIGHT;
                } else if(randomNum == 0 && environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.UP)){
                    moveUp(myPos);
                    currentDirection = Directions.UP;
                } else if (randomNum == 1 && environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.DOWN)) {
                    moveDown(myPos);
                    currentDirection = Directions.DOWN;
                } else if (environment.canMove(myPos.y(), myPos.x(), MazeElement.Directions.LEFT)) {
                    moveLeft(myPos);
                    currentDirection = Directions.LEFT;
                }
            }

        }
    }
}
