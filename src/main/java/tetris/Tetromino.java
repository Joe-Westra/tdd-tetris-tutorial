// Copyright (c) 2008-2017  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

/**
 * Created by jdub on 03/03/17.
 */
public enum Tetromino {
    T_SHAPE(".T.\n" +
            "TTT\n" +
            "...\n"),
    T_R( ".T.\n" +
         ".TT\n" +
         ".T.\n"),
    T_D("...\n" +
        "TTT\n" +
        ".T.\n"),
    T_L(".T.\n" +
        "TT.\n" +
        ".T.\n"),
    I_SHAPE(".....\n" +
            ".....\n" +
            "IIII.\n" +
            ".....\n" +
            ".....\n"),
    I_V("..I..\n" +
        "..I..\n" +
        "..I..\n" +
        "..I..\n" +
        ".....\n"),
    O_SHAPE(".OO\n" +
            ".OO\n" +
            "...\n");

    private Piece shape;

    Tetromino(String s) {
        shape = new Piece(s);
    }


    public Tetromino rotateRight(){
        switch(this){
            case T_SHAPE:
                return T_R;
            case T_R:
                return T_D;
            case T_D:
                return T_L;
            case T_L:
                return T_SHAPE;
            case I_SHAPE:
                return I_V;
            case I_V:
                return I_SHAPE;
            case O_SHAPE:
                return O_SHAPE;
        }
        return null;
    }



    public Tetromino rotateLeft(){
        switch(this){
            case T_SHAPE:
                return T_L;
            case T_R:
                return T_SHAPE;
            case T_D:
                return T_R;
            case T_L:
                return T_D;
            case I_SHAPE:
                return I_V;
            case I_V:
                return I_SHAPE;
            case O_SHAPE:
                return O_SHAPE;
        }
        return null;
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

    public Piece getShape(){
        return shape;
    }
}
