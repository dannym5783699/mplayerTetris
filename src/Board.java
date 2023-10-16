import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.function.Predicate;

/**
 * This class handles most of the board related functions such as display. Also moving shapes.
 * @author Danny Metcalfe
 */
public class Board {
    private final BorderPane gameLayout;
    private final GridPane gameGrid;
    private final StackPane gameStack;
    private final Rectangle border;

    private final int[][] fullGrid;
    private final int columns = 20;
    private final int rows = 40;

    /**
     * Class for handling individual Tetris shapes.
     */
    public class TetrisShape{
        private int x = 0;
        private int y = 0;
        private int[][] squareLocations;
        private final int shapeSize;
        private final Color color;
        private boolean canMove = true;

        /**
         * Creates a new shape.
         * @param size size of the shape of the longest part. ex 1 by 4 rectangle size should be 4.
         * @param color color of the shape.
         * @param squares a grid that represents the shape. Where numbers are positions of the squares.
         */
        public TetrisShape(int size, Color color, int[][] squares){
            shapeSize = size;
            this.color = color;
            if(size > 0 && size == squares.length && size == squares[0].length){
                squareLocations = new int[size][size];
                for(int c = 0; c<size; c++){
                    for(int r = 0; r<size; r++){
                        squareLocations[c][r] = squares[c][r];
                    }
                }

            }
        }
        private boolean setX(int x){
            if(x >= -2 && x<columns){
                this.x = x;
                return true;
            }
            return false;
        }

        private boolean setY(int y){
            if(y>=-2 && y<rows){
                this.y = y;
                return true;
            }
            return false;
        }

        /**
         * Gets the size of the shape.
         * @return Returns shape size.
         */
        public int getShapeSize() {
            return shapeSize;
        }

        /**
         * Gets the array representation of the shape, only for the board to use except when created.
         * @return returns an int[][] that holds the shape representation.
         */
        private int[][] getSquareLocations(){
            return squareLocations;
        }

        private void setSquareLocations(int [][] newArray){
            this.squareLocations = newArray;
        }
    }

    /**
     * Creates a new board, for now it only takes a borderPane.
     * @param gameLayout the borderPane to hold the game.
     */
    public Board(BorderPane gameLayout){
        this.gameLayout = gameLayout;
        this.gameGrid = new GridPane();
        this.gameStack = new StackPane();
        gameLayout.setCenter(gameStack);
        this.border = new Rectangle();
        border.setHeight(800);
        border.setWidth(500);
        border.setFill(Color.SILVER);
        border.setStroke(Color.BLACK);
        gameStack.getChildren().add(border);
        gameStack.getChildren().add(gameGrid);
        gameGrid.setPrefWidth(500);
        gameGrid.setPrefHeight(800);
        gameGrid.setHgap(0);
        gameGrid.setVgap(0);
        gameStack.setMinSize(500,800);
        gameStack.setMaxSize(500,800);
        gameGrid.setMinSize(500,800);
        gameGrid.setMaxSize(500,800);

        fullGrid = new int[columns][rows];
        for(int c = 0; c < columns; c++ ){
            for(int r = 0; r<rows; r++){
                Rectangle fill = new Rectangle(25,20);
                fill.setFill(Color.TRANSPARENT);
                gameGrid.add(fill, c,r);
                fullGrid[c][r] = 0;
            }
        }
    }

    /**
     * Adds shape to grid, assumes it can fit.
     * @param startX Where to place the beginning x in the grid.
     * @param startY Where to place the beginning y in the grid.
     * @param shape Shape to add.
     * @return true if added, false if not.
     */
    private synchronized boolean addShape(int startX, int startY, TetrisShape shape){
        boolean couldAdd = false;
            if (shape.setX(startX) && shape.setY(startY) && canAdd(startX, startY, shape)) {
                couldAdd = true;
                for (int c = 0; c < shape.getShapeSize(); c++) {
                    for (int r = 0; r < shape.getShapeSize(); r++) {
                        int realC = startX +c;
                        int realR = startY +r;
                        int[][] shapeArray = shape.getSquareLocations();
                        if (shapeArray[c][r] != 0 && realC >= 0 && realC <columns && realR >= 0 && realR < rows){
                            fullGrid[realC][realR] = 1;
                            gameGrid.getChildren().removeIf(new Predicate<Node>() {
                                @Override
                                public boolean test(Node node) {
                                    if(GridPane.getColumnIndex(node) == realC && GridPane.getRowIndex(node) == realR){
                                        return true;
                                    }

                                    return false;
                                }
                            });
                            Rectangle newRect = new Rectangle(25, 20);
                            newRect.setFill(shape.color);
                            gameGrid.add(newRect, realC, realR);
                        }
                    }
                }
            }
        return couldAdd;
    }

