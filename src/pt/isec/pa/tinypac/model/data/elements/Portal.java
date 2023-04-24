package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;

public class Portal extends MazeElement {
    public static final char SYMBOL = 'Y';
    public Portal(Environment environment) {
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
