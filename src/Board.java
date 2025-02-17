import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * This class handles most of the board related functions such as display. Also moving shapes.
 *
 * @author Danny Metcalfe
 */
public class Board {
    private final GridPane gameGrid;

    private final BorderPane borderPane;

    private final int[][] fullGrid;
    private final int columns = 20;
    private final int rows = 40;

    private Opponent opponent1 = new Opponent();
    private Opponent opponent2 = new Opponent();
    private Opponent opponent3 = new Opponent();
    private Opponent opponent4 = new Opponent();

    private ArrayList<Opponent> opponents = new ArrayList<>();

    private int numPlayers = 1;

    private int ends = 0;

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getEnds() {
        return ends;
    }

    public void setEnds(int ends){
        this.ends = ends;
    }




    /**
     * Class for handling individual Tetris shapes.
     */
    public static class TetrisShape{
        private int x = 0;
        private int y = 0;
        private int[][] squareLocations;
        private final int shapeSize;
        private final Color color;
        private boolean canMove = true;

        private final int colIndex;

        /**
         * Creates a new shape.
         * @param size size of the shape of the longest part. ex 1 by 4 rectangle size should be 4.
         * @param color color of the shape.
         * @param squares a grid that represents the shape. Where numbers are positions of the squares.
         */
        public TetrisShape(int size, Color color, int[][] squares, int index){
            colIndex = index;
            shapeSize = size;
            this.color = color;
            if(size > 0 && size == squares.length && size == squares[0].length){
                squareLocations = new int[size][size];
                for(int c = 0; c<size; c++){
                    for(int r = 0; r<size; r++){
                        if(squares[c][r] != -1){
                            squareLocations[c][r] = index;
                        }
                        else squareLocations[c][r] = -1;
                    }
                }

            }
        }

        /**
         * Sets the x value of the shape, returns true if properly set and false if not.
         * @param x to set, shape x positions.
         * @return returns true if set and false if not.
         */
        private boolean setX(int x){
            this.x = x;
            return true;

        }

        /**
         * Sets the y value of the shape. Returns true if it was set and false if it was not.
         * @param y the y value of the shape location.
         * @return Returns true if that value was set and false if it was not.
         */
        private boolean setY(int y){
            this.y = y;
            return true;
        }

        private int getColIndex(){
            return this.colIndex;
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

        /**
         * Sets the shape array.
         * @param newArray the array representation of the block shape.
         */
        private void setSquareLocations(int [][] newArray){
            this.squareLocations = newArray;
        }

        /**
         * Checks if the shape can be moved, if it cannot move down then it cannot move.
         * @return True if shape can be moved and false if not.
         */
        public boolean isCanMove() {
            return canMove;
        }

        /**
         * Gets the x location value from the shape.
         * @return x location of initial index.
         */
        public int getX(){
            return this.x;
        }

        /**
         * Gets the y position of the first shape index.
         * @return y location of initial grid index.
         */
        public int getY(){
            return this.y;
        }
    }

    /**
     * Creates a new board, for now it only takes a borderPane.
     * @param gameLayout the borderPane to hold the game.
     */
    public Board(BorderPane gameLayout){
        this.gameGrid = new GridPane();
        StackPane gameStack = new StackPane();
        gameLayout.setCenter(gameStack);
        Rectangle border = new Rectangle();
        border.setHeight(800);
        border.setWidth(500);
        border.setFill(Color.BLACK);
        this.borderPane = gameLayout;

        // Set the stroke (border) color and width
        border.setStroke(Color.GREY); // You can change the color if needed
        border.setStrokeWidth(5); // Set the border width as per your preference

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

        HBox topBox = new HBox();
        VBox opponent1and3 = new VBox();
        VBox opponent2and4 = new VBox();
        opponent1 = new Opponent();
        opponent2 = new Opponent();
        opponent3 = new Opponent();
        opponent4 = new Opponent();
        opponents.add(opponent1);
        opponents.add(opponent2);
        opponents.add(opponent3);
        opponents.add(opponent4);

        opponent1and3.setSpacing(10);
        opponent2and4.setSpacing(10);
        opponent1and3.getChildren().add(opponent1.getPlayerHBox());
        opponent2and4.getChildren().add(opponent2.getPlayerHBox());
        opponent1and3.getChildren().add(opponent3.getPlayerHBox());
        opponent2and4.getChildren().add(opponent4.getPlayerHBox());


        topBox.getChildren().add(opponent1and3);
        topBox.getChildren().add(opponent2and4);
        topBox.setAlignment(Pos.TOP_LEFT);

        gameLayout.setLeft(topBox);

        for(int c = 0; c < columns; c++ ){
            for(int r = 0; r<rows; r++){
                Rectangle fill = new Rectangle(25,20);
                fill.setFill(Color.TRANSPARENT);
                gameGrid.add(fill, c,r);
                fullGrid[c][r] = -1;
            }
        }
    }

