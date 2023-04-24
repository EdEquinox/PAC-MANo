package pt.isec.pa.tinypac.model.data.elements.ghosts;

import pt.isec.pa.tinypac.model.data.Environment;

public class Clyde extends Ghost{
    protected Clyde(Environment environment) {
        super(environment);
    }

    @Override
    public char getSymbol() {
        return 0;
    }

    @Override
    public void evolve() {

    }
}
