// Copyright (c) 2008-2017  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

/**
 * Created by jdub on 03/03/17.
 */
public class Tetromino implements Droppable{

    public static final Tetromino T_SHAPE =
            new Tetromino(".T.\n" +
                             "TTT\n" +
                             "...\n");

    public static final Tetromino I_SHAPE =
            new Tetromino(".....\n" +
                             ".....\n" +
                             "IIII.\n" +
                             ".....\n" +
                             ".....\n");

    public static final Tetromino O_SHAPE =
            new Tetromino(".OO\n" +
                             ".OO\n" +
                             "...\n");

    public static final Tetromino I_SHAPE_VERT =
            new Tetromino(   "..I..\n" +
                                "..I..\n" +
                                "..I..\n" +
                                "..I..\n" +
                                ".....\n");

    private Piece shape;


    Tetromino(String s) {
        shape = new Piece(s);
    }


    public Tetromino rotateRight(){
        if(this == O_SHAPE)
            return O_SHAPE;
        else if(this == I_SHAPE)
            return I_SHAPE_VERT;
        else if(this == I_SHAPE_VERT)
            return I_SHAPE;
        else
            return new Tetromino(shape.rotateRight().toString());
    }



    public Tetromino rotateLeft(){
        if(this == O_SHAPE)
            return O_SHAPE;
        else if(this == I_SHAPE)
            return I_SHAPE_VERT;
        else if(this == I_SHAPE_VERT)
            return I_SHAPE;
        else
            return new Tetromino(shape.rotateLeft().toString());
    }

    @Override
    public String toString(){
        return shape.toString();
    }



    public int getWidth(){
        return shape.p[0].length;
    }


    public int getHeight(){
        return shape.p.length;
    }


    @Override
    public Block getBlockAt(int row, int column) {
        return shape.getBlockAt(row,column);
    }

    public Piece getShape(){
        return shape;
    }


}
