package pt.isec.pa.tinypac.model.data.elements.ghosts;

import pt.isec.pa.tinypac.model.data.Environment;

public class Pinky extends Ghost{
    protected Pinky(Environment environment) {
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
