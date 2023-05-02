package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.model.fsm.PacmanStateAdapter;

public class MovingState extends PacmanStateAdapter {
    public MovingState(PacmanContext context, Environment data) {
        super(context, data);
    }

    @Override
    public PacmanState getState() {
        return PacmanState.MOVING;
    }

    @Override
    public boolean died() {
        changeState(PacmanState.INIT_LEVEL);
        return true;
    }

    @Override
    public boolean nextLevel() {
        changeState(PacmanState.INIT_LEVEL);
        return true;
    }

    @Override
    public boolean changeDirection() {
        changeState(PacmanState.MOVING);
        return true;
    }

    @Override
    public boolean pause(PacmanState currentState) {
        changeState(PacmanState.PAUSE);
        return true;
    }

    @Override
    public boolean gg() {
        changeState(PacmanState.ENDGAME);
        return true;
    }

    @Override
    public boolean ggwp() {
        changeState(PacmanState.ENDGAME);
        return true;
    }

    @Override
    public boolean eatBigBall() {
        changeState(PacmanState.LUNCH_TIME);
        return true;
    }

}
