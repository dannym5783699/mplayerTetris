import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class Opponent {
    private String playerName;
    private int[][] boardMatrix;
    private Label playerNameLabel;
    private GridPane boardGrid;
    private HBox playerHBox;

    String strMatrix = "1-0-1-2-3-4-5-1-0-1-2-3-4-111111";

    public Opponent() {
        this.playerName = "Default";
        this.boardMatrix = new int[40][20]; // 40x20 matrix
        this.playerNameLabel = new Label(this.playerName);

        this.boardGrid = new GridPane();

        this.playerHBox = new HBox();
        this.playerHBox.getChildren().addAll(boardGrid, playerNameLabel);

        setUIElement();
    }

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

    public HBox getPlayerHBox() {
        return playerHBox;
    }

    public String getName(){
        return this.playerName;
    }

}
