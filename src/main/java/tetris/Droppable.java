// Copyright (c) 2008-2017  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

/**
 * Created by jdub on 06/03/17.
 */
public interface Droppable {

    int getWidth();
    void setWidth(int width);
    int getHeight();
    void setHeight(int height);
//    Block[][] getShape();
    Block getBlockAt(int x, int y);

//    void drop(int boardWidth);
//    void setOffset(int blocks);
//    boolean dropOnTick();
}