    /**
     * Removes a shape from the board.
     * @param shape the shape to remove.
     */
    private synchronized void removeShape(TetrisShape shape){
        int shapeSize = shape.getShapeSize();
        int [][] shapeArray = shape.getSquareLocations();
        for(int c = 0; c<shapeSize; c++){
            for(int r = 0; r<shapeSize; r++){
                int realC = shape.x + c;
                int realR = shape.y +r;
                if (shapeArray[c][r] != 0 && realC >= 0 && realC <columns && realR >= 0 && realR < rows) {
                    fullGrid[realC][realR] = 0;
                    gameGrid.getChildren().removeIf(new Predicate<Node>() {
                        @Override
                        public boolean test(Node node) {
                            if (GridPane.getColumnIndex(node) == realC && GridPane.getRowIndex(node) == realR) {
                                return true;
                            }

                            return false;
                        }
                    });
                    Rectangle newRect = new Rectangle(25, 20);
                    newRect.setFill(Color.TRANSPARENT);
                    gameGrid.add(newRect, realC, realR);
                }
            }
        }
    }


    /**
     * Checks if a piece can be added to a position in the baord.
     * @param x
     * @param y
     * @param shape
     * @return
     */
    private synchronized boolean canAdd(int x, int y, TetrisShape shape){
        int arraySize = shape.getShapeSize();
        int [][] shapeArray = shape.getSquareLocations();
        for(int c = 0; c<arraySize; c++){
            for(int r = 0; r<arraySize; r++){
                int actualColumn = x + c;
                int actualRow = y +r;
                if(shapeArray[c][r] != 0){
                    if(actualRow >= 0 && actualRow < rows && actualColumn >= 0 && actualColumn<columns){
                        if(fullGrid[actualColumn][actualRow] != 0) return false;
                    }
                    else{
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Moves a shape to a new location with x and y offset. Assumes shape is already added.
     * @param xDir offset from original x.
     * @param yDir offset from original y.
     * @param shape
     */
    public synchronized void moveShape(int xDir, int yDir, TetrisShape shape){
        int newX = shape.x + xDir;
        int newY = shape.y + yDir;
        removeShape(shape);
        if(canAdd(newX, newY, shape) && shape.canMove){
            if(!canAdd(newX, newY+1, shape)){
                shape.canMove = false;
            }
            addShape(newX, newY, shape);
        }
        else{
            addShape(shape.x, shape.y, shape);
        }
    }

    /**
     * Rotates an  existing shape.
     * @param dir positive for right rotate and 0 or less for left rotate.
     * @param shape
     */
    public void rotateShape(int dir, TetrisShape shape){
        if(shape.canMove) {
            int[][] current = shape.getSquareLocations();
            int[][] newArray = new int[shape.getShapeSize()][shape.getShapeSize()];
            if (dir > 0) {
                for (int c = 0; c < shape.getShapeSize(); c++) {
                    for (int r = 0; r < shape.getShapeSize(); r++) {
                        newArray[c][r] = current[r][shape.getShapeSize() - 1 - c];
                    }
                }
            } else {
                for (int c = 0; c < shape.getShapeSize(); c++) {
                    for (int r = 0; r < shape.getShapeSize(); r++) {
                        newArray[c][r] = current[shape.getShapeSize() - 1 - r][c];
                    }
                }
            }
            TetrisShape rotated = new TetrisShape(shape.getShapeSize(), shape.color, newArray);
            removeShape(shape);
            if (canAdd(shape.x, shape.y, rotated)) {
                shape.setSquareLocations(newArray);
                if (!canAdd(shape.x, shape.y+1, shape)) {
                    shape.canMove = false;
                }

            }
            addShape(shape.x, shape.y, shape);
        }
    }


    /**
     * Adds a shape to the top of the board.
     * @param shape shape to add.
     * @return true if added and false if not.
     */
    public boolean addTetrisPiece(TetrisShape shape){
        boolean canAdd = false;
        if(shape.getShapeSize() < 10){
            int startX = (columns/2) - (shape.getShapeSize()/2);
            int startY = 0;
            canAdd = addShape(startX, startY, shape);
        }
        return canAdd;
    }





}
