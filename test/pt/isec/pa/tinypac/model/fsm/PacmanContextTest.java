package pt.isec.pa.tinypac.model.fsm;

import org.junit.jupiter.api.Test;
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.data.elements.Pacman;
import pt.isec.pa.tinypac.model.data.elements.SuperCoin;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Pinky;

import static org.junit.jupiter.api.Assertions.*;

class PacmanContextTest {

    @Test
    void testPause_LUNCH_TIME() {
        // Arrange
        Environment data = new Environment();
        PacmanContext fsm = new PacmanContext(data);
        data.addElement(new Pacman(data), 4,4);
        data.addElement(new SuperCoin(data),4,5);

        // Act
        fsm.changeDirection(MazeElement.Directions.RIGHT);
        fsm.evolve();
        fsm.pause(fsm.getState());
        fsm.resume();

        // Assert
        assertEquals(PacmanState.LUNCH_TIME, fsm.getState());

    }

    @Test
    void testIfDieAndLoseGame() {
        // Arrange
        Environment data = new Environment();
        PacmanContext fsm = new PacmanContext(data);
        data.addElement(new Pacman(data), 4,4);
        data.addElement(new Pinky(data),4,5);
        data.setLives(1);

        // Act
        fsm.changeDirection(MazeElement.Directions.RIGHT);
        fsm.evolve();

        // Assert
        assertEquals(PacmanState.ENDGAME, fsm.getState());
    }

    @Test
    public void testChangeDirection_INIT_STATE(){
        // Arrange
        Environment data = new Environment();
        PacmanContext fsm = new PacmanContext(data);

        // Act
        fsm.changeDirection(MazeElement.Directions.RIGHT);

        // Assert
        assertEquals(PacmanState.MOVING, fsm.getState());
    }
}