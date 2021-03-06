// Copyright (c) 2008-2017  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;


/**
 * Created by jdub on 02/03/17.
 */
public class Piece implements Droppable {
    private int cols;
    private int rows;
    protected Block[][] p;


    public Piece(String str) {
        cols = str.indexOf("\n");
        rows = str.split("\n").length;
        p = new Block[rows][cols];
        str = str.replaceAll("\n", "");
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                p[row][col] = new Block(str.charAt(col + (row * cols)));
            }
        }
    }


    public Piece(Block[][] temp) {
        this.rows = temp[0].length;
        this.cols = temp.length;
        p = new Block[cols][rows];
        for (int row = 0; row < cols; row++) {
            System.arraycopy(temp[row],0,p[row],0,temp[row].length);
        }
    }


    public Piece rotateRight() {
        Block[][] temp = new Block[cols][rows];
        for (int row = 0; row < cols; row++) {
            for (int col = 0; col < rows; col++) {
                temp[col][(cols - 1) - row] = p[row][col];
            }
        }
        return (new Piece(temp));
    }


    public Piece rotateLeft() {
        Block[][] temp = new Block[cols][rows];
        for (int row = 0; row < cols; row++) {
            for (int col = 0; col < rows; col++) {
                temp[(rows - 1) - col][row] = p[row][col];
            }
        }
        return (new Piece(temp));
    }


    @Override
    public String toString() {
        String s = "";
        for (int row = 0; row < cols; row++) {
            for (int col = 0; col < rows; col++) {
                s += p[row][col].getChar();
            }
            s += "\n";
        }
        return s;
    }

    @Override
    public int getWidth() {
        return cols;
    }

    @Override
    public int getHeight() {
        return rows;
    }

    @Override
    public Block getBlockAt(int row, int column) {
        return p[row][column];
    }
}
