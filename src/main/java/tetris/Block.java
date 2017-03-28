// Copyright (c) 2008-2017  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

/**
 * Created by jdub on 01/03/17.
 */
public class Block implements Droppable{
    public static final char EMPTY = '.';

    public void setShape(char shape) {
        this.shape = shape;
    }

    private char shape;



    Block(){
        this(EMPTY);

    }

    Block(char type){
        shape = type;
    }


    @Override
    public int getWidth() {
        return 1;
    }



    @Override
    public int getHeight() {
        return 1;
    }




    @Override
    public Block getBlockAt(int x, int y) {
        return new Block(shape);
    }

    public char getChar() {
        return shape;
    }


}
