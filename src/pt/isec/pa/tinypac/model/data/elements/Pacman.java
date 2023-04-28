package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.MazeElement;

import java.util.List;

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

    protected void warp(Environment.Position myPos){
//        if (environment.getElement(myPos.y(), myPos.x()) instanceof Warp){
//            Warp warp = environment.getWarp().get(0);
//            if (environment.getPositionOf(warp).equals(myPos)){
//                Warp newWarp = environment.getWarp().get(1);
//                myPos = environment.getPositionOf(newWarp);
//                environment.addElement(warp, myPos.y(), myPos.x());
//                environment.addElement(this, myPos.y() + 1, myPos.x());
//            }
//        }
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
    @Override
    protected void moveDown(Environment.Position myPos) {
        warp(myPos);
        super.moveDown(myPos);
        superChange(myPos);
        scoreUp(myPos);
        environment.addElement(new EmptyCell(environment), myPos.y(), myPos.x());
    }

    @Override
    protected void moveLeft(Environment.Position myPos) {
        super.moveLeft(myPos);
        superChange(myPos);
        scoreUp(myPos);
        environment.addElement(new EmptyCell(environment), myPos.y(), myPos.x());
    }

    @Override
    protected void moveRight(Environment.Position myPos) {
        super.moveRight(myPos);
        superChange(myPos);
        scoreUp(myPos);
        environment.addElement(new EmptyCell(environment), myPos.y(), myPos.x());
    }

    @Override
    protected void moveUp(Environment.Position myPos) {
        super.moveUp(myPos);
        superChange(myPos);
        scoreUp(myPos);
        environment.addElement(new EmptyCell(environment), myPos.y(), myPos.x());
    }
}
