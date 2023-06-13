package pt.isec.pa.tinypac.ui.text;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.ui.text.utils.PAInput;

import java.io.IOException;

public class PacManTUI {
    PacmanContext fsm;

    public PacManTUI(PacmanContext fsm) {
        this.fsm = fsm;
    }

    public void start() throws IOException {
        while (true) {
            System.out.println(" ");
            System.out.println("/DEIS-ISEC-IPC/      /LEI/      /Programação/" +
                    "      /2022-2023/      /José Marques - 2018019295/");
            System.out.println("____________________");
            System.out.println("--- MENU INICIAL ---");
            switch (PAInput.chooseOption("Escolha a opção! ",
                    "Iniciar jogo!", "Top 5","Teste", "Sair")) {
                case 1 -> begin();
                case 2 -> System.out.println("Top 5 não implementado");
                case 3 -> beginTeste();
                case 4 -> {
                    if (sair()) return;
                }
            }
       }
    }
    private void begin() throws IOException {
        PacManLanternaUI pacManLanternaUI = new PacManLanternaUI(fsm);
        GameEngine gameEngine = new GameEngine();
        //gameEngine.registerClient(fsm);
        gameEngine.registerClient(pacManLanternaUI);
        gameEngine.start(500);
        gameEngine.waitForTheEnd();
    }
    private boolean sair() {
        switch (PAInput.chooseOption("Deseja mesmo sair, amigo? ",
                "Sim", "Não")) {
            case 1 -> {
                return true;
            }
            case 2 -> {
                return false;
            }
        }
        return false;
    }

    //region TESTE ONLY

    private boolean finish = false;
    private void beginTeste() {
        finish = false;
        while (!finish){
            System.out.println("O jogo está no estado " + fsm.getState().toString());
            switch (fsm.getState()){
                case INIT_LEVEL -> initGame();
                case MOVING -> moving();
                case PAUSE -> pause();
                case LUNCH_TIME -> lunchTime();
                case ENDGAME -> endgame();
            }
        }
    }

    private void pause() {
        switch (PAInput.chooseOption(" AÇÕES ",
                "VOLTAR", "GUARDAR SCORE", "ABANDONAR JOGO")){
            case 1 ->fsm.resume();
            //case 2-> fsm.saveGame();
            case 3-> fsm.leaveGame();
        }

    }
    private void endgame() {
        System.out.println("O teu score é " + Math.random());
        finish = true;
    }

    private void lunchTime() {
        switch (PAInput.chooseOption(" AÇÕES ",
                "PAUSA", "ACABOU O TEMPO", "TUDO COMIDO")){
            case 1 ->fsm.pause(fsm.getState());

        }
    }

    private void moving() {
        switch (PAInput.chooseOption(" AÇÕES ",
                "MOVER", "MORRER", "PASSAR NIVEL", "PAUSA", "GANHAR", "PERDER", "FICAR SUPER")){
            case 1 ->{
                switch (PAInput.chooseOption(" MOVER ",
                        "UP", "DOWN", "RIGHT", "LEFT")) {
                    case 1: {
                        fsm.changeDirection(MazeElement.Directions.UP);
                    }
                    case 2: {
                        fsm.changeDirection(MazeElement.Directions.DOWN);
                    }
                    case 3: {
                        fsm.changeDirection(MazeElement.Directions.RIGHT);
                    }
                    case 4: {
                        fsm.changeDirection(MazeElement.Directions.LEFT);
                    }
                }
            }

            case 4 -> fsm.pause(fsm.getState());

        }
    }


    private void initGame() {
        switch (PAInput.chooseOption(" MOVER ",
                "UP", "DOWN", "RIGHT", "LEFT")) {
            case 1: {
                fsm.changeDirection(MazeElement.Directions.UP);
            }
            case 2: {
                fsm.changeDirection(MazeElement.Directions.DOWN);
            }
            case 3: {
                fsm.changeDirection(MazeElement.Directions.RIGHT);
            }
            case 4: {
                fsm.changeDirection(MazeElement.Directions.LEFT);
            }
        }
    }
    //endregion FIM DOS TESTES
}
