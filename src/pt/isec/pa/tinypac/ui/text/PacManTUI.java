package pt.isec.pa.tinypac.ui.text;

import pt.isec.pa.tinypac.utils.PAInput;

public class PacManTUI {

        public void start() {
        while (true) {
            System.out.println(" ");
            System.out.println("/DEIS-ISEC-IPC/      /LEI/      /Programação/" +
                    "      /2022-2023/      /José Marques - 2018019295/");
            System.out.println("____________________");
            System.out.println("--- MENU INICIAL ---");
            switch (PAInput.chooseOption("Escolha a opção! ",
                    "Iniciar jogo!", "Top 5", "Sair")) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:
                 if (sair())return;

           }
       }
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
