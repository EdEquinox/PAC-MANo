package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.elements.PacmanData;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.model.fsm.PacmanStateAdapter;

public class PauseState extends PacmanStateAdapter {
    PacmanState previousState;
    public PauseState(PacmanContext context, Environment data, PacmanState state) {
        super(context, data);
        previousState = state;
    }

    @Override
    public PacmanState getState() {
        return PacmanState.PAUSE;
    }

    @Override
    public boolean resume() {
        //terá que saber qual o estado que estava anteriormente
        switch (previousState){
            case MOVING -> changeState(PacmanState.MOVING);
            case LUNCH_TIME -> changeState(PacmanState.LUNCH_TIME);
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
