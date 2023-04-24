package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.elements.PacmanData;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.model.fsm.PacmanStateAdapter;

public class InitLevelState extends PacmanStateAdapter {
    public InitLevelState(PacmanContext context, Environment data) {
        super(context, data);
    }

    @Override
    public PacmanState getState() {
        return PacmanState.INIT_LEVEL;
    }

    @Override
    public boolean changeDirection(PacmanData.Directions currentDirection) {
        //muda a direção tipo usar estados, por exemplo
        changeState(PacmanState.MOVING);
        return true;
    }
}
