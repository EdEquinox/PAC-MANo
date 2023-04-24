package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;

public class Wall extends MazeElement {
    public static final char SYMBOL = 'x';

    public Wall(Environment environment) {
        super(environment);
    }

    @Override
    public void evolve() {

    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
