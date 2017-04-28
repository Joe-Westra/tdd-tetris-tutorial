// Copyright (c) 2008-2015  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.



/*

Current to do:

-revamp the hasFalling() method to check if there is an active tet rather than block

 */
package tetris;

public class Board {
    private static final int DROP_BONUS = 1;
    private static final int ROW_SCORE = 20;
    private final int rows;
    private final int columns;
    private CurrentlyFalling falling;
    private Block[][] area;
    private String representation;
    private ScoreBoard scoreKeeper;
    private boolean isFirstPlacement;
    private ShuffleBag<Tetromino> shuffleBag;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        area = new Block[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                area[row][col] = new Block();
            }
        }
        falling = new CurrentlyFalling();
        redrawBoard();
        scoreKeeper = new ScoreBoard();
        shuffleBag = new ShuffleBag<>();
        loadTheBag();
    }

    private void loadTheBag() {
        for (int i = 0; i < 2; i++) {
            shuffleBag.add(Tetromino.I_SHAPE);
            shuffleBag.add(Tetromino.O_SHAPE);
            shuffleBag.add(Tetromino.T_SHAPE);
            shuffleBag.add(Tetromino.J_SHAPE);
            shuffleBag.add(Tetromino.L_SHAPE);
            shuffleBag.add(Tetromino.Z_SHAPE);
            shuffleBag.add(Tetromino.S_SHAPE);
        }
    }

    @Override
    public String toString() {
        return representation;
    }

    protected boolean hasFalling() {
        return falling.isFalling();
    }

    /**
     * Drops a new piece from the middle top of the board.
     *
     * @param piece
     */
    protected void drop(Droppable piece) throws IllegalStateException{
        if (!falling.isFalling()) {
            falling.setDroppable(piece);
            falling.setX(findMiddle(falling.getDroppable()));
            falling.setY(getStartingHeight());
            isFirstPlacement = true;
            tick();
        } else {
            throw new IllegalStateException("already falling");
        }
    }

    /**
     * Determines the highest non-empty for in the 'falling' piece
     * and returns that value - 1, so as to ensure dropping a piece
     * starts it as high up on the board as possible.
     *
     * @return int top row
     */
    private int getStartingHeight() {
        Droppable temp = falling.getDroppable();
        for (int row = 0; row < temp.getHeight(); row++) {
            for (int col = 0; col < temp.getWidth(); col++) {
                if (temp.getBlockAt(row, col).getChar() != Block.EMPTY) {
                    return (row + 1) * -1;
                }
            }
        }
        return -1;
    }

    /**
     * Determines the Actual Width of the piece (excludes empty columns from Tet)
     * and information that centers the piece on the board
     *
     * @param piece
     * @return an integer x-offset of squares from the left side of the board
     */
    private int findMiddle(Droppable piece) {
        int colStart = 0;
        int colEnd = piece.getWidth() - 1;
        boolean minFound = false;
        boolean maxFound = false;
        for (int col = 0; col < piece.getWidth(); col++) {
            for (int row = 0; row < piece.getHeight(); row++) {
                if (piece.getBlockAt(row, col).getChar() != Block.EMPTY
                        && !minFound) {
                    colStart = col;
                    minFound = true;
                }
                if (piece.getBlockAt(row, piece.getWidth() - 1 - col).getChar() != Block.EMPTY
                        && !maxFound) {
                    colEnd = piece.getWidth() - 1 - col;
                    maxFound = true;
                }
            }
            if (minFound && maxFound) {
                break;
            }
        }

        int actualWidth = (colEnd - colStart + 1);
        int xOffset = (int) Math.ceil((columns - actualWidth) / 2.0);

        return xOffset - colStart;
    }

    /**
     * Calculates the a string representation of the boards contents from the permanent
     * board data, and inserts the currently falling piece.
     */
    private void redrawBoard() {
        representation = "";

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (containsPiece(row, col)) {
                    int fallY = falling.getY();
                    int fallX = falling.getX();
                    representation +=
                            (falling.getDroppable().getBlockAt(row - fallY, col - fallX).getChar() != Block.EMPTY)
                                    ? falling.getDroppable().getBlockAt(row - fallY, col - fallX).getChar()
                                    : area[row][col].getChar();
                } else {
                    representation += area[row][col].getChar();
                }
            }
            representation += "\n";
        }
    }

    /**
     * The piece drops or locks, as appropriate.
     */
    protected void tick() {
        if (CanBeDropped()) {
            DropALevel();
            isFirstPlacement = false;
        } else if (isFirstPlacement) {
            throw new IllegalStateException("Game Over");
        } else {
            lockPieceInPlace();
            checkForFullRows();
        }
        redrawBoard();
    }

    /**
     * Iterates over each row in the board, clears each full one and increases the
     * score by one.
     */
    private void checkForFullRows() {
        int scoreAdjustment = 0;
        for (int row = 0; row < rows; row++) {
            int nonEmpty = 0;
            for (int col = 0; col < columns; col++) {
                if (area[row][col].getChar() != Block.EMPTY) {
                    nonEmpty++;
                }
            }
            if (nonEmpty == columns) {
                scoreAdjustment++;
                dropAllRowsAbove(row);
            }
        }
        scoreKeeper.increaseScore(ROW_SCORE * scoreAdjustment);
    }


    /**
     * Clears the row and drops all above rows down one.
     *
     * @param rowToBeCleared
     */
    private void dropAllRowsAbove(int rowToBeCleared) {
        for (int row = rowToBeCleared; row >= 0; row--) {
            for (int col = 0; col < columns; col++) {
                area[row][col] = row > 0
                        ? area[row - 1][col]
                        : new Block();
            }
        }
    }

    /**
     * Used by the redrawBoard method to determine when to insert the falling pieces
     * data instead of the Boards data.
     *
     * @param x the x location on the board
     * @param y the y location on the board
     * @return whether the currently falling piece is at this location on the board.
     */
    private boolean containsPiece(int x, int y) {
        if (falling.getDroppable() == null) {
            return false;
        }
        int fallY = falling.getY();
        int fallX = falling.getX();
        if (x >= +fallY && x < fallY + falling.getDroppable().getHeight()
                && y >= +fallX && y < fallX + falling.getDroppable().getWidth()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Adds the falling piece to the boards array and nullifies the currentlyFalling's data
     */
    private void lockPieceInPlace() {
        Droppable d = falling.getDroppable();
        int x = falling.getX();
        int y = falling.getY();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (row >= +y
                        && row < y + d.getHeight()
                        && col >= +x
                        && col < x + d.getWidth()) {
                    if (d.getBlockAt(row - y, col - x).getChar() != Block.EMPTY) {
                        area[row][col] = d.getBlockAt(row - y, col - x);
                    }
                }

            }
        }
        falling.stopDropping();
    }

    private void DropALevel() {
        falling.setY(falling.getY() + 1);
    }

    private boolean CanBeDropped() {
        return adjustmentIsLegal(1, 0);
    }


    protected void moveLeft() {
        if (hasFalling() && adjustmentIsLegal(0, -1)) {
            falling.setX(falling.getX() - 1);
            redrawBoard();
        }
    }

    private boolean adjustmentIsLegal(int rowShift, int colShift) {
        return adjustmentIsLegal(falling.getDroppable(), rowShift, colShift);
    }


    /**
     * This method is being too multipurposed at the moment.  It checks for the legality
     * of a suggested placement of a piece.  It is being used by movements AND rotations, but
     * rotations should probably have their own logic once the finalized rotation rules
     * are implemented.  For now it does the job.
     *
     * @param d        piece to test
     * @param rowShift tentative Y increment from where d is currently located
     * @param colShift tentative X increment from where d is currently located
     * @return whether the move is legal or not
     */
    private boolean adjustmentIsLegal(Droppable d, int rowShift, int colShift) {
        int fallingWidth = d.getWidth();
        int fallingHeight = d.getHeight();
        int fallingY = falling.getY();
        int fallingX = falling.getX();

        if (fallingY + rowShift > rows) { //guards against NPE when trying to access the board array
            return false;
        }

        for (int y = 0; y < fallingHeight; y++) {
            for (int x = 0; x < fallingWidth; x++) {
                if (y + fallingY + rowShift >= 0
                        && d.getBlockAt(y, x).getChar() != Block.EMPTY) {
                    if (y + fallingY + rowShift >= rows
                            || x + fallingX + colShift >= columns
                            || x + fallingX + colShift < 0) {
                        return false;
                    } else if (area[y + fallingY + rowShift][x + fallingX + colShift].getChar() != Block.EMPTY) {
                        return false;
                    }
                } else if (fallingY < 0 && d.getBlockAt(y, x).getChar() != Block.EMPTY) //if piece runs off the top of the board
                {
                    return false;
                }

            }
        }
        return true;
    }


    public void moveRight() {
        if (hasFalling() && adjustmentIsLegal(0, 1)) {
            falling.setX(falling.getX() + 1);
            redrawBoard();
        }
    }

    public void moveDown() {
        if (adjustmentIsLegal(1, 0)) {
            falling.setY(falling.getY() + 1);
            redrawBoard();
        } else {
            lockPieceInPlace();
        }
    }


    public void rotateRight() {
        if (hasFalling()) {
            Droppable d = falling.getDroppable().rotateRight();
            loopThroughRotationAttempts(d);
        }
    }

    public void rotateLeft() {
        if (hasFalling()) {
            Droppable d = falling.getDroppable().rotateLeft();
            loopThroughRotationAttempts(d);
        }
    }

    /**
     * Since there are multiple legal rotation outputs given different situations,
     * this method loops through the three standard attempts, and one special case
     * for horizontal I tetrominos
     *
     * @param d piece to iterate rotations over
     */
    private void loopThroughRotationAttempts(Droppable d) {
        if (!attemptRotation(d, 0, 0)) {
            if (!attemptRotation(d, 0, 1)) {
                if (!attemptRotation(d, 0, -1)
                        && d.equals(Tetromino.I_SHAPE)) {
                    attemptRotation(d, 0, 2);
                }
            }
        }
    }


    /**
     * Although the individual rotation methods in Piece and Tetromino classes
     * are fitting for the sub-suite-6 tests, the board rotate methods must modify the
     * X and Y coordinates of the falling Droppable.
     * NOTICE: if attempt is successful, the rotation is made, then true is returned.
     *
     * @param d      piece to rotate
     * @param yShift tentative Y increment from where d is currently located
     * @param xShift tentative X increment from where d is currently located
     * @return whether the rotation is legal, if so the rotation is enacted
     */
    private boolean attemptRotation(Droppable d, int yShift, int xShift) {
        int yOffset = yShift;
        int xOffset = xShift;
        if (d.toString().equals(Tetromino.I_SHAPE.toString())) {
            yOffset = -1;
        } else if (d.toString().equals(Tetromino.I_SHAPE_VERT.toString())) {
            yOffset = 1;
        }
        if (adjustmentIsLegal(d, yOffset, xOffset)) {
            falling.setDroppable(d);
            falling.setX(falling.getX() + xOffset);
            falling.setY(falling.getY() + yOffset);
            redrawBoard();
            return true;
        } else {
            return false;
        }
    }

    public void addScoreBoard(ScoreBoard s) {
        scoreKeeper = s;
    }

    public int getScore() {
        return scoreKeeper.getScore();
    }

    public void dropToBottom() {
        while (falling.isFalling()) {
            scoreKeeper.increaseScore(DROP_BONUS);
            tick();
        }
    }

    public Tetromino chooseRandomTetromino() {
        return shuffleBag.getNext();
    }

    public void doTurn() throws IllegalStateException{
        if (falling.isFalling()) {
            tick();
        } else {
            try{
                this.drop(chooseRandomTetromino());
            } catch (IllegalStateException e){
                System.out.println(e.getMessage());
            }
        }
    }

}


/**
 * This is to keep track of the currently falling Droppable
 */
class CurrentlyFalling {
    private int x;
    private int y;
    private Droppable falling;
    private boolean isFalling = false;


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Droppable getDroppable() {
        return falling;
    }

    public void setDroppable(Droppable block) {
        this.falling = block;
        isFalling = true;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void stopDropping() {
        isFalling = false;
        x = 0;
        y = 0;
        falling = null;
    }
}
