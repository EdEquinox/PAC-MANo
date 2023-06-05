package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.elements.Pacman;

public abstract class PacmanStateAdapter implements IPacmanState, IGameEngineEvolve {
    protected PacmanContext context;
    protected Environment data;

    protected PacmanStateAdapter (PacmanContext context, Environment data){
        this.context = context;
        this.data = data;
    }

    protected void changeState(PacmanState newState){
        context.changeState(PacmanState.createState(newState,context,data));
    }
    public boolean died() {
        return false;
    }

    public boolean changeDirection() {
        return false;
    }

    public boolean nextLevel() {
        return false;
    }

    public boolean pause(PacmanState currentState) {
        return false;
    }

    public boolean resume() {
        return false;
    }

    public boolean saveGame() {
        return false;
    }

    public boolean leaveGame() {
        return false;
    }

    public boolean eatBigBall() {
        return false;
    }

    public boolean timesUp() {
        return false;
    }

    public boolean gg() {
        return false;
    }

    public boolean initGame() {
        changeState(PacmanState.INIT_LEVEL);
        return false;
    }

    public boolean saveScore() {
        return false;
    }
    public boolean ggwp() {
        return false;
    }
    public boolean evolve(){
        return false;
    }
    public Pacman.Directions getCurrentDirection() {
        return Pacman.Directions.UP;
    }
}
