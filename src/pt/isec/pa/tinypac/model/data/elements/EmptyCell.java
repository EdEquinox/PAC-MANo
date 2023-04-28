package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;

public class EmptyCell extends MazeElement {
    public static final char SYMBOL = ' ';

    public EmptyCell(Environment environment) {
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
