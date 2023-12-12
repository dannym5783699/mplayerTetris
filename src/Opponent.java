import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

/**
 * The Opponent class represents an opponent in a game,
 * managing their board state, name, score, and level.
 * It provides methods to update and display these attributes in a GUI using JavaFX.
 */
public class Opponent {
    private String playerName;
    private int[][] boardMatrix;
    private Label playerNameLabel;
    private GridPane boardGrid;
    private HBox playerHBox;
    private long opScore;
    private int opLevel;
    private int opClears;


    String strMatrix = "1-0-1-2-3-4-5-1-0-1-2-3-4-111111";


    /**
     * Constructor for Opponent.
     * Initializes the opponent's name, board matrix, and JavaFX elements for displaying the opponent's data.
     */
    public Opponent() {
        this.playerName = "Default";
        this.boardMatrix = new int[40][20]; // 40x20 matrix
        this.playerNameLabel = new Label(this.playerName);

        this.boardGrid = new GridPane();

        this.playerHBox = new HBox();
        this.playerHBox.getChildren().addAll(boardGrid, playerNameLabel);

        setUIElement();
    }

    /**
     * Sets the board matrix based on a given string representation.
     *
     * @param stringMatrix A string representing the board state.
     */
    public void setBoardMatrix(String stringMatrix) {
        int k = 0;
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                if (k < stringMatrix.length()) {
                    if(stringMatrix.charAt(k) == '-'){
                        boardMatrix[i][j] = -1;
                        k+=2;
                    }
                    else {
                        boardMatrix[i][j] = Character.getNumericValue(stringMatrix.charAt(k));
                        k++;
                    }
                }
            }
        }

    }

    /**
     * Updates the UI elements based on the current state of the board matrix.
     * Each cell in the board grid is represented by a colored pane.
     */
    public void setUIElement() {
        boardGrid.getChildren().clear();
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Pane pane = new Pane();
                pane.setPrefSize(5, 5);
                Color color;
                switch (boardMatrix[i][j]) {
                    case 6:
                        color = Color.WHITE;
                        break;
                    case 0:
                        color = Color.RED;
                        break;
                    case 1:
                        color = Color.CADETBLUE;
                        break;
                    case 2:
                        color = Color.PURPLE;
                        break;
                    case 3:
                        color = Color.LIME;
                        break;
                    case 4:
                        color = Color.DARKOLIVEGREEN;
                        break;
                    case 5:
                        color = Color.PINK;
                        break;
                    default:
                        color = Color.GRAY;
                }
                pane.setBackground(new Background(new BackgroundFill(color, null, null)));
                boardGrid.add(pane, j, i);
            }
        }
    }

    /**
     * Gets the opponents board
     * @return player's hbox
     */
    public HBox getPlayerHBox() {
        return playerHBox;
    }

    /**
     * Gets the player name
     * @return player name
     */
    public String getName(){
        return this.playerName;
    }

    /**
     * Sets the name of the opponent and updates the corresponding UI label.
     *
     * @param name The new name to be set for the opponent.
     */
    public void setName(String name){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                playerNameLabel.setText(name);
            }
        });

        this.playerName = name;
    }

    /**
     * Gets opponent score
     * @return opponent score
     */
    public long getOpScore() {
        return opScore;
    }

    /**
     * Sets opponent score
     * @param opScore value being set
     */
    public void setOpScore(long opScore) {
        this.opScore = opScore;
    }

    /**
     * Get opponent level
     * @return opponent level
     */
    public int getOpLevel() {
        return opLevel;
    }

    /**
     * Sets opponent level
     * @param opLevel value being set
     */
    public void setOpLevel(int opLevel) {
        this.opLevel = opLevel;
    }

    /**
     * Gets the number of lines cleared by the opponent.
     *
     * @return The number of lines cleared by the opponent.
     */
    public int getOpClears() {
        return opClears;
    }

    /**
     * Sets the number of lines cleared by the opponent.
     *
     * @param opClears The new number of lines cleared to be set for the opponent.
     */
    public void setOpClears(int opClears) {
        this.opClears = opClears;
    }
}
