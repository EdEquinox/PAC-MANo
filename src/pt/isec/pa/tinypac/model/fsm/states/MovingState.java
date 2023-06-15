package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.model.fsm.PacmanStateAdapter;

public class MovingState extends PacmanStateAdapter {
    public MovingState(PacmanContext context, Environment data) {
        super(context, data);
    }

    @Override
    public PacmanState getState() {
        return PacmanState.MOVING;
    }
    
    @Override
    public boolean changeDirection(MazeElement.Directions directions) {
        data.changeDirection(directions);
        changeState(PacmanState.MOVING);
        return true;
    }

    @Override
    public boolean pause(PacmanState currentState) {
        changeState(PacmanState.PAUSE);
        return true;
    }

    @Override
    public boolean evolve() {
        data.evolve();
        if (data.isSuper()){                   //comeu a bola?
            changeState(PacmanState.LUNCH_TIME);
            return true;
        } else if (data.isDead()){//morreu?
            if (data.gameLost()) {//ultima vida?
                changeState(PacmanState.ENDGAME);
                return true;}
            changeState(PacmanState.INIT_LEVEL);
            return true;
        }else if (data.nextLvl()) {//acabou as moedas?
            if (data.gameWin()){
                changeState(PacmanState.ENDGAME);
                return true;
            }
            changeState(PacmanState.INIT_LEVEL);
            return true;
        }
        return false;
    }

    //deprecated
    /*@Override
    public boolean died() {
        changeState(PacmanState.INIT_LEVEL);
        return true;
    }

    @Override
    public boolean nextLevel() {
        changeState(PacmanState.INIT_LEVEL);
        return true;
    }
    @Override
    public boolean gg() {
        changeState(PacmanState.ENDGAME);
        return true;
    }

    @Override
    public boolean ggwp() {
        changeState(PacmanState.ENDGAME);
        return true;
    }

    @Override
    public boolean eatBigBall() {
        changeState(PacmanState.LUNCH_TIME);
        return true;
    }*/
    //

}
