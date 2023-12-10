import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GameBoard {
    private final int[][] boardMatrix;

    private final int boardRows = 40;
    private final int boardCols = 10;

    private final List<GameShape> shapes = new ArrayList<>();
    private GameShape currentShape;

    public GameBoard() {
        boardMatrix = establishBoardMatrix();
    }

    private int[][] establishBoardMatrix() {
        int[][] matrix = new int[boardRows][boardCols];

        for (int row = 0; row < boardRows; row++) {
            for (int col = 0; col < boardCols; col++) {
                matrix[row][col] = 0;
            }
        }

        return matrix;
    }

    private void addShape() {
        // Establishing the new shape
        GameShape shape;
        if (shapes.isEmpty()) {
            shape = new GameShape(0, 0);
        } else {
            GameShape previousShape = shapes.get(shapes.size() - 1);
            shape = new GameShape(previousShape.getColorNumber(), previousShape.getShapeNumber());
        }

        shapes.add(shape);
        currentShape = shape;

        // Setting the matrix
        int[][] shapeMatrix = shape.getShapeMatrix();

        for (int row = 0; row < 3; row++) {
            for (int col = 3; col < 7; col++) {
                int adjustedCol = col - 3;
                boardMatrix[row][col] = shapeMatrix[row][adjustedCol];
            }
        }
    }

    private void moveShapeDown() {
        if (checkMoveDown()) {
            int currentRow = currentShape.getCurrentBoardRow();
            int[][] shapeMatrix = currentShape.getShapeMatrix();

            // Clearing Previous Row
            int boardLastRow = currentRow + 4;
            if (boardLastRow >= 40) {
                boardLastRow = 40;
            }
            for (int col = 3; col < 7; col++) {
                boardMatrix[currentRow][col] = 0;
                for (int row = currentRow + 1; row < boardLastRow; row++) {
                    int adjustedRow = row - currentRow - 1;
                    int adjustedCol = col - 3;
                    boardMatrix[row][col] = shapeMatrix[adjustedRow][adjustedCol];
                }
            }
            currentShape.setCurrentBoardRow();
        } else {
            addShape();
        }
    }

    private boolean checkMoveDown() {
        int currentRow = currentShape.getCurrentBoardRow();
        int[][] shapeMatrix = currentShape.getShapeMatrix();

        for (int row = currentRow + 1; row < currentRow + 4; row++) {
            for (int col = 3; col < 7; col++) {
                int adjustedRow = row - currentRow - 1;
                int adjustedCol = col - 3;

                if (shapeMatrix[adjustedRow][adjustedCol] != 0 && shapeMatrix[adjustedRow + 1][adjustedCol] == 0) {
                    if (row >= boardRows || boardMatrix[row][col] != 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void moveShapeLeft() {
        if (checkMoveLeft()) {
            int currentCol = currentShape.getCurrentBoardCol();
            int currentRow = currentShape.getCurrentBoardRow();
            int[][] shapeMatrix = currentShape.getShapeMatrix();

            // Setting new piece
            for (int row = currentRow; row < currentRow + 3; row++) {
                for (int col = currentCol - 1; col < currentCol + 3; col++) {
                    int adjustedRow = row - currentRow;
                    int adjustedCol = col - currentCol + 1;

                    if (shapeMatrix[adjustedRow][adjustedCol] != 0) {
                        boardMatrix[row][col+1] = 0;
                        boardMatrix[row][col] = shapeMatrix[adjustedRow][adjustedCol];
                    }
                }
            }
            currentShape.setCurrentBoardCol(currentCol - 1);
        }
    }

    private boolean checkMoveLeft() {
        int currentCol = currentShape.getCurrentBoardCol();
        int currentRow = currentShape.getCurrentBoardRow();
        int[][] shapeMatrix = currentShape.getShapeMatrix();

        for (int row = currentRow; row < currentRow + 3; row++) {
            for (int col = currentCol - 1; col < currentCol + 3; col++) {
                int adjustedRow = row - currentRow;
                int adjustedCol = col - currentCol + 1;

                if (shapeMatrix[adjustedRow][adjustedCol] != 0 && shapeMatrix[adjustedRow][adjustedCol - 1] == 0) {
                    if (col <= 0 || boardMatrix[row][col] != 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void printBoard() {
        System.out.println("Current Board:");
        for (int row = 0; row < boardRows; row++) {
            for (int col = 0; col < boardCols; col++) {
                System.out.print(boardMatrix[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        gameBoard.addShape();
        gameBoard.printBoard();
        for (int i = 0; i < 5; i++) {
            gameBoard.moveShapeLeft();
            gameBoard.printBoard();
        }
    }
}
