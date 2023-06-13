package pt.isec.pa.tinypac.model.fsm;


//so importa data
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.data.elements.*;

import java.io.*;
import java.util.ArrayList;

public class PacmanContext {
    private IPacmanState state;
    private Environment data;
    public String FILE = "files/Level19.txt";

    //region constructors
    //contrutor para ler de ficheiro
    public PacmanContext() {
        data = new Environment().readFile(FILE);
        state = PacmanState.createState(PacmanState.INIT_LEVEL,this,data);
    }

    //construtor para load game
    public PacmanContext(Environment environment) {
        data = environment;
        state = PacmanState.createState(PacmanState.MOVING,this,data);
    }
    //endregion

    // package-private
    void changeState(IPacmanState newState) {
        state = newState;
    }

    //region TRANSITIONS
    public void changeDirection(MazeElement.Directions directions){
        state.changeDirection(directions);
    }
    public void leaveGame(){
        state.leaveGame();
    }
    public void pause(PacmanState currentState) {
        state.pause(currentState);
    }
    public void resume(){
        state.resume();
    }
    public void evolve() {
        state.evolve();
    }
    public void saveScore(String username){
        state.saveScore(username);
    }

    //endregion

    //region GETTERS
    public PacmanState getState() {
        return state.getState();
    }
    public int getScore() {
        return data.getScore();
    }
    public char[][] getMaze() {
        return data.getMaze();
    }
    public boolean getSuper() {
        return data.isSuper();
    }
    public int getNLives(){
        return data.getnLives();
    }
    public Pacman.Directions getDirection(){
        return data.getPacman().getCurrentDirection();
    }
    public int getTime() {
        return data.getTimeSuper();
    }
    public <T extends IMazeElement> ArrayList<IMazeElement> getListElement(Class<T> type){
        return data.getListElement(type);
    }
    public Environment getEnvironment() {
        return data;
    }
    //endregion



}
