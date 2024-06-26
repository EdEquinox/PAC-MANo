package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.data.elements.Warp;
import java.util.ArrayList;

public abstract class MazeElement implements IMazeElement{
    protected Environment environment;
    public enum Directions {UP,DOWN,RIGHT,LEFT,NADA}
    public MazeElement(Environment environment) {
        this.environment = environment;
    }
    abstract public void evolve();

    protected void moveUp(Environment.Position myPos){
        MazeElement mazeElement = environment.getElement(myPos.y() - 1, myPos.x());
        ArrayList<IMazeElement> listA = environment.getListElement(Warp.class);
        if (mazeElement instanceof Warp) {
            if (listA.size() == 2) {
                environment.addElement(null, myPos.y(), myPos.x());
                Warp thisWarp = (Warp) environment.getElement(myPos.y() -1, myPos.x());
                ArrayList<IMazeElement> list = environment.getListElement(Warp.class);
                if (list.size() == 2 && list.get(0) == thisWarp) {
                    Warp thatWarp = (Warp) list.get(1);
                    Environment.Position position = environment.getPositionOf(thatWarp);
                    environment.addElement(this, position.y()-1, position.x());
                } else if (list.size() == 2 && list.get(1) == thisWarp) {
                    Warp thatWarp = (Warp) list.get(0);
                    Environment.Position position = environment.getPositionOf(thatWarp);
                    environment.addElement(this, position.y()-1, position.x());
                }
            }
        } else {
            environment.addElement(this, myPos.y() - 1, myPos.x());
            environment.addElement(mazeElement, myPos.y(), myPos.x());
        }
    }
    protected void moveDown(Environment.Position myPos){
        MazeElement mazeElement = environment.getElement(myPos.y() + 1, myPos.x());
        ArrayList<IMazeElement> listA = environment.getListElement(Warp.class);
        if (mazeElement instanceof Warp) {
            if (listA.size() == 2) {
                environment.addElement(null, myPos.y(), myPos.x());
                Warp thisWarp = (Warp) environment.getElement(myPos.y() +1, myPos.x());
                ArrayList<IMazeElement> list = environment.getListElement(Warp.class);
                if (list.size() == 2 && list.get(0) == thisWarp) {
                    environment.addElement(null, myPos.y(), myPos.x());
                    Warp thatWarp = (Warp) list.get(1);
                    Environment.Position position = environment.getPositionOf(thatWarp);
                    environment.addElement(this, position.y()+1, position.x());
                } else if (list.size() == 2 && list.get(1) == thisWarp) {
                    Warp thatWarp = (Warp) list.get(0);
                    Environment.Position position = environment.getPositionOf(thatWarp);
                    environment.addElement(this, position.y()+1, position.x());
                }
            }
        } else {
            environment.addElement(this, myPos.y() + 1, myPos.x());
            environment.addElement(mazeElement, myPos.y(), myPos.x());
        }
    }
    protected void moveLeft(Environment.Position myPos){
        MazeElement mazeElement = environment.getElement(myPos.y(), myPos.x() - 1);
        ArrayList<IMazeElement> listA = environment.getListElement(Warp.class);
        if (mazeElement instanceof Warp) {
            if (listA.size() == 2) {
                environment.addElement(null, myPos.y(), myPos.x());
                Warp thisWarp = (Warp) environment.getElement(myPos.y(), myPos.x() - 1);
                ArrayList<IMazeElement> list = environment.getListElement(Warp.class);
                if (list.size() == 2 && list.get(0) == thisWarp) {
                    Warp thatWarp = (Warp) list.get(1);
                    Environment.Position position = environment.getPositionOf(thatWarp);
                    environment.addElement(this, position.y(), position.x() - 1);
                } else if (list.size() == 2 && list.get(1) == thisWarp) {
                    Warp thatWarp = (Warp) list.get(0);
                    Environment.Position position = environment.getPositionOf(thatWarp);
                    environment.addElement(this, position.y(), position.x() - 1);
                }
            }
        } else {
        environment.addElement(this, myPos.y(), myPos.x() - 1);
        environment.addElement(mazeElement, myPos.y(), myPos.x());
        }
    }
    protected void moveRight(Environment.Position myPos){
        MazeElement mazeElement = environment.getElement(myPos.y(), (myPos.x() + 1));
        ArrayList<IMazeElement> listA = environment.getListElement(Warp.class);
        if (mazeElement instanceof Warp) {
            if (listA.size() == 2) {
                environment.addElement(null, myPos.y(), myPos.x());
                Warp thisWarp = (Warp) environment.getElement(myPos.y(), myPos.x() + 1);
                ArrayList<IMazeElement> list = environment.getListElement(Warp.class);
                if (list.size() == 2 && list.get(0) == thisWarp) {
                    Warp thatWarp = (Warp) list.get(1);
                    Environment.Position position = environment.getPositionOf(thatWarp);
                    environment.addElement(this, position.y(), position.x() + 1);
                } else if (list.size() == 2 && list.get(1) == thisWarp) {
                    Warp thatWarp = (Warp) list.get(0);
                    Environment.Position position = environment.getPositionOf(thatWarp);
                    environment.addElement(this, position.y(), position.x() + 1);
                }
            }
        } else {
        environment.addElement(this, myPos.y(), myPos.x() + 1);
        environment.addElement(mazeElement, myPos.y(), myPos.x());
        }
    }
}
