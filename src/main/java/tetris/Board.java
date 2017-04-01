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

    protected boolean hasFalling(){
        return falling.isFalling();
    }

    protected void drop(Droppable piece){
        if(! falling.isFalling()) {
            falling.setDroppable(piece);
            falling.setX(findMiddle(falling.getDroppable()));
            falling.setY(getStartingHeight());
            tick();
        }else
            throw new IllegalStateException("already falling");
    }

    /**
     * Determines the highest non-empty for in the 'falling' piece
     * and returns that value - 1, so as to ensure dropping a piece
     * starts it as high up on the board as possible.
     * @return int top row
     */
    private int getStartingHeight() {
        Droppable temp = falling.getDroppable();
        for (int row = 0; row < temp.getHeight(); row++) {
            for (int col = 0; col < temp.getWidth(); col++) {
                if(temp.getBlockAt(row,col).getChar() != Block.EMPTY)
                    return (row +1) * -1;
            }
        }
        return -1;
    }

    /**
     * Sadly, this method is kind of bogus.  I assumed that the shape creator
     * made tetromino boxes to fit the shapes exactly, or to be at least centered.
     * This is not the case.  This method needs to be modified to:
     *  -determine the coordinates of the smallest box that contains the tetromino
     *  -and return half that value.
     *  This was discovered via a broken O-Shape.
     * @param piece
     * @return
     */
    private int findMiddle(Droppable piece){
        int fallingWidth = piece.getWidth();
        return (columns - fallingWidth) % 2 == 0
                ? (columns - fallingWidth) / 2
                : (columns - fallingWidth) / 2 + 1;
    }

    private void redrawBoard() {
        representation = "";

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (containsPiece(row, col)){
                    int fallY = falling.getY();
                    int fallX = falling.getX();
                    representation +=
                            (falling.getDroppable().getBlockAt(row - fallY,col - fallX).getChar() != Block.EMPTY)
                            ? falling.getDroppable().getBlockAt(row - fallY,col - fallX).getChar()
                            : area[row][col].getChar();
                } else
                    representation += area[row][col].getChar();
            }
            representation += "\n";
        }
    }

    protected void tick(){
        if(CanBeDropped())
            DropALevel();
        else {
            lockPieceInPlace();
        }
        redrawBoard();
    }

    private boolean containsPiece(int x, int y){
        if(falling.getDroppable() == null)
            return false;

        int fallY = falling.getY();
        int fallX = falling.getX();
        if ( x >= + fallY && x < fallY + falling.getDroppable().getHeight()
                    && y >= +fallX && y < fallX + falling.getDroppable().getWidth()){
            return true;
        } else
            return false;
    }

    private void lockPieceInPlace() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (row >= + falling.getY() && row < falling.getY() + falling.getDroppable().getHeight()
                        && col >= + falling.getX() && col < falling.getX() + falling.getDroppable().getWidth()){
                        if (falling.getDroppable().getBlockAt(row-falling.getY(),col-falling.getX()).getChar() != Block.EMPTY)
                            area[row][col]=falling.getDroppable().getBlockAt(row-falling.getY(),col-falling.getX());
                }

            }
        }
        falling.stopDropping();
    }

    private void DropALevel() {
        falling.setY(falling.getY() + 1);
    }

    private boolean CanBeDropped() {
        return testAdjustment(1,0);
    }


    public void moveLeft() {
        if(testAdjustment(0,-1)) {
            falling.setX(falling.getX() - 1);
            redrawBoard();
        }
    }
    public boolean testAdjustment(int rowShift, int colShift){

        int fallingWidth = falling.getDroppable().getWidth();
        int fallingHeight = falling.getDroppable().getHeight();
        int fallingY = falling.getY();
        int fallingX = falling.getX();

        System.out.println(representation);
        if (fallingY + rowShift > rows)
            return false;

        for (int y = 0; y < fallingHeight; y++) {
            for (int x = 0; x < fallingWidth; x++) {
                if(y + fallingY + rowShift >= 0
                    && falling.getDroppable().getBlockAt(y,x).getChar() != Block.EMPTY){
                    if(y + fallingY  + rowShift >= rows
                            || x + fallingX + colShift >= columns
                            || x + fallingX + colShift < 0)
                        return false;
                    else if(area[y + fallingY  + rowShift][x + fallingX +colShift].getChar() != Block.EMPTY)
                        return false;
                }

            }
        }
        return true;
    }


    public void moveRight() {
        if(testAdjustment(0,1)) {
            falling.setX(falling.getX() + 1);
            redrawBoard();
        }
    }

    public void moveDown() {
        if(testAdjustment(1,0)) {
            falling.setY(falling.getY() + 1);
            redrawBoard();
        }else
            lockPieceInPlace();
    }
}



/*This is to keep track of the currently falling Droppable*/
class CurrentlyFalling{
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

    public boolean isFalling(){
        return isFalling;
    }

    public void stopDropping(){
        isFalling = false;
        x = 0;
        y = 0;
        falling = null;
    }
}
