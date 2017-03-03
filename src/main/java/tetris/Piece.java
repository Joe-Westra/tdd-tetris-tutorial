// Copyright (c) 2008-2017  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

/**
 * Created by jdub on 02/03/17.
 *
 * Fiercely loyal to 2D arrays at this point.
 */
public class Piece {
    private int rows;
    private int cols;
    private char[][] p;

    public Piece(String str){
        rows = str.indexOf("\n");
        cols = str.split("\n").length;
        p = new char[rows][cols];
        str = str.replaceAll("\n","");
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                p[row][col] = str.charAt(col + (row * rows));
            }
        }
    }

    public Piece(char[][] array){
         this.p=array.clone();
         this.cols = p[0].length;
         this.rows = p.length;
    }

    @Override
    public String toString(){
        String s = "";
        for(int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                s += p[row][col];
            }
            s+="\n";
        }
        return s;
    }

    protected Piece rotateRight(){
        char[][] temp = new char[rows][cols];

        for(int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                temp[col][(rows-1) - row] = p[row][col];
            }
        }

        return (new Piece(temp));
    }

    protected Piece rotateLeft(){
        char[][] temp = new char[rows][cols];

        for(int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                temp[(cols-1)-col][row] = p[row][col];
            }
        }
        return (new Piece(temp));
    }

}
