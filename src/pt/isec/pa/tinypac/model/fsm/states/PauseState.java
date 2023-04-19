package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.PacmanData;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.model.fsm.PacmanStateAdapter;

public class PauseState extends PacmanStateAdapter {
    public PauseState(PacmanContext context, PacmanData data) {
        super(context, data);
    }

    @Override
    public PacmanState getState() {
        return PacmanState.PAUSE;
    }

    @Override
    public boolean resume() {
        //terá que saber qual o estado que estava anteriormente
        PacmanState state = null;
        switch (state){
            case MOVING -> {}
            case LUNCH_TIME -> {}
        }
        return true;
    }

    @Override
    public boolean saveGame() {
        //logica de guardar jogo
        return true;
    }

    @Override
    public boolean leaveGame() {
        changeState(PacmanState.ENDGAME);
        return true;
    }
}
