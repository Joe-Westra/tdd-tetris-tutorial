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
    protected Block[][] p;


    public Piece(String str){
        rows = str.indexOf("\n");
        cols = str.split("\n").length;
        p = new Block[rows][cols];
        str = str.replaceAll("\n","");
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                p[row][col] = new Block(str.charAt(col + (row * rows)));
            }
        }
    }


    public Piece(char[][] array){
         this.cols = array[0].length;
         this.rows = array.length;
         p = new Block[rows][cols];
         for(int row = 0; row < rows; row++){
             for(int col = 0; col < cols; col++){
                 p[rows][cols] = new Block(array[rows][cols]);
             }
         }
    }

    public Piece(Block[][] temp) {
        this.cols = temp[0].length;
        this.rows = temp.length;
        p = new Block[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                p[row][col] = temp[row][col];
            }
        }
    }


    @Override
    public String toString(){
        String s = "";
        for(int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                s += p[row][col].shape;
            }
            s+="\n";
        }
        return s;
    }


    protected Piece rotateRight(){
        Block[][] temp = new Block[rows][cols];
        for(int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                temp[col][(rows-1) - row] = p[row][col];
            }
        }
        return (new Piece(temp));
    }


    protected Piece rotateLeft(){
        Block[][] temp = new Block[rows][cols];
        for(int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                temp[(cols-1)-col][row] = p[row][col];
            }
        }
        return (new Piece(temp));
    }
}
