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
            LockPieceInPlace();
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

    private void LockPieceInPlace() {
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
        /*
        THIS APPROACH DOESN'T WORK, need to check each square individually.
        determine pieces actual bottom (AB)
        check to see if AB +1 is greater than the board height
        check if AB+1's height encounters a block on the board.



        check to see if AB +1 is greater than the board height
        iterate over falling's block array
            for each non empty block, check to see if the block below is non empty



         */
        final int INCREMENT = 1;
        int fallingWidth = falling.getDroppable().getWidth();
        int fallingHeight = falling.getDroppable().getHeight();
        int fallingY = falling.getY();
        int fallingX = falling.getX();

        System.out.println(representation);
        if (fallingY + INCREMENT > rows)
            return false;

        for (int y = 0; y < fallingHeight; y++) {
            for (int x = 0; x < fallingWidth; x++) {
                if(falling.getDroppable().getBlockAt(y,x).getChar() != Block.EMPTY){
                    if(y + fallingY  + INCREMENT >= rows)
                        return false;
                    else if(area[y + fallingY  + INCREMENT][x + fallingX].getChar() != Block.EMPTY)
                        return false;
                }

            }
        }



        /*for (int cols = 0; cols < fallingHeight; cols++) {
            for (int rows = 0; rows < fallingWidth; rows++) {
                System.out.printf("attempting to access %d, %d\n",cols + fallingY,rows + fallingX);
                if (fallingY + cols >= this.rows)
                    return false;
                if(falling.getDroppable().getBlockAt(cols,rows).getChar() != Block.EMPTY
                        && area[cols + fallingY][rows + fallingX].getChar() != Block.EMPTY)
                    return false;

            }


        }*/
        return true;
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
