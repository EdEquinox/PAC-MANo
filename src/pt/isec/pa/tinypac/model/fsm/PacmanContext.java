package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.elements.Pacman;

public class PacmanContext {
    private IPacmanState state;
    private Environment data;

    public PacmanContext() {
        data = new Environment(31,30);
        state = PacmanState.createState(PacmanState.INIT_LEVEL,this,data);
    }

    void changeState(IPacmanState newState) {
        state = newState;
    }

    public boolean died(){
        return state.died();
    }

    public boolean changeDirection(){
        return state.changeDirection();
    }
    public boolean nextLevel(){
        return state.nextLevel();
    }
    public boolean pause(PacmanState currentState) {
        return state.pause(currentState);
    }
    public boolean resume(){
        return state.resume();
    }
    public boolean saveGame(){
        return state.saveGame();
    }
    public boolean leaveGame(){
        return state.leaveGame();
    }
    public boolean eatBigBall(){
        return state.eatBigBall();
    }
    public boolean timesUp(){
        return state.timesUp();
    }
    public boolean ghostsBusted(){
        return state.ghostsBusted();
    }
    public boolean gg(){
        return state.gg();
    }
    public boolean initGame(){
        return state.initGame();
    }
    public boolean saveScore(){
        return state.saveScore();
    }
    public boolean ggwp() {
        return state.ggwp();
    }
    //getters

    public PacmanState getState() {
        return state.getState();
    }


}
