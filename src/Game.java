import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;


/**
 * This class works to handle running the game and using the board to do this.
 *
 * @author Danny Metcalfe
 */
public class Game {
    private final Board tetrisBoard;

    private final ArrayList<int[][]> availShapes = new ArrayList<>();

    private final ArrayList<Color> availColors = new ArrayList<>();

    private Board.TetrisShape currentShape = null;

    //How many kinds of shapes and colors.
    private static final int numShapes = 7;
    private static final int numColors = 6;

    private boolean hasEnd = false;

    private final ScoreHandler scoreHandler = new ScoreHandler();

    /**
     * Creates a game and runs it.
     *
     * @param gamePane  The BorderPane for the game to create a board.
     * @param gameScene the scene of the game to set the key events.
     */
    public Game(BorderPane gamePane, Scene gameScene) {
        tetrisBoard = new Board(gamePane);
        //Adding shapes. Can add any shapes that fit in the board.
        availShapes.add(0, new int[][]{{0, 1, 0}, {1, 1, 0}, {0, 1, 0}});
        availShapes.add(new int[][]{{0, 0, 0}, {1, 1, 1}, {0, 0, 1}});
        availShapes.add(new int[][]{{1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}});
        availShapes.add(new int[][]{{1, 1}, {1, 1}});
        availShapes.add(new int[][]{{0, 1, 0}, {1, 1, 0}, {1, 0, 0}});
        availShapes.add(new int[][]{{0, 1, 0}, {0, 1, 1}, {0, 0, 1}});
        availShapes.add(new int[][]{{1, 0, 0}, {1, 0, 0}, {1, 0, 0}});
        //availShapes.add(new int[][]{{1,0,0,0}, {1,1,1,1}, {0, 0, 0,1},{0,0,0,0}});

        //Adding colors. Can add any colors.
        availColors.add(0, Color.RED);
        availColors.add(Color.CADETBLUE);
        availColors.add(Color.PURPLE);
        availColors.add(Color.LIME);
        availColors.add(Color.DARKOLIVEGREEN);
        availColors.add(Color.PINK);

        Random random = new Random();

        VBox scoring = new VBox();
        gamePane.setRight(scoring);
        scoring.setSpacing(60);
        scoring.setAlignment(Pos.CENTER);
        scoring.setTranslateX(-70);
        Label currentLevel = new Label("Current Level: 1");
        scoring.getChildren().add(currentLevel);
        Label currentScore = new Label("Current Score: 0");
        scoring.getChildren().add(currentScore);
        Label linesCleared = new Label("Current line clears: 0");
        scoring.getChildren().add(linesCleared);

        AnimationTimer timer = new AnimationTimer() {
            private long last = 0;

            @Override
            public void handle(long now) {
                //Runnning the game.
                if ((currentShape == null || !currentShape.isCanMove()) && !hasEnd) {
                    if (currentShape != null) {
                        int shapeSize = currentShape.getShapeSize();
                        int deletes = 0;
                        for (int i = shapeSize - 1; i >= 0; i--) {
                            int deleteRow = currentShape.getY() + i;
                            if (deleteRow < tetrisBoard.getRows() && deleteRow >= 0 &&
                                    tetrisBoard.fullRow(deleteRow)) {
                                tetrisBoard.deleteRow(deleteRow);
                                deletes++;
                                i++;
                            }
                        }
                        scoreHandler.setLineClears(scoreHandler.getLineClears() + deletes);
                        scoreHandler.updateScore(deletes);
                        currentScore.setText("Current Score: " + scoreHandler.getCurrentScore());
                        linesCleared.setText("Current line clears: " + scoreHandler.getLineClears());

                        scoreHandler.setLevel((scoreHandler.getLineClears() / 5) + 1);
                        currentLevel.setText("Current level: " + scoreHandler.getCurrentLevel());
                        scoreHandler.updateTiming();

                    }
                    int randNum = random.nextInt(0, numShapes);
                    int randCol = random.nextInt(0, numColors);
                    int size = availShapes.get(randNum).length;
                    int[][] shape = availShapes.get(randNum);
                    Board.TetrisShape newShape = new Board.TetrisShape(size, availColors.get(randCol), shape);
                    hasEnd = !tetrisBoard.addTetrisPiece(newShape);
                    if (hasEnd) {
                        currentShape = null;
                    } else {
                        currentShape = newShape;
                    }
                } else if (now - last > Duration.ofMillis(1000 - scoreHandler.getMsSubtraction()).toNanos() &&
                        currentShape != null) {
                    tetrisBoard.moveShape(0, 1, currentShape);
                    last = now;
                }
            }
        };

        // Set an event handler for key presses on the game scene
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // Check if there is a current shape and the game has not ended
                if (currentShape != null && !hasEnd) {
                    // Move the shape left when the LEFT arrow key is pressed
                    if (event.getCode().equals(KeyCode.LEFT)) {
                        tetrisBoard.moveShape(-1, 0, currentShape);
                    }
                    // Move the shape down faster when the DOWN arrow key is pressed
                    else if (event.getCode().equals(KeyCode.DOWN)) {
                        tetrisBoard.moveShape(0, 1, currentShape);
                    }
                    // Move the shape right when the RIGHT arrow key is pressed
                    else if (event.getCode().equals(KeyCode.RIGHT)) {
                        tetrisBoard.moveShape(1, 0, currentShape);
                    }
                    // Rotate the shape when the UP arrow key is pressed
                    else if (event.getCode().equals(KeyCode.UP)) {
                        tetrisBoard.rotateShape(1, currentShape);
                    }
                }
            }
        });
        // Start the game timer to handle game updates
        timer.start();
    }
}

