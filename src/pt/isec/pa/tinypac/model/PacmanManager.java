package pt.isec.pa.tinypac.model;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.fsm.IPacmanState;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PacmanManager implements IGameEngineEvolve{

    private PacmanContext fsm;
    PropertyChangeSupport pcs;

    public PacmanManager() {
        this.fsm = new PacmanContext();
        this.pcs = new PropertyChangeSupport(this);
    }

    public PacmanManager(Environment environment) {
        this.fsm = new PacmanContext(environment);
        this.pcs = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void changeDirection(MazeElement.Directions directions){
        fsm.changeDirection(directions);
        pcs.firePropertyChange(null,null,null);
    }
    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        pcs.firePropertyChange(null,null,null);
        fsm.evolve(gameEngine,currentTime);
    }
    public PacmanState getState(){
        return fsm.getState();
    }

    public int getNLives() {
        return fsm.getNLives();
    }

    public int getScore() {
        return fsm.getScore();
    }

    public char[][] getMaze() {
        return fsm.getMaze();
    }

    public PacmanContext getFsm() {
        return fsm;
    }

    public void pause() {
        fsm.pause(getState());
        pcs.firePropertyChange(null,null,null);
    }


    public void exit() {
        fsm.leaveGame();
        pcs.firePropertyChange(null,null,null);
    }

    public void save() {
        fsm.save();
    }

    public void resume() {
        fsm.resume();
        pcs.firePropertyChange(null,null,null);
    }

    public void saveScore() {
        fsm.saveScore();
        pcs.firePropertyChange(null,null,null);
    }
}
