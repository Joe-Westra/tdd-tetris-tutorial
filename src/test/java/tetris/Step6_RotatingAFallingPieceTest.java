// Copyright (c) 2008-2015  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

import net.orfjackal.nestedjunit.NestedJUnit;
import org.junit.*;
import org.junit.runner.RunWith;

@RunWith(NestedJUnit.class)
public class Step6_RotatingAFallingPieceTest extends Assert {

    // Step 6: Training wheels off
    // - Remove the @Ignore annotation from this class
    // - You're now responsible for covering all corner cases
    // - Next step: see the README for details

    private final Board board = new Board(6, 8);


    public class I_Pieces_can_be_rotated_in_both_directions {

        @Before
        public void set_up_board() {
            board.drop(Tetromino.I_SHAPE);
            board.tick();
            board.tick();
        }

        @Test
        public void rotate_right_several_times() {
            board.rotateRight();
            assertEquals("" +
                    "........\n" +
                    "....I...\n" +
                    "....I...\n" +
                    "....I...\n" +
                    "....I...\n" +
                    "........\n", board.toString());
            board.rotateRight();
            assertEquals("" +
                    "........\n" +
                    "........\n" +
                    "..IIII..\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
            board.rotateRight();
            assertEquals("" +
                    "........\n" +
                    "....I...\n" +
                    "....I...\n" +
                    "....I...\n" +
                    "....I...\n" +
                    "........\n", board.toString());
        }

        @Test
        public void rotate_left() {
            board.rotateLeft();
            assertEquals("" +
                    "........\n" +
                    "....I...\n" +
                    "....I...\n" +
                    "....I...\n" +
                    "....I...\n" +
                    "........\n", board.toString());
            board.rotateLeft();
            assertEquals("" +
                    "........\n" +
                    "........\n" +
                    "..IIII..\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
            board.rotateLeft();
            assertEquals("" +
                    "........\n" +
                    "....I...\n" +
                    "....I...\n" +
                    "....I...\n" +
                    "....I...\n" +
                    "........\n", board.toString());
        }
    }

    public class T_Pieces_can_be_rotated_in_both_directions {
        @Before
        public void setup_board() {
            board.drop(Tetromino.T_SHAPE);
            board.tick();
        }

        @Test
        public void several_right_rotations() {
            board.rotateRight();
            assertEquals("" +
                    "........\n" +
                    "....T...\n" +
                    "....TT..\n" +
                    "....T...\n" +
                    "........\n" +
                    "........\n", board.toString());
            board.rotateRight();
            assertEquals("" +
                    "........\n" +
                    "........\n" +
                    "...TTT..\n" +
                    "....T...\n" +
                    "........\n" +
                    "........\n", board.toString());
            board.rotateRight();
            assertEquals("" +
                    "........\n" +
                    "....T...\n" +
                    "...TT...\n" +
                    "....T...\n" +
                    "........\n" +
                    "........\n", board.toString());
            board.rotateRight();
            assertEquals("" +
                    "........\n" +
                    "....T...\n" +
                    "...TTT..\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }

        @Test
        public void several_left_rotations() {
            board.rotateLeft();
            assertEquals("" +
                    "........\n" +
                    "....T...\n" +
                    "...TT...\n" +
                    "....T...\n" +
                    "........\n" +
                    "........\n", board.toString());
            board.rotateLeft();
            assertEquals("" +
                    "........\n" +
                    "........\n" +
                    "...TTT..\n" +
                    "....T...\n" +
                    "........\n" +
                    "........\n", board.toString());
            board.rotateLeft();
            assertEquals("" +
                    "........\n" +
                    "....T...\n" +
                    "....TT..\n" +
                    "....T...\n" +
                    "........\n" +
                    "........\n", board.toString());
            board.rotateLeft();
            assertEquals("" +
                    "........\n" +
                    "....T...\n" +
                    "...TTT..\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }


    }

    public class Rotations_at_the_top_row {

        @Test
        public void are_valid_for_T_pieces() {
            board.drop(Tetromino.T_SHAPE);
            board.rotateRight();
            assertEquals("" +
                    "....T...\n" +
                    "....TT..\n" +
                    "....T...\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }

        @Test
        public void are_valid_for_O_pieces() {
            board.drop(Tetromino.O_SHAPE);
            board.rotateRight();
            assertEquals("" +
                    "...OO...\n" +
                    "...OO...\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }


        @Test
        public void are_invalid_for_I_pieces() {
            board.drop(Tetromino.I_SHAPE);
            board.rotateRight();
            assertEquals("" +
                    "..IIII..\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }

    }


    public class Wall_kicks_work_for_T_pieces {

        /*
         "Up to 3 locations are tried, in this order, before rotation will fail:
        Basic rotation
        1 space right of basic rotation
        1 space left of basic rotation"
         */
        @Before
        public void setup_board() {
            board.drop(Tetromino.T_SHAPE);
            board.tick();
        }

        @Test
        public void kick_out_from_left_wall() {
            board.rotateRight();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            assertEquals("" +
                    "........\n" +
                    "T.......\n" +
                    "TT......\n" +
                    "T.......\n" +
                    "........\n" +
                    "........\n", board.toString());
            board.rotateLeft();
            assertEquals("" +
                    "........\n" +
                    ".T......\n" +
                    "TTT.....\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }

        @Test
        public void kick_out_from_right_wall() {
            board.rotateLeft();
            board.moveRight();
            board.moveRight();
            board.moveRight();
            assertEquals("" +
                    "........\n" +
                    ".......T\n" +
                    "......TT\n" +
                    ".......T\n" +
                    "........\n" +
                    "........\n", board.toString());
            board.rotateRight();
            assertEquals("" +
                    "........\n" +
                    "......T.\n" +
                    ".....TTT\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }
    }


    public class Wall_kicks_work_for_I_pieces {

        @Before
        public void setup_board() {
            board.drop(Tetromino.I_SHAPE);
            board.tick();
            board.tick();
        }

        @Test
        public void kick_from_right_wall() {
            board.rotateRight();
            board.moveRight();
            board.moveRight();
            board.moveRight();
            assertEquals(""+
                    "........\n" +
                    ".......I\n" +
                    ".......I\n" +
                    ".......I\n" +
                    ".......I\n" +
                    "........\n", board.toString());
            board.rotateRight();
            assertEquals(""+
                    "........\n" +
                    "........\n" +
                    "....IIII\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }

        @Test
        public void kick_from_left_wall() {
            board.rotateRight();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            assertEquals(""+
                    "........\n" +
                    "I.......\n" +
                    "I.......\n" +
                    "I.......\n" +
                    "I.......\n" +
                    "........\n", board.toString());
            board.rotateRight();
            assertEquals(""+
                    "........\n" +
                    "........\n" +
                    "IIII....\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }

        }

    // TODO: a falling piece can be rotated clockwise
    // TODO: a falling piece can be rotated counter-clockwise
    // TODO: it cannot be rotated when there is no room to rotate (left wall, right wall, other pieces...)

    // TODO: when piece is up against a wall (or piece) and it is rotated (no room to rotate), move it away from the wall ("wallkick")
    // See: http://bsixcentdouze.free.fr/tc/tgm-en/tgm.html
    // http://bsixcentdouze.free.fr/tc/tgm-en/img/wallkick1.png
    // http://bsixcentdouze.free.fr/tc/tgm-en/img/wallkick2.png
    // http://bsixcentdouze.free.fr/tc/tgm-en/img/wallkick3.png
}
