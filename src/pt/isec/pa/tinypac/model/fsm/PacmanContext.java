package pt.isec.pa.tinypac.model.fsm;


//so importa data
import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement;
import pt.isec.pa.tinypac.model.data.elements.*;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Blinky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Clyde;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Inky;
import pt.isec.pa.tinypac.model.data.elements.ghosts.Pinky;

public class PacmanContext {
    private IPacmanState state;
    private final Environment data;

    //region constructors
    //contrutor para ler de ficheiro
    public PacmanContext() {
        data = new Environment();
        state = PacmanState.createState(PacmanState.INIT_LEVEL,this,data);
    }

    //construtor para load game
    public PacmanContext(Environment environment) {
        data = environment;
        state = PacmanState.createState(PacmanState.MOVING,this,data);
    }
    //endregion

    // package-private
    void changeState(IPacmanState newState) {
        state = newState;
    }

    //region TRANSITIONS
    public void changeDirection(MazeElement.Directions directions){
        state.changeDirection(directions);
    }
    public void leaveGame(){
        state.leaveGame();
    }
    public void pause(PacmanState currentState) {
        state.pause(currentState);
    }
    public void resume(){
        state.resume();
    }
    public void evolve() {
        state.evolve();
    }
    public void saveScore(String username){
        state.saveScore(username);
    }

    //endregion

    //region GETTERS
    public PacmanState getState() {
        return state.getState();
    }
    public int getScore() {
        return data.getScore();
    }
    public char[][] getMaze() {
        return data.getMaze();
    }
    public boolean getSuper() {
        return data.isSuper();
    }
    public int getNLives(){
        return data.getnLives();
    }
    public Pacman.Directions getDirection(){
        return data.getPacman().getCurrentDirection();
    }
    public Pacman.Directions getDirectionPinky(){
        return ((Pinky)data.getElement(Pinky.class)).getCurrentDirection();
    }
    public Pacman.Directions getDirectionInky(){
        return ((Inky)data.getElement(Inky.class)).getCurrentDirection();
    }
    public Pacman.Directions getDirectionBlinky(){
        return ((Blinky)data.getElement(Blinky.class)).getCurrentDirection();
    }
    public Pacman.Directions getDirectionClyde(){
        return ((Clyde)data.getElement(Clyde.class)).getCurrentDirection();
    }
    public int getTime() {
        return data.getTimeSuper();
    }
    public Environment getEnvironment() {
        return data;
    }

    public int getLevel() {
        return data.getLevel();
    }

    public String getCoins() {
        return data.getCoins();
    }

    public boolean checkEnv() {
        return data.checkEnv();
    }

    public Pacman getPacman() {
        return data.getPacman();
    }

    public String getTimeGhost() {
        return String.valueOf(data.getTimeGhost());
    }
    //endregion



}
