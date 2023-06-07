package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.Environment;
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

   
}
