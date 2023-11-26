/**
 * This class handles score calculations and holds the values.
 *
 * @author Danny Metcalfe
 */
public class ScoreHandler {

    private long currentScore = 0;

    private int currentLevel = 0;

    //Score where calculations are based off of.
    private final static int initSingleScore = 100;

    private int lineClears = 0;

    private int msSubtraction = 0;


    /**
     * Creates a new scoreHandler, no arguments.
     */
    public ScoreHandler(){}

    /**
     * Updates the score depending on how many lines are cleared. Also adds points based on level.
     * @param clears how many rows were cleared.
     */
    public void updateScore(int clears){
        if(clears != 0) {
            int score = initSingleScore;
            for (int i = 0; i < clears; i++) {
                score *= 2;
            }
            currentScore = currentScore + score + ((currentLevel-1) * 100L);
        }
    }

    /**
     * Sets the current level of the game.
     * Stops changing at level 50. This will be as fast as possible at level 50.
     * @param currentLevel level to set.
     */
    public void setLevel(int currentLevel){
        if(currentLevel >= 1 && currentLevel <= 50){
            this.currentLevel = currentLevel;
        }
    }


    /**
     * Gets the current game level.
     * @return returns the game difficulty level.
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Gets the current game score.
     * @return Returns the current game score.
     */
    public long getCurrentScore() {
        return currentScore;
    }

    /**
     * Gets how many lines have been cleared.
     * @return returns the number of rows cleared.
     */
    public int getLineClears() {
        return lineClears;
    }

    /**
     * Sets how many lines are cleared.
     * @param lineClears number of lines that have been cleared.
     */
    public void setLineClears(int lineClears) {
        this.lineClears = lineClears;
    }

    /**
     * Gets how many milliseconds to subtract from the interval based on level. Max is 1000ms.
     * @return returns time to subtract from total.
     */
    public int getMsSubtraction() {
        return msSubtraction;
    }

    /**
     * Calculates milliseconds to take of the interval based on 20ms per level. Max at 1000ms.
     */
    public void updateTiming() {
        msSubtraction = Math.min(currentLevel * 20, 1000);

    }
}
