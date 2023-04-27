package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.elements.Pacman;
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
    public boolean died() {
        //data.perdeVida();
        changeState(PacmanState.INIT_LEVEL);
        return true;
    }

    @Override
    public boolean nextLevel() {
        //logica de carregamento de um novo mapa
        changeState(PacmanState.INIT_LEVEL);
        return true;
    }

    @Override
    public boolean changeDirection() {
        changeState(PacmanState.MOVING);
        return true;
    }

    @Override
    public boolean pause(PacmanState currentState) {
        changeState(PacmanState.PAUSE);
        return true;
    }

    @Override
    public boolean gg() {
        //perdeu
        //passa dados de jogo tipo pontos
        changeState(PacmanState.ENDGAME);
        return true;
    }

    @Override
    public boolean ggwp() {
        //ganhou
        changeState(PacmanState.ENDGAME);
        return true;
    }

    @Override
    public boolean eatBigBall() {
        //muda comportamento dos fantasmas
        changeState(PacmanState.LUNCH_TIME);
        return true;
    }

}
