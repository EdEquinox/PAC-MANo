package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;

public class Fruit extends MazeElement {

    public static final char SYMBOL = 'F';
    public Fruit(Environment environment) {
        super(environment);
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }

    @Override
    public void evolve() {

    }
}
