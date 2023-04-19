package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.PacmanData;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.model.fsm.PacmanStateAdapter;

public class LunchTime extends PacmanStateAdapter {
    public LunchTime(PacmanContext context, PacmanData data) {
        super(context, data);
    }

    @Override
    public PacmanState getState() {
        return PacmanState.LUNCH_TIME;
    }

    @Override
    public boolean timesUp() {
        //pensar melhor o que é preciso fazer

        changeState(PacmanState.MOVING);
        return true;
    }

    @Override
    public boolean ghostsBusted() {
        //pensar melhor o que é preciso fazer
        changeState(PacmanState.MOVING);
        return true;
    }

    @Override
    public boolean pause() {
        changeState(PacmanState.PAUSE);
        return true;
    }
}
