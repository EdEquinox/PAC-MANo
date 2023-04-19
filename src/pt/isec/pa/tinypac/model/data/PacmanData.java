package pt.isec.pa.tinypac.model.data;

public class PacmanData {
    public enum Directions{UP,DOWN,RIGHT,LEFT}
    int nLives;
    Directions currentDirection;

    public PacmanData() {
        this.nLives = 3;
        this.currentDirection = Directions.RIGHT;
    }

    public void perdeVida() {
        this.nLives = nLives-1;
    }

    public void setCurrentDirection(Directions currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Directions getCurrentDirection() {
        return currentDirection;
    }

    public int getnLives() {
        return nLives;
    }
}
