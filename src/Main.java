import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class starts the application and sets up the window.
 * @author Danny Metcalfe
 */
public class Main extends Application {

    public int x = 0;
    public final double screenSize = 1000;

    /**
     * Starts the application.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tetris");
        BorderPane gameLayout = new BorderPane();
        Scene gameScene = new Scene(gameLayout);
        primaryStage.setScene(gameScene);
        primaryStage.setHeight(screenSize);
        primaryStage.setWidth(screenSize);
        primaryStage.show();
        Board gameBoard = new Board(gameLayout);
        int[][] testGrid = {{0,1,0},{1,1,1}, {0,1,0}};
        Board.TetrisShape test = gameBoard.new TetrisShape(3, Color.AQUA, testGrid);
        //gameBoard.addTetrisPiece(test);
        testGrid = new int[][]{{0, 1, 0}, {1,1,0}, {0,1,0}};
        Board.TetrisShape test2 = gameBoard.new TetrisShape(3, Color.RED, testGrid);
        gameBoard.addTetrisPiece( test);

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.LEFT)){
                    gameBoard.moveShape(-1,0,test2);
                }
                else if(event.getCode().equals(KeyCode.DOWN)){
                    gameBoard.moveShape(0,1,test2);
                }
                else if(event.getCode().equals(KeyCode.RIGHT)){
                    gameBoard.moveShape(1,0,test2);
                }
                else if(event.getCode().equals(KeyCode.D)){
                    gameBoard.rotateShape(1, test2);
                }
                else if(event.getCode().equals(KeyCode.A)){
                    gameBoard.rotateShape(-1, test2);
                }
            }
        });

    }
}