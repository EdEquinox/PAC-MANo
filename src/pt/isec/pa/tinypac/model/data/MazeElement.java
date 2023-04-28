package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.elements.EmptyCell;

public abstract class MazeElement implements IMazeElement{
    protected Environment environment;

    public enum Directions{UP,DOWN,RIGHT,LEFT,NADA}
    public MazeElement(Environment environment) {
        this.environment = environment;
    }
    abstract public void evolve();

    protected void moveUp(Environment.Position myPos){
        MazeElement mazeElement = environment.getElement(myPos.y() - 1, myPos.x());
        environment.addElement(null, myPos.y(), myPos.x());
        environment.addElement(this, myPos.y() - 1, myPos.x());
        environment.addElement(mazeElement, myPos.y(), myPos.x());
    }
    protected void moveDown(Environment.Position myPos){
        MazeElement mazeElement = environment.getElement(myPos.y() + 1, myPos.x());
        environment.addElement(null, myPos.y(), myPos.x());
        environment.addElement(this, myPos.y() + 1, myPos.x());
        environment.addElement(mazeElement, myPos.y(), myPos.x());
    }
    protected void moveLeft(Environment.Position myPos){
        MazeElement mazeElement = environment.getElement(myPos.y(), myPos.x() - 1);
        environment.addElement(null, myPos.y(), myPos.x());
        environment.addElement(this, myPos.y(), myPos.x() - 1);
        environment.addElement(mazeElement, myPos.y(), myPos.x());
    }
    protected void moveRight(Environment.Position myPos){
        MazeElement mazeElement = environment.getElement(myPos.y(), myPos.x() + 1);
        environment.addElement(null, myPos.y(), myPos.x());
        environment.addElement(this, myPos.y(), myPos.x() + 1);
        environment.addElement(mazeElement, myPos.y(), myPos.x());
    }

    protected void moveTo(int y, int x, Environment.Position myPos){
        MazeElement element = environment.getElement(myPos.y(), myPos.x());
        environment.addElement(new EmptyCell(environment), myPos.y(), myPos.x());
        environment.addElement(element,y,x);
    }
}
