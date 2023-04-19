package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.PacmanData;

public abstract class PacmanStateAdapter implements IPacmanState{
    protected PacmanContext context;
    protected PacmanData data;

    protected PacmanStateAdapter (PacmanContext context, PacmanData data){
        this.context = context;
        this.data = data;
    }

    protected void changeState(PacmanState newState){
        context.changeState(PacmanState.createState(newState,context,data));
    }
    public boolean died() {
        return false;
    }

    public boolean changeDirection(PacmanData.Directions currentDirection) {
        return false;
    }

    public boolean nextLevel() {
        return false;
    }

    public boolean pause() {
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

    public boolean ghostsBusted() {
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
}
