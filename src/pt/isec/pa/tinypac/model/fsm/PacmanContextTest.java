package pt.isec.pa.tinypac.model.fsm;

import org.junit.jupiter.api.Test;
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.fsm.states.InitLevelState;

import static org.junit.jupiter.api.Assertions.*;

class PacmanContextTest {

    @Test
    public void testChangeState(){
        // Arrange

        Environment data = new Environment().readFile("files/Level1.txt");
        PacmanContext fsm = new PacmanContext();

        fsm.changeState(new InitLevelState(fsm,data));

        // Act

        fsm.changeDirection(MazeElement.Directions.RIGHT);
        PacmanState state = fsm.getState();

        // Assert

        assertEquals(PacmanState.MOVING, state);
    }

    //todo os meus testes estao nesta pasta why





}