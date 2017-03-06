// Copyright (c) 2008-2017  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

/**
 * Created by jdub on 01/03/17.
 */
public class Block {
    public static final char EMPTY = '.';
    char shape;
    int row;
    int col;

    Block(char type){
        this.shape = type;
    }

}