    /**
     * Adds shape to grid, assumes it can fit within the game grid.
     * @param startX Where to place the beginning x in the grid.
     * @param startY Where to place the beginning y in the grid.
     * @param shape Shape to add.
     * @return true if added, false if not.
     */
    private boolean addShape(int startX, int startY, TetrisShape shape){
        boolean couldAdd = false;
        if (shape.setX(startX) && shape.setY(startY) && canAdd(startX, startY, shape)) {
            couldAdd = true;
            for (int c = 0; c < shape.getShapeSize(); c++) {
                for (int r = 0; r < shape.getShapeSize(); r++) {
                    int realC = startX +c;
                    int realR = startY +r;
                    int[][] shapeArray = shape.getSquareLocations();
                    if (shapeArray[c][r] != -1 && realC >= 0 && realC <columns && realR >= 0 && realR < rows){
                        fullGrid[realC][realR] = shapeArray[c][r];
                        gameGrid.getChildren().removeIf(new Predicate<Node>() {
                            @Override
                            public boolean test(Node node) {
                                return GridPane.getColumnIndex(node) == realC &&
                                        GridPane.getRowIndex(node) == realR;
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
    private void removeShape(TetrisShape shape){
        int shapeSize = shape.getShapeSize();
        int [][] shapeArray = shape.getSquareLocations();
        for(int c = 0; c<shapeSize; c++){
            for(int r = 0; r<shapeSize; r++){
                int realC = shape.x + c;
                int realR = shape.y +r;
                if (shapeArray[c][r] != -1 && realC >= 0 && realC <columns && realR >= 0 && realR < rows) {
                    fullGrid[realC][realR] = -1;
                    gameGrid.getChildren().removeIf(new Predicate<Node>() {
                        @Override
                        public boolean test(Node node) {
                            return GridPane.getColumnIndex(node) == realC && GridPane.getRowIndex(node) == realR;
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
     * @param x column to place the top left of shape grid in full grid.
     * @param y row to place the top left of shape grid into full grid.
     * @param shape shape to add.
     * @return returns true if added and false if not.
     */
    private boolean canAdd(int x, int y, TetrisShape shape){
        int arraySize = shape.getShapeSize();
        int [][] shapeArray = shape.getSquareLocations();
        for(int c = 0; c<arraySize; c++){
            for(int r = 0; r<arraySize; r++){
                int actualColumn = x + c;
                int actualRow = y +r;
                if(shapeArray[c][r] != -1){
                    if(actualRow >= 0 && actualRow < rows && actualColumn >= 0 && actualColumn<columns){
                        if(fullGrid[actualColumn][actualRow] != -1) return false;
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
     * @param shape shape to move.
     */
    public void moveShape(int xDir, int yDir, TetrisShape shape){
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
     * @param shape shape to rotate.
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
            TetrisShape rotated = new TetrisShape(shape.getShapeSize(), shape.color, newArray, shape.getColIndex());
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
        if(shape != null && shape.getShapeSize() < 10){
            int startX = (columns/2) - (shape.getShapeSize()/2);
            int startY = 0;
            canAdd = addShape(startX, startY, shape);
            if(canAdd){
                removeShape(shape);
                if(!canAdd(startX, startY+1, shape)){
                    shape.canMove = false;
                }
                addShape(startX, startY, shape);
            }
        }
        return canAdd;
    }

    /**
     * Deletes a row from the board. Assumes valid row index is given.
     *
     * @param row row to delete.
     */
    public void deleteRow(int row){
        for(int r = row; r>0; r--){
            for(int c = 0; c<columns; c++){
                fullGrid[c][r] = fullGrid[c][r-1];

                if(r==row){
                    for(int i = 0; i<gameGrid.getChildren().size(); i++){
                        Node current = gameGrid.getChildren().get(i);
                        if(GridPane.getColumnIndex(current) == c && GridPane.getRowIndex(current) == row){
                            gameGrid.getChildren().remove(current);
                            i--;
                        }
                    }
                }
                for (int i = 0; i < gameGrid.getChildren().size(); i++) {
                    Node current = gameGrid.getChildren().get(i);
                    if (GridPane.getColumnIndex(current) == c && GridPane.getRowIndex(current) == r - 1) {
                        gameGrid.getChildren().remove(current);
                        gameGrid.add(current, c, r);
                        i--;
                    }
                }



            }
        }
        for(int c= 0; c<columns; c++){
            Rectangle rect = new Rectangle(25, 20);
            rect.setFill(Color.TRANSPARENT);
            gameGrid.add(rect, c, 0);
            fullGrid[c][0] = -1;

        }
    }

    /**
     * Finds out if a row in the grid is full of squares. Assumes row is valid.
     * @param row what row to check
     * @return returns true if the row is full and false if not.
     */
    public boolean fullRow(int row){
        boolean rowFull = true;
        for(int c = 0; c<columns; c++){
            if(fullGrid[c][row] == -1){
                rowFull = false;
                break;
            }
        }
        return rowFull;
    }

    /**
     * Gets rows.
     * @return returns the number of rows in the board.
     */
    public int getRows(){
        return rows;
    }

    /**
     * Gets the number of columns.
     * @return returns the number of columns in the grid.
     */
    public int getColumns(){
        return columns;
    }


    /**
     * This to string has the grid from top to bottom matching the UI representation.
     * @return Returns the string of the board.
     */
    @Override
    public String toString() {
        String fullString = "";
        for(int r = 0; r<rows; r++){
            for(int c = 0; c<columns; c++){
                fullString = fullString + fullGrid[c][r];
            }
        }
        fullString+='\n';
        return fullString;
    }

    /**
     * This returns a copy of the grid representation where -1 is empty space.
     * any other number is the index of the color in the games color array.
     * @return Returns a string representing the board.
     */
    public int[][] getFullGrid(){
        int[][] copy = new int[rows][columns];
        for(int r = 0; r< rows; r++){
            for(int c = 0; c<columns; c++){
                copy[r][c] = fullGrid[c][r];
            }
        }
        return copy;
    }

    /**
     * Sets the board of another player.
     * @param user user to set the board.
     * @param boardString The board as a string.
     */
    public void setPlayer(String user, String boardString){
        for(int x = 0; x< opponents.size(); x++) {
            if(opponents.get(x).getName().equals(user) || opponents.get(x).getName().equals("Default")) {
                opponents.get(x).setBoardMatrix(boardString);
                if(opponents.get(x).getName().equals("Default")) {
                    opponents.get(x).setName(user);
                    numPlayers++;
                }
                int finalX = x;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        opponents.get(finalX).setUIElement();
                    }
                });
                break;
            }
        }
    }

    /**
     * Sets the score information for a specific player for end game results.
     * @param user User to set the scoring to.
     * @param scoreInfo Information of their score of the form "ScorexLevelxClears"
     */
    public void setScore(String user, String scoreInfo){
        for (Opponent value : opponents) {
            if (value.getName().equals(user)) {
                String[] info = scoreInfo.split("x");
                value.setOpScore(Long.parseLong(info[0]));
                value.setOpLevel(Integer.parseInt(info[1]));
                value.setOpClears(Integer.parseInt(info[2]));
                ends++;
                break;
            }
        }
    }


    /**
     * Displays the end game results after clearing the screen.
     */
    public void displayScores(ScoreHandler scoreHandler){
        borderPane.getChildren().clear();
        HBox mainBox = new HBox();
        mainBox.setTranslateX(100);
        mainBox.setTranslateY(100);
        mainBox.setSpacing(100);
        borderPane.setLeft(mainBox);
        VBox names = new VBox();
        VBox scores = new VBox();
        VBox levels = new VBox();
        VBox lineClears  = new VBox();

        names.setSpacing(40);
        scores.setSpacing(40);
        levels.setSpacing(40);
        lineClears.setSpacing(40);

        mainBox.getChildren().add(names);
        mainBox.getChildren().add(scores);
        mainBox.getChildren().add(levels);
        mainBox.getChildren().add(lineClears);
        names.getChildren().add(new Label("Names: "));
        scores.getChildren().add(new Label("Scores: "));
        levels.getChildren().add(new Label("Levels: "));
        lineClears.getChildren().add(new Label("Line Clears: "));
        names.getChildren().add(new Label("You"));
        scores.getChildren().add(new Label(Long.toString(scoreHandler.getCurrentScore())));
        levels.getChildren().add(new Label(Integer.toString(scoreHandler.getCurrentLevel())));
        lineClears.getChildren().add(new Label(Integer.toString(scoreHandler.getLineClears())));
        for (Opponent opponent : opponents) {
            if (!opponent.getName().equals("Default")) {
                names.getChildren().add(new Label(opponent.getName()));
                scores.getChildren().add(new Label(Long.toString(opponent.getOpScore())));
                levels.getChildren().add(new Label(Integer.toString(opponent.getOpLevel())));
                lineClears.getChildren().add(new Label(Integer.toString(opponent.getOpClears())));
            }
        }

    }




}
