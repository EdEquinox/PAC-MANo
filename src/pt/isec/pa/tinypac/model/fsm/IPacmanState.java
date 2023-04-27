package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.elements.Pacman;

public interface IPacmanState {
    PacmanState getState();
    boolean died();             //carlão morre e perde uma vida
    boolean changeDirection();  //muda direção
    boolean nextLevel();        //passa de nivel e muda o mapa
    boolean pause(PacmanState currentState);            //manda para pausa
    boolean resume();           //volta ao jogo
    boolean saveGame();         //guarda jogo(quem diria)
    boolean leaveGame();        //vai para o menu de final
    boolean eatBigBall();       //quando como a bola grande e passa para super
    boolean timesUp();          //acaba o tempo de comer
    boolean ghostsBusted();     //comeu todos os fantasmas
    boolean gg();               //ganhou ou perdeu, talvez sejam preciso 2
    boolean initGame();         //inicia o jogo, entra no fsm
    boolean saveScore();        //finaliza o jogo, guarda ou nao o score e sai do fsm
    boolean ggwp();
    Pacman.Directions getCurrentDirection();
}
