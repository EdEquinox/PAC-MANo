package pt.isec.pa.tinypac.model.data.elements.ghosts;

import pt.isec.pa.tinypac.model.data.Environment;

public class Inky extends Ghost{
    protected Inky(Environment environment) {
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
