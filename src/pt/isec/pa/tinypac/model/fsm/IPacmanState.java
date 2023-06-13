package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.data.elements.Pacman;

public interface IPacmanState {

    boolean changeDirection(MazeElement.Directions directions);  //muda direção
    boolean pause(PacmanState currentState);            //manda para pausa
    boolean resume();           //volta ao jogo
    boolean leaveGame();        //vai para o menu de final
    boolean saveScore(String username);        //finaliza o jogo, guarda ou nao o score e sai do fsm
    boolean evolve();
    Pacman.Directions getCurrentDirection();
    PacmanState getState();
}
