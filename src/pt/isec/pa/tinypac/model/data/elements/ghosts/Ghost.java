package pt.isec.pa.tinypac.model.data.elements.ghosts;

import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.MazeElement;

public abstract class Ghost extends MazeElement {
    protected Environment environment;

    protected Ghost(Environment environment){
        super(environment);
    }


    abstract public void evolve();
}
