package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.model.fsm.PacmanStateAdapter;

public class EndGameState extends PacmanStateAdapter {
    public EndGameState(PacmanContext context, Environment data) {
        super(context,data);
    }

    @Override
    public PacmanState getState() {
        return PacmanState.ENDGAME;
    }

    @Override
    public boolean saveScore() {

        return true;
    }

}
