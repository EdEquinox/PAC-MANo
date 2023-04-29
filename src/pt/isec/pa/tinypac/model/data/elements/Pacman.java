package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Ghost;

import java.util.ArrayList;

public class Pacman extends MazeElement {

public static final char SYMBOL = 'M';

    private MazeElement.Directions currentDirection;

    public Pacman(Environment environment) {
        super(environment);
        this.currentDirection = Directions.NADA;
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

    public void setCurrentDirection(MazeElement.Directions currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Directions getCurrentDirection() {
        return currentDirection;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }

    protected void superChange(Environment.Position myPos){
        if (environment.getElement(myPos.y(), myPos.x()) instanceof SuperCoin){
            environment.superChange();
        }
    }
    protected void scoreUp(Environment.Position myPos){
        if (environment.getElement(myPos.y(), myPos.x()) instanceof Coin){
            environment.scoreUp();
        }
    }
    protected void hpUp(Environment.Position myPos){
        if (environment.getElement(myPos.y(), myPos.x()) instanceof Fruit){
            environment.hpUP();
        }
    }

    protected void die(Environment.Position myPos){
        if (environment.getElement(myPos.y(), myPos.x()) instanceof Ghost){
            environment.die();
        }
    }
    @Override
    protected void moveDown(Environment.Position myPos) {
        super.moveDown(myPos);
        superChange(myPos);
        scoreUp(myPos);
        hpUp(myPos);
        die(myPos);
        if (environment.getElement(myPos.y(), myPos.x()) instanceof Coin || environment.getElement(myPos.y(), myPos.x()) instanceof SuperCoin || environment.getElement(myPos.y(), myPos.x()) instanceof Fruit){
            environment.addElement(new EmptyCell(environment), myPos.y(), myPos.x());
        }
    }

    @Override
    protected void moveLeft(Environment.Position myPos) {
        super.moveLeft(myPos);
        superChange(myPos);
        scoreUp(myPos);
        hpUp(myPos);
        die(myPos);
        if (environment.getElement(myPos.y(), myPos.x()) instanceof Coin || environment.getElement(myPos.y(), myPos.x()) instanceof SuperCoin || environment.getElement(myPos.y(), myPos.x()) instanceof Fruit){
            environment.addElement(new EmptyCell(environment), myPos.y(), myPos.x());
        }
    }

    @Override
    protected void moveRight(Environment.Position myPos) {
        super.moveRight(myPos);
        superChange(myPos);
        scoreUp(myPos);
        hpUp(myPos);
        die(myPos);
        if (environment.getElement(myPos.y(), myPos.x()) instanceof Coin || environment.getElement(myPos.y(), myPos.x()) instanceof SuperCoin || environment.getElement(myPos.y(), myPos.x()) instanceof Fruit){
            environment.addElement(new EmptyCell(environment), myPos.y(), myPos.x());
        }
    }

    @Override
    protected void moveUp(Environment.Position myPos) {
        super.moveUp(myPos);
        superChange(myPos);
        scoreUp(myPos);
        hpUp(myPos);
        die(myPos);
        if (environment.getElement(myPos.y(), myPos.x()) instanceof Coin || environment.getElement(myPos.y(), myPos.x()) instanceof SuperCoin || environment.getElement(myPos.y(), myPos.x()) instanceof Fruit){
            environment.addElement(new EmptyCell(environment), myPos.y(), myPos.x());
        }
    }
}
