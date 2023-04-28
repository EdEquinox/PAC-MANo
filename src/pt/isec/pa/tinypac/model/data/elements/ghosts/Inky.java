package pt.isec.pa.tinypac.model.data.elements.ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;

import java.util.Random;

public class Inky extends Ghost{
    public static final char SYMBOL = 'i';
    private MazeElement.Directions currentDirection;
    public Inky(Environment environment) {
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
