package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.model.fsm.PacmanStateAdapter;

public class LunchTime extends PacmanStateAdapter {
    public LunchTime(PacmanContext context, Environment data) {
        super(context, data);
    }

    @Override
    public PacmanState getState() {
        return PacmanState.LUNCH_TIME;
    }

    @Override
    public boolean pause(PacmanState currentState) {
        changeState(PacmanState.PAUSE);
        return true;
    }
    public boolean changeDirection(MazeElement.Directions directions) {
        data.changeDirection(directions);
        changeState(PacmanState.LUNCH_TIME);
        return true;
    }

    @Override
    public boolean evolve() {
        data.evolve();
        if (data.timesUp()){
            data.resetTime();
            changeState(PacmanState.MOVING);
            return true;
        } else if (data.ghostsBusted()){
            changeState(PacmanState.MOVING);
            return true;
        } else if (data.nextLvl()) {
            data.getPacman().setCurrentDirection(MazeElement.Directions.NADA);
            changeState(PacmanState.INIT_LEVEL);
            return true;
        } else if (data.gameLost()) {
            changeState(PacmanState.ENDGAME);
            return true;
        } else if (data.gameWin()) {
            changeState(PacmanState.ENDGAME);
            return true;
        }
        return false;
    }
}
