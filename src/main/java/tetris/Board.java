// Copyright (c) 2008-2015  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.



/*

Current to do:

-revamp the hasFalling() method to check if there is an active tet rather than block

 */
package tetris;

public class Board {

    private final int rows;
    private final int columns;
    private CurrentlyFalling falling;
    private Block[][] area;
    private String representation;


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

    }

    @Override
    public String toString() {
        return representation;
    }

    protected boolean hasFalling() {
        return falling.isFalling();
    }

    protected void drop(Droppable piece) {
        if (!falling.isFalling()) {
            falling.setDroppable(piece);
            falling.setX(findMiddle(falling.getDroppable()));
            falling.setY(getStartingHeight());
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
     * Sadly, this method is kind of bogus.  I assumed that the shape creator
     * made tetromino boxes to fit the shapes exactly, or to be at least centered.
     * This is not the case.  This method needs to be modified to:
     * -determine the coordinates of the smallest box that contains the tetromino
     * -and return half that value.
     * This was discovered via a broken O-Shape.
     *
     * @param piece
     * @return
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
        System.out.println("xOffset =" + xOffset);
        if (piece.getWidth() == actualWidth) {
            return xOffset;
        } else {
            return xOffset + (piece.getWidth() - actualWidth - colStart) - 1;
        }


    }

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

    protected void tick() {
        if (CanBeDropped()) {
            DropALevel();
        } else {
            lockPieceInPlace();
        }
        redrawBoard();
    }

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
        return testAdjustment(1, 0);
    }


    protected void moveLeft() {
        if (testAdjustment(0, -1)) {
            falling.setX(falling.getX() - 1);
            redrawBoard();
        }
    }

    private boolean testAdjustment(int rowShift, int colShift) {
        return testAdjustment(falling.getDroppable(), rowShift, colShift);

    }


    private boolean testAdjustment(Droppable d, int rowShift, int colShift) {
        int fallingWidth = d.getWidth();
        int fallingHeight = d.getHeight();
        int fallingY = falling.getY();
        int fallingX = falling.getX();

        System.out.println(representation);
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
        if (testAdjustment(0, 1)) {
            falling.setX(falling.getX() + 1);
            redrawBoard();
        }
    }

    public void moveDown() {
        if (testAdjustment(1, 0)) {
            falling.setY(falling.getY() + 1);
            redrawBoard();
        } else {
            lockPieceInPlace();
        }
    }

    /**
     * Although the individual rotation methods in Piece and Tetromino classes
     * are fitting for the sub-suite-6 tests, the board rotate methods must modify the
     * X and Y coordinates of the falling Droppable.
     */
    public void rotateRight() {
        Droppable d = falling.getDroppable().rotateRight();
        if (!attemptRotation(d, 0, 0)) {
            if (!attemptRotation(d, 0, 1)) {
                attemptRotation(d, 0, -1);
            }
        }
        if (d.toString() == Tetromino.I_SHAPE.toString()) {
            attemptRotation(d, 0, 2);
        }
    }

    public void rotateLeft() {
        Droppable d = falling.getDroppable().rotateLeft();
        if (!attemptRotation(d, 0, 0)) {
            if (!attemptRotation(d, 0, 1)) {
                attemptRotation(d, 0, -1);
            }
        }
        if (d.toString() == Tetromino.I_SHAPE.toString()) {
            attemptRotation(d, 0, 2);
        }
    }

    private boolean attemptRotation(Droppable d, int yShift, int xShift) {
        int yOffset = yShift;
        int xOffset = xShift;
        //Need cases for all Tetrominos?
        if (d.toString().equals(Tetromino.I_SHAPE.toString())) {
            yOffset = -1;
        } else if (d.toString().equals(Tetromino.I_SHAPE_VERT.toString())) {
            yOffset = 1;
        } else if (d.toString().equals(Tetromino.T_SHAPE.toString())
                || d.toString().equals(Tetromino.T_SHAPE.rotateRight().rotateRight().toString())) {

        }
        if (testAdjustment(d, yOffset, xOffset)) {
            falling.setDroppable(d);
            falling.setX(falling.getX() + xOffset);
            falling.setY(falling.getY() + yOffset);
            redrawBoard();
            return true;
        } else {
            System.out.println("not a valid rotation");
            return false;
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
