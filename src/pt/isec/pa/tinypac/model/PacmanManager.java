package pt.isec.pa.tinypac.model;


import pt.isec.pa.tinypac.model.data.Environment;
import pt.isec.pa.tinypac.model.data.MazeElement.Directions;
import pt.isec.pa.tinypac.model.fsm.PacmanContext;
import pt.isec.pa.tinypac.model.fsm.PacmanState;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;

public class PacmanManager{
    PacmanContext fsm;
    PropertyChangeSupport pcs;

    //region contructors
    /**
     * Create empty manager
     */
    public PacmanManager() {
        this.fsm = new PacmanContext();
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Create manager from loader
     *
     * @param  environment loaded environment
     */
    public PacmanManager(Environment environment) {
        this.fsm = new PacmanContext(environment);
        this.pcs = new PropertyChangeSupport(this);
    }
    //endregion

    //region observer/observable managenment
    /**
     * Add a PropertyChangeListener to the listener list.
     *
     * @param  listener The PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    //endregion

    //region transitions
    /**
     * Change direction of pacman (state transition)
     *
     * @param  directions inteded direction
     */
    public void changeDirection(Directions directions){
        fsm.changeDirection(directions);
        pcs.firePropertyChange(null,null,null);
    }

    /**
     * Evolve game (state transition)
     */
    public void evolve() {
        pcs.firePropertyChange(null,null,null);
        fsm.evolve();
    }
    /**
     * Pauses game (state transition)
     */
    public void pause() {
        fsm.pause(getState());
        pcs.firePropertyChange(null,null,null);
        pcs.firePropertyChange(null,null,null);
    }
    /**
     * Leaves game (state transition)
     */
    public void leaveGame() {
        fsm.leaveGame();
        pcs.firePropertyChange(null,null,null);
        pcs.firePropertyChange(null,null,null);
    }
    /**
     * Resumes game (state transition)
     */
    public void resume() {
        fsm.resume();
        pcs.firePropertyChange(null,null,null);
        pcs.firePropertyChange(null,null,null);
    }
    /**
     * Saves score (state transition)
     *
     * @param username name of the user
     */
    public void saveScore(String username) {
        fsm.saveScore(username);
        pcs.firePropertyChange(null,null,null);
    }
    //endregion

    //region serial
    /**
     * Loads environment
     *
     * @param path path of the file
     * @return loaded environment
     */
    public Environment load(String path){
        File file = new File(path);
        try(FileInputStream fs = new FileInputStream(file);
            ObjectInputStream ds = new ObjectInputStream(fs)){
            return (Environment) ds.readObject();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("erro a loadar"
                    + "A criar um jogo novo");
            return null;
        } finally {
            file.delete();
        }
    }
    /**
     * Saves game
     */
    public void save() {
        try(FileOutputStream fs = new FileOutputStream("files/game.bin");
            ObjectOutputStream os = new ObjectOutputStream(fs)){
            os.writeObject(fsm.getEnvironment());


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("erro a gravar");
        }
    }
    /**
     * Check if file exists
     *
     * @param path path of the file
     * @return returns boolean based on the file existance
     */
    public boolean checkFile(String path){
        File file = new File(path);
        return file.exists();
    }
    //endregion



    //region gets
    /**
     * Get fsm state
     *
     * @return fsm state
     */
    public PacmanState getState(){
        return fsm.getState();
    }
    /**
     * Get number of lives
     *
     * @return number of lives
     */
    public int getNLives() {
        return fsm.getNLives();
    }
    /**
     * Get score
     *
     * @return score
     */
    public int getScore() {
        return fsm.getScore();
    }
    /**
     * Get maze
     *
     * @return maze
     */
    public char[][] getMaze() {
        return fsm.getMaze();
    }
    /**
     * Get time of super pacman
     *
     * @return time of super pacman
     */
    public String getTime() {
        return String.valueOf(fsm.getTime());
    }
    /**
     * Get current level
     *
     * @return current level
     */
    public int getLevel() {
        return fsm.getLevel();
    }
    /**
     * Get number of coins remaining
     *
     * @return number of coins remaining
     */
    public String getCoins() {
        return fsm.getCoins();
    }
    /**
     * Checks if environment is viable for playing the game
     *
     * @return boolean based on the environment viability
     */
    public boolean checkEnv() {
        return fsm.checkEnv();
    }
    /**
     * Gets reference to pacman
     *
     * @return reference to pacman
     */
    public String getTimeGhost() {
        return fsm.getTimeGhost();
    }
    /**
     * Gets current direction of the pacman
     *
     * @return current direction of the pacman
     */
    public Directions getCurrentDirection(){
        return fsm.getDirection();
    }
    /**
     * Gets current direction of the Pinky
     *
     * @return current direction of the Pinky
     */
    public Directions getCurrentDirectionPinky(){
        return fsm.getDirectionPinky();
    }
    /**
     * Gets current direction of the Blinky
     *
     * @return current direction of the Blinky
     */
    public Directions getCurrentDirectionBlinky(){
        return fsm.getDirectionBlinky();
    }
    /**
     * Gets current direction of the Clyde
     *
     * @return current direction of the Clyde
     */
    public Directions getCurrentDirectionClyde(){
        return fsm.getDirectionClyde();
    }
    /**
     * Gets current direction of the Inky
     *
     * @return current direction of the Inky
     */
    public Directions getCurrentDirectionInky(){
        return fsm.getDirectionInky();
    }
    //endregion
}
