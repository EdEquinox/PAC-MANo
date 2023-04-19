package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.PacmanData;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.model.fsm.PacmanStateAdapter;

public class EndGameState extends PacmanStateAdapter {
    public EndGameState(PacmanContext context, PacmanData data) {
        super(context,data);
    }

    @Override
    public PacmanState getState() {
        return PacmanState.ENDGAME;
    }

    @Override
    public boolean saveScore() {
        //sai da maquina de estados
        return true;
    }
}
