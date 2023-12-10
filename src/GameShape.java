import java.util.Random;

/**
 * Will create a shape object in the form of a 3x4 matrix for our board
 */
public class GameShape {
    private final Random random = new Random();

    private final int colorNumber;
    private final int previousColor;

    private final int shapeNumber;
    private final int previousShape;

    private final int[][] shapeMatrix;
    private final int shapeRow = 4;
    private final int shapeCol = 4;

    private int currentBoardRow = 0;
    private int currentBoardCol = 3;

    public GameShape(final int previousColor,final int previousShape) {
        this.previousColor = previousColor;
        colorNumber = establishColorNumber();

        this.previousShape = previousShape;

        shapeNumber = establishShapeNumber();
        shapeMatrix = establishShapeMatrix();
    }

    // Returns the random shape number between 1 and 6 but not the previously used shape number
    private int establishColorNumber() {
        int randomNumber;

        do {
            randomNumber = random.nextInt(9) + 1;
        } while (randomNumber == previousColor);

        return randomNumber;
    }

    // Returns a random shape number between 1 and 6 but not the previously used shape number
    private int establishShapeNumber() {
        int randomNumber;

        do {
            randomNumber = random.nextInt(6) + 1;
        } while (randomNumber == previousShape);

        return randomNumber;
    }

    // Establishes which shape yours is based off the shapeNumber
    private int[][] establishShapeMatrix() {
        switch (shapeNumber) {
            case 1 -> {
                return shape1();
            }
            case 2 -> {
                return shape2();
            }
            case 3 -> {
                return shape3();
            }
            case 4 -> {
                return shape4();
            }
            case 5 -> {
                return shape5();
            }
            default -> {
                return shape6();
            }
        }
    }

    // 0100
    // 0110
    // 0010
    // Returns the above shape, but with the colorNumber rather than the 1
    private int[][] shape1() {
        int[][] matrix = getEmptyShapeMatrix();

        matrix[0][1] = colorNumber;
        matrix[1][1] = colorNumber;
        matrix[1][2] = colorNumber;
        matrix[2][2] = colorNumber;

        return matrix;
    }

    // 0110
    // 0100
    // 0100
    // Returns the above shape, but with the colorNumber rather than the 1
    private int[][] shape2() {
        int[][] matrix = getEmptyShapeMatrix();

        matrix[0][1] = colorNumber;
        matrix[0][2] = colorNumber;
        matrix[1][1] = colorNumber;
        matrix[2][1] = colorNumber;

        return matrix;
    }

    // 0010
    // 0110
    // 0010
    // Returns the above shape, but with the colorNumber rather than the 1
    private int[][] shape3() {
        int[][] matrix = getEmptyShapeMatrix();

        matrix[0][2] = colorNumber;
        matrix[1][1] = colorNumber;
        matrix[1][2] = colorNumber;
        matrix[2][2] = colorNumber;

        return matrix;
    }

    // 1111
    // 0000
    // 0000
    // Returns the above shape, but with the colorNumber rather than the 1
    private int[][] shape4() {
        int[][] matrix = getEmptyShapeMatrix();

        matrix[0][0] = colorNumber;
        matrix[0][1] = colorNumber;
        matrix[0][2] = colorNumber;
        matrix[0][3] = colorNumber;

        return matrix;
    }

    // 0010
    // 0110
    // 0100
    // Returns the above shape, but with the colorNumber rather than the 1
    private int[][] shape5() {
        int[][] matrix = getEmptyShapeMatrix();

        matrix[0][2] = colorNumber;
        matrix[1][1] = colorNumber;
        matrix[1][2] = colorNumber;
        matrix[2][1] = colorNumber;

        return matrix;
    }

    // 0110
    // 0010
    // 0010
    // Returns the above shape, but with the colorNumber rather than the 1
    private int[][] shape6() {
        int[][] matrix = getEmptyShapeMatrix();

        matrix[0][1] = colorNumber;
        matrix[0][2] = colorNumber;
        matrix[1][2] = colorNumber;
        matrix[2][2] = colorNumber;

        return matrix;
    }

    // Returns and shape matrix full of 0s
    private int[][] getEmptyShapeMatrix() {
        int[][] matrix = new int[shapeRow][shapeCol];

        for (int row = 0; row < shapeRow; row++) {
            for (int col = 0; col < shapeCol; col++) {
                matrix[row][col] = 0;
            }
        }

        return matrix;
    }

    /**
     * @return the shapes color number
     */
    public int getColorNumber() {
        return colorNumber;
    }

    /**
     * @return the shape number
     */
    public int getShapeNumber() {
        return shapeNumber;
    }

    /**
     * @return the shapes matrix
     */
    public int[][] getShapeMatrix() {
        return shapeMatrix;
    }

    /**
     * Adds a row to the current board row
     */
    public void setCurrentBoardRow() {
        currentBoardRow++;
    }

    /**
     * @return the current board row for the given shape
     */
    public int getCurrentBoardRow() {
        return currentBoardRow;
    }

    /**
     * @param col the column number that indicates the start of the shape matrix
     */
    public void setCurrentBoardCol(final int col) {
        currentBoardCol = col;
    }

    /**
     * @return the current board column that the endge of the shape matrix is against
     */
    public int getCurrentBoardCol() {
        return currentBoardCol;
    }
}
