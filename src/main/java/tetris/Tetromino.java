// Copyright (c) 2008-2017  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

/**
 * Created by jdub on 03/03/17.
 */
public class Tetromino implements Droppable {
    private Piece shape;

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
            new Tetromino("..I..\n" +
                    "..I..\n" +
                    "..I..\n" +
                    "..I..\n" +
                    ".....\n");

    public static final Tetromino J_SHAPE =
            new Tetromino("" +
                    "..J\n" +
                    "..J\n" +
                    ".JJ\n");

    public static final Tetromino L_SHAPE =
            new Tetromino("" +
                    "L..\n" +
                    "L..\n" +
                    "LL.\n");

    public static final Tetromino Z_SHAPE =
            new Tetromino("" +
                    "...\n" +
                    "ZZ.\n" +
                    ".ZZ\n");

    public static final Tetromino Z_SHAPE_VERT =
            new Tetromino("" +
                    "..Z\n" +
                    ".ZZ\n" +
                    ".Z.\n");

    public static final Tetromino S_SHAPE =
            new Tetromino("" +
                    "...\n" +
                    ".SS\n" +
                    "SS.\n");

    public static final Tetromino S_SHAPE_VERT =
            new Tetromino("" +
                    "S..\n" +
                    "SS.\n" +
                    ".S.\n");

    Tetromino(String s) {
        shape = new Piece(s);
    }

    public Tetromino rotateRight() {
        if (this == O_SHAPE ||
                this == I_SHAPE_VERT ||
                this == I_SHAPE ||
                this == Z_SHAPE ||
                this == Z_SHAPE_VERT ||
                this == S_SHAPE ||
                this == S_SHAPE_VERT) {
            return rotateBoringShapes();
        } else {
            return new Tetromino(shape.rotateRight().toString());
        }
    }

    public Tetromino rotateLeft() {
        if (this == O_SHAPE ||
                this == I_SHAPE_VERT ||
                this == I_SHAPE ||
                this == Z_SHAPE ||
                this == Z_SHAPE_VERT ||
                this == S_SHAPE ||
                this == S_SHAPE_VERT) {
            return rotateBoringShapes();
        } else {
            return new Tetromino(shape.rotateLeft().toString());
        }
    }

    private Tetromino rotateBoringShapes() {
        if (this == O_SHAPE) {
            return O_SHAPE;
        } else if (this == I_SHAPE) {
            return I_SHAPE_VERT;
        } else if (this == I_SHAPE_VERT) {
            return I_SHAPE;
        } else if (this == Z_SHAPE_VERT) {
            return Z_SHAPE;
        } else if (this == Z_SHAPE) {
            return Z_SHAPE_VERT;
        } else if (this == S_SHAPE_VERT) {
            return S_SHAPE;
        } else if (this == S_SHAPE) {
            return S_SHAPE_VERT;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return shape.toString();
    }

    public int getWidth() {
        return shape.p[0].length;
    }

    public int getHeight() {
        return shape.p.length;
    }

    @Override
    public Block getBlockAt(int row, int column) {
        return shape.getBlockAt(row, column);
    }

}
