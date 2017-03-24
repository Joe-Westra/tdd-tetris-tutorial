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
        //needs to be dynamic
/*
        representation =  "...\n" +
                          "...\n" +
                          "...\n";
*/
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
            falling.setY(-1);
            tick();
        }else
            throw new IllegalStateException("already falling");
    }

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
            LockPieceinPlace();
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

    private void LockPieceinPlace() {
        for (int row = 0; row < rows; row++) {
            for (int y = 0; y < columns; y++) {
                if (row >= + falling.getY() && row < falling.getY() + falling.getDroppable().getHeight()
                        && y >= + falling.getX() && y < falling.getX() + falling.getDroppable().getWidth()){
                        if (falling.getDroppable().getBlockAt(row-falling.getX(),y-falling.getY()).getChar() != Block.EMPTY)
                            area[row][y]=falling.getDroppable().getBlockAt(row-falling.getX(),y-falling.getY());
                }

            }
        }
        falling.stopDropping();
    }

    private void DropALevel() {
        falling.setY(falling.getY() + 1);
    }

    private boolean CanBeDropped() {
        final int INCREMENT = 1;
        int fallingWidth = falling.getDroppable().getWidth();
        int fallingHeight = falling.getDroppable().getHeight();
        int fallingY = falling.getY() + INCREMENT;
        int fallingX = falling.getX();

        if (fallingY >= columns)
            return false;
        for (int cols = 0; cols < fallingHeight; cols++) {
            for (int rows = 0; rows < fallingWidth; rows++) {
                if(area[cols + fallingY][rows + fallingX].getChar() != Block.EMPTY
                        && falling.getDroppable().getBlockAt(cols,rows).getChar() != Block.EMPTY)
                    return false;
            }
        }
        return true;
    }


}



/*This is to keep track of the currently falling Droppable*/
class CurrentlyFalling{
    private int x;
    private int y;
    private Droppable falling;
    private Block[][] area;
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
