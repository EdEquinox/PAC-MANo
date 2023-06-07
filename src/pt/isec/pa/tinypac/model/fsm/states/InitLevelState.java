package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.model.fsm.PacmanStateAdapter;

public class InitLevelState extends PacmanStateAdapter {
    public InitLevelState(PacmanContext context, Environment data) {
        super(context, data);
        data.revive();
        data.setCoin();
    }

    @Override
    public PacmanState getState() {
        return PacmanState.INIT_LEVEL;
    }

    @Override
    public boolean changeDirection() {
        changeState(PacmanState.MOVING);
        return true;
    }


}
