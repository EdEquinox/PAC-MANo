package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.PacmanData;

public class PacmanContext {
    private IPacmanState state;
    private PacmanData data;

    public PacmanContext() {
        data = new PacmanData();
        state = PacmanState.createState(PacmanState.INIT_LEVEL,this,data);
    }

    void changeState(IPacmanState newState) {
        state = newState;
    }

    public boolean died(){
        return state.died();
    }

    public boolean changeDirection(PacmanData.Directions currentDirection){
        return state.changeDirection(currentDirection);
    }
    public boolean nextLevel(){
        return state.nextLevel();
    }
    public boolean pause() {
        return state.pause();
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

    //getters

    public PacmanState getState() {
        return state.getState();
    }

    public PacmanData.Directions getCurrentDirection() {
        return data.getCurrentDirection();
    }

    public int getnLives() {
        return data.getnLives();
    }
}
