package pt.isec.pa.tinypac.model.fsm.states;

import org.junit.jupiter.api.Test;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.model.fsm.PacmanStateAdapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndGameState extends PacmanStateAdapter {
    public EndGameState(PacmanContext context, Environment data) {
        super(context,data);
    }

    @Override
    public PacmanState getState() {
        return PacmanState.ENDGAME;
    }

    @Override
    public boolean saveScore(String username) {
        data.saveScore(username);
        return true;
    }
}
