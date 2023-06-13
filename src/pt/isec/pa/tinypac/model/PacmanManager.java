package pt.isec.pa.tinypac.model;


import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.ui.gui.GameUI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;

public class PacmanManager{

    public static final String PROP_STATE = "_state_"; // it's assumed that includes UIUPDATE
    public static final String PROP_UIUPDATE = "_ui_";
    PacmanContext fsm;
    PropertyChangeSupport pcs;
    Top5 top5;
    boolean start;

    //region contructors
    public PacmanManager() {
        this.fsm = new PacmanContext();
        this.pcs = new PropertyChangeSupport(this);
    }

    public PacmanManager(Environment environment) {
        this.fsm = new PacmanContext(environment);
        this.pcs = new PropertyChangeSupport(this);
    }
    //endregion

    //region observer/observable managenment
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    //endregion

    //region transitions
    public void changeDirection(MazeElement.Directions directions){
        fsm.changeDirection(directions);
        pcs.firePropertyChange(PROP_UIUPDATE,null,null);
    }

    public void evolve() {
        pcs.firePropertyChange(PROP_STATE,null,null);
        pcs.firePropertyChange(PROP_UIUPDATE,null,null);
        fsm.evolve();
    }
    public void pause() {
        fsm.pause(getState());
        pcs.firePropertyChange(PROP_STATE,null,null);
        pcs.firePropertyChange(PROP_UIUPDATE,null,null);
    }

    public void leaveGame() {
        fsm.leaveGame();
        pcs.firePropertyChange(PROP_STATE,null,null);
        pcs.firePropertyChange(PROP_UIUPDATE,null,null);
    }
    public void resume() {
        fsm.resume();
        pcs.firePropertyChange(PROP_STATE,null,null);
        pcs.firePropertyChange(PROP_UIUPDATE,null,null);
    }

    public Environment load(File file){
        try(FileInputStream fs = new FileInputStream(file);
            ObjectInputStream ds = new ObjectInputStream(fs);){

            Environment environment = (Environment) ds.readObject();
            return environment;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("erro a loadar"
                    + "A criar um jogo novo");
            return null;
        } finally {
            file.delete();
        }
    }

    public void save() {
        try(FileOutputStream fs = new FileOutputStream("files/game.bin");
            ObjectOutputStream os = new ObjectOutputStream(fs)){
            os.writeObject(fsm.getEnvironment());


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("erro a gravar");
        }
    } //nao é transiçao meter como uma funcao

    public void saveScore(String username) {
        fsm.saveScore(username);
        pcs.firePropertyChange(null,null,null);
    }
    //endregion
    //region gets
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

    public String getTime() {
        return String.valueOf(fsm.getTime());
    }
    //endregion
}
