package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.elements.Pacman;

public interface IPacmanState {
    PacmanState getState();
    //boolean died();             //carlão morre e perde uma vida
    boolean changeDirection();  //muda direção
    //boolean nextLevel();        //passa de nivel e muda o mapa
    boolean pause(PacmanState currentState);            //manda para pausa
    boolean resume();           //volta ao jogo
    boolean saveGame();         //guarda jogo(quem diria)
    boolean leaveGame();        //vai para o menu de final
    boolean saveScore();        //finaliza o jogo, guarda ou nao o score e sai do fsm
    boolean evolve();
    Pacman.Directions getCurrentDirection();
}
