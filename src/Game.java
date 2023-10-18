import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class works to handle running the game and using the board to do this.
 * @author Danny Metcalfe
 */
public class Game {
    private final Board tetrisBoard;

    private final AnimationTimer timer;

    private int timeSec = 1;

    private final ArrayList<int[][]> availShapes = new ArrayList<>();

    private final ArrayList<Color> availColors = new ArrayList<>();

    private Board.TetrisShape currentShape = null;

    //How many kinds of shapes and colors.
    private int numShapes = 5;
    private int numColors = 5;

    private boolean hasEnd = false;

    /**
     * Creates a game and runs it.
     * @param gamePane
     * @param gameScene
     */
    public Game(BorderPane gamePane, Scene gameScene){
        tetrisBoard = new Board(gamePane);
        availShapes.add(0, new int [][]{{0,1,0},{1,1,0},{0,1,0}});
        availShapes.add(new int[][]{{0,0,0},{1,1,1},{0,0,1}});
        availShapes.add(new int[][]{{1,0,0,0}, {1,0,0,0}, {1,0,0,0}, {1,0,0,0}});
        availShapes.add(new int[][]{{1,1},{1,1}});
        availShapes.add(new int[][]{{0,1,0},{1,1,0},{1,0,0}});
        //availShapes.add(new int[][]{{1,0,0,0}, {1,1,1,1}, {0, 0, 0,1},{0,0,0,0}});

        availColors.add(0, Color.RED);
        availColors.add(Color.CADETBLUE);
        availColors.add(Color.PURPLE);
        availColors.add(Color.LIME);
        availColors.add(Color.DARKOLIVEGREEN);

        Random random = new Random();

        // use for moving shape down.
        timer = new AnimationTimer() {
            private long last = 0;
            private long wait = 0;
            @Override
            public void handle(long now) {
                if((currentShape == null || !currentShape.isCanMove()) && !hasEnd &&
                        now-wait > Duration.ofMillis(0).toNanos()){
                    if(currentShape != null) {
                        int shapeSize = currentShape.getShapeSize();
                        for (int i = shapeSize -1 ; i >= 0; i--){
                            int deleteRow = currentShape.getY() + i;
                            if(deleteRow< tetrisBoard.getRows() && deleteRow >= 0 &&
                                    tetrisBoard.fullRow(deleteRow)){
                                tetrisBoard.deleteRow(deleteRow);
                                i++;
                            }
                        }
                    }
                    int randNum = random.nextInt(0, numShapes);
                    int randCol = random.nextInt(0, numColors);
                    int size = availShapes.get(randNum).length;
                    int[][] shape = availShapes.get(randNum);
                    Board.TetrisShape newShape = tetrisBoard.new TetrisShape(size, availColors.get(randCol), shape);
                    hasEnd = !tetrisBoard.addTetrisPiece(newShape);
                    if(hasEnd){
                        currentShape = null;
                    }
                    else{
                        currentShape = newShape;
                    }
                    wait = now;
                }
                else if(now-last > Duration.ofSeconds(timeSec).toNanos() && currentShape != null){
                    tetrisBoard.moveShape(0, 1, currentShape);
                    last = now;
                }
            }
        };
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(currentShape != null && !hasEnd) {
                    if (event.getCode().equals(KeyCode.LEFT)) {
                        tetrisBoard.moveShape(-1, 0, currentShape);
                    } else if (event.getCode().equals(KeyCode.DOWN)) {
                        tetrisBoard.moveShape(0, 1, currentShape);
                    } else if (event.getCode().equals(KeyCode.RIGHT)) {
                        tetrisBoard.moveShape(1, 0, currentShape);
                    } else if (event.getCode().equals(KeyCode.D)) {
                        tetrisBoard.rotateShape(1, currentShape);
                    } else if (event.getCode().equals(KeyCode.A)) {
                        tetrisBoard.rotateShape(-1, currentShape);
                    }
                }
            }
        });
        timer.start();
    }

}
