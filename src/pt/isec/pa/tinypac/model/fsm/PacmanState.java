package pt.isec.pa.tinypac.model.fsm;


import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.elements.PacmanData;
import pt.isec.pa.tinypac.model.fsm.states.*;

public enum PacmanState {
    INIT_LEVEL, MOVING, LUNCH_TIME, PAUSE, ENDGAME;

    static IPacmanState createState(PacmanState type, PacmanContext context, Environment data){
        return switch (type){
            case INIT_LEVEL -> new InitLevelState(context,data);
            case MOVING -> new MovingState(context,data);
            case LUNCH_TIME -> new LunchTime(context,data);
            case PAUSE -> new PauseState(context,data,context.getState());
            case ENDGAME -> new EndGameState(context,data);
        };
    }
}
