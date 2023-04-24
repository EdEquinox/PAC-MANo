package pt.isec.pa.tinypac.model.data;

public abstract class MazeElement implements IMazeElement{
    protected Environment environment;

    public MazeElement(Environment environment) {
        this.environment = environment;
    }
    abstract public void evolve();
}
