package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.data.elements.Pacman;

public abstract class PacmanStateAdapter implements IPacmanState {
    protected PacmanContext context;
    protected Environment data;

    protected PacmanStateAdapter (PacmanContext context, Environment data){
        this.context = context;
        this.data = data;
    }

    protected void changeState(PacmanState newState){
        context.changeState(PacmanState.createState(newState,context,data));
    }

    public boolean changeDirection() {
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

    public boolean saveScore() {
        return false;
    }
    public boolean evolve(){
        return false;
    }
    public Pacman.Directions getCurrentDirection() {
        return Pacman.Directions.NADA;
    }
}
