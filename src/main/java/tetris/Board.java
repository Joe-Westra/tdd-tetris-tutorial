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
    private Block falling;
    private char[][] area;


    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        area = new char[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                area[row][col] = Block.EMPTY;
            }
        }
    }


    @Override
    public String toString() {
        String s = "";
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                s += area[row][col];
            }
            s += "\n";
        }
        return s;
    }


    protected boolean hasFalling(){
        return falling != null;
    }


    protected void drop(Block b){
        if(falling == null) {
            falling = b;
            falling.row = 0;
            falling.col = columns / 2;
            area[falling.row][falling.col] = falling.shape;
        }else
            throw new IllegalStateException("already falling");
    }


    protected void drop(Tetromino tet){
        int tetWidth = tet.getWidth();
        int tetHeight = tet.getHeight();
        int colOffset = (columns - tetWidth) % 2 == 0
                        ? (columns - tetWidth) / 2
                        : (columns - tetWidth) / 2 +1;
        for (int row = 0; row < tetHeight; row++) {
            for (int col = 0; col < tetWidth; col++) {
                if(area[row][col + colOffset] == '.'){
                    area[row][col + colOffset] = tet.getShape().p[row][col].shape;
                }

            }
        }
    }


    protected void tick(){
        if(falling.row < rows -1 && area[falling.row+1][falling.col] == '.') {
            area[falling.row][falling.col] = '.';
            falling.row++;
            area[falling.row][falling.col] = falling.shape;
        }else
            falling = null;
    }















}


