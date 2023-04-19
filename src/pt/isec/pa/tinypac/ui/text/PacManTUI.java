package pt.isec.pa.tinypac.ui.text;

import pt.isec.pa.tinypac.model.data.PacmanData;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import pt.isec.pa.tinypac.utils.PAInput;

public class PacManTUI {
    PacmanContext fsm;

    public PacManTUI(PacmanContext fsm) {
        this.fsm = fsm;
    }

    public void start() {
        while (true) {
            System.out.println(" ");
            System.out.println("/DEIS-ISEC-IPC/      /LEI/      /Programação/" +
                    "      /2022-2023/      /José Marques - 2018019295/");
            System.out.println("____________________");
            System.out.println("--- MENU INICIAL ---");
            switch (PAInput.chooseOption("Escolha a opção! ",
                    "Iniciar jogo!", "Top 5", "Sair")) {
                case 1 -> begin();
                case 2 -> System.out.println("Top 5 não implementado");
                case 3 -> {
                    if (sair()) return;
                }
            }
       }
   }

    private boolean finish = false;
    private void begin() {
        finish = false;
        while (!finish){
            System.out.println("O Carlão está no estado " + fsm.getState().toString());
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
            case 2-> fsm.saveGame();
            case 3-> fsm.leaveGame();
        }

    }
    private void endgame() {
        System.out.println("O teu score é " + Math.random());
        fsm.saveScore();
        finish = true;
    }

    private void lunchTime() {
        switch (PAInput.chooseOption(" AÇÕES ",
                "PAUSA", "ACABOU O TEMPO", "TUDO COMIDO")){
            case 1 ->fsm.pause(fsm.getState());
            case 2-> fsm.timesUp();
            case 3-> fsm.ghostsBusted();
        }
    }

    private void moving() {
        switch (PAInput.chooseOption(" AÇÕES ",
                "MOVER", "MORRER", "PASSAR NIVEL", "PAUSA", "GANHAR", "PERDER", "FICAR SUPER")){
            case 1 ->{
                switch (PAInput.chooseOption(" MOVER ",
                        "UP", "DOWN", "RIGHT", "LEFT")) {
                    case 1: {
                        fsm.changeDirection(PacmanData.Directions.UP);
                    }
                    case 2: {
                        fsm.changeDirection(PacmanData.Directions.DOWN);
                    }
                    case 3: {
                        fsm.changeDirection(PacmanData.Directions.RIGHT);
                    }
                    case 4: {
                        fsm.changeDirection(PacmanData.Directions.LEFT);
                    }
                }
                System.out.println("O Carlão está a ir para" + fsm.getCurrentDirection().toString());
            }
            case 2-> fsm.died();
            case 3-> fsm.nextLevel();
            case 4 -> fsm.pause(fsm.getState());
            case 5 -> fsm.ggwp();
            case 6 -> fsm.gg();
            case 7 -> fsm.eatBigBall();
        }
    }


    private void initGame() {
        fsm.initGame();
        switch (PAInput.chooseOption(" MOVER ",
                "UP", "DOWN", "RIGHT", "LEFT")) {
            case 1: {
                fsm.changeDirection(PacmanData.Directions.UP);
            }
            case 2: {
                fsm.changeDirection(PacmanData.Directions.DOWN);
            }
            case 3: {
                fsm.changeDirection(PacmanData.Directions.RIGHT);
            }
            case 4: {
                fsm.changeDirection(PacmanData.Directions.LEFT);
            }
            System.out.println("O Carlão está a ir para" + fsm.getCurrentDirection().toString());
        }
        //começa a ui da maquina de estados
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
}
