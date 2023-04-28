package pt.isec.pa.tinypac.ui.text;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.data.EnvironmentManager;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.utils.PAInput;

import java.io.IOException;

public class PacManTUI {
    PacmanContext fsm;
    EnvironmentManager environmentManager;

    public PacManTUI(PacmanContext fsm,EnvironmentManager environmentManager) {
        this.fsm = fsm;
        this.environmentManager = environmentManager;
    }

    public void start() throws IOException {
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

    private void begin() throws IOException {
        PacManLanternaUI pacManLanternaUI = new PacManLanternaUI(fsm,environmentManager);
        GameEngine gameEngine = new GameEngine();
        gameEngine.registerClient(environmentManager);
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
}
