package pt.isec.pa.tinypac.model.data.elements.ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;

import java.util.Random;

public class Pinky extends Ghost{
    public static final char SYMBOL = 'p';
    private MazeElement.Directions currentDirection;
    public Pinky(Environment environment) {
        super(environment);
        this.environment = environment;
        currentDirection = Directions.RIGHT;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }

    @Override
    public void evolve() {
        if (environment.timeGhost()) {
            //movimenta
        }
    }

}
