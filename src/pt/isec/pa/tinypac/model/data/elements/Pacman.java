package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.data.elements.ghosts.*;

import java.util.ArrayList;

public class Pacman extends MazeElement {

public static final char SYMBOL = 'M';
    private Environment.Position initialPosition;

    private MazeElement.Directions currentDirection;

    public Pacman(Environment environment) {
        super(environment);
        this.currentDirection = Directions.NADA;
    }

    @Override
    public void evolve() {
        if (environment.getTimeGhost()==0){
            this.initialPosition = environment.getPositionOf(this);
        }
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

    public boolean superChange(Environment.Position myPos){
        //Environment.Position myPos = environment.getPositionOf(this);
        System.out.println("!vwe");
        if (environment.getElement(myPos.y(), myPos.x()) instanceof SuperCoin){
            environment.superChange();
            System.out.println("ver");
            return true;
        }
        return false;
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

    public boolean die(Environment.Position myPos){
        //Environment.Position myPos = environment.getPositionOf(this);
        if (environment.getElement(myPos.y(), myPos.x()) instanceof Ghost){
            if (environment.getSuper()){
                environment.scoreUp();
                ArrayList<IMazeElement> blinky = environment.getListElement(Blinky.class);
                ArrayList<IMazeElement> clyde = environment.getListElement(Clyde.class);
                ArrayList<IMazeElement> inky = environment.getListElement(Inky.class);
                ArrayList<IMazeElement> pinky = environment.getListElement(Pinky.class);
                ArrayList<IMazeElement> listCave = environment.getListElement(Cave.class);
                if (environment.getPositionOf(blinky.get(0)) == myPos){
                    Environment.Position cavePos = environment.getPositionOf(listCave.get(1));
                    environment.addElement(null, myPos.y(), myPos.x());
                    environment.addElement(blinky.get(0), cavePos.y(), cavePos.x());
                } else if(environment.getPositionOf(clyde.get(0)) == myPos){
                    Environment.Position cavePos = environment.getPositionOf(listCave.get(1));
                    environment.addElement(null, myPos.y(), myPos.x());
                    environment.addElement(clyde.get(0), cavePos.y(), cavePos.x());
                } else if (environment.getPositionOf(inky.get(0)) == myPos) {
                    Environment.Position cavePos = environment.getPositionOf(listCave.get(1));
                    environment.addElement(null, myPos.y(), myPos.x());
                    environment.addElement(inky.get(0), cavePos.y(), cavePos.x());
                } else if (environment.getPositionOf(pinky.get(0)) == myPos) {
                    Environment.Position cavePos = environment.getPositionOf(listCave.get(1));
                    environment.addElement(null, myPos.y(), myPos.x());
                    environment.addElement(pinky.get(0), cavePos.y(), cavePos.x());
                }
            } else {
                System.out.println(myPos);
                environment.loseLife();
                environment.die();
                ArrayList<IMazeElement> blinky = environment.getListElement(Blinky.class);
                ArrayList<IMazeElement> clyde = environment.getListElement(Clyde.class);
                ArrayList<IMazeElement> inky = environment.getListElement(Inky.class);
                ArrayList<IMazeElement> pinky = environment.getListElement(Pinky.class);
                ArrayList<IMazeElement> list = new ArrayList<>();
                list.add(blinky.get(0));
                list.add(clyde.get(0));
                list.add(inky.get(0));
                list.add(pinky.get(0));
                ArrayList<IMazeElement> listCave = environment.getListElement(Cave.class);
                for (int i=0; i<list.size();i++){
                    Environment.Position cavePos = environment.getPositionOf(listCave.get(i));
                    Environment.Position pacPos = environment.getPositionOf(list.get(i));
                    environment.addElement(null, pacPos.y(), pacPos.x());
                    environment.addElement(list.get(i), cavePos.y(), cavePos.x());
                    environment.addElement(null, pacPos.y(), pacPos.x());
                }
                Environment.Position pac = environment.getPositionOf(this);
                environment.addElement(this, this.getInitialPosition().y(), getInitialPosition().x());
                environment.addElement(null, pac.y(), pac.x());
                environment.resetTimeGhost();
                return true;
            }
        }
        return false;
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

    public Environment.Position getInitialPosition() {
        return initialPosition;
    }
}
