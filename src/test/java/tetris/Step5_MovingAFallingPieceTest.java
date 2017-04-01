// Copyright (c) 2008-2015  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

import net.orfjackal.nestedjunit.NestedJUnit;
import org.junit.*;
import org.junit.runner.RunWith;

@RunWith(NestedJUnit.class)
public class Step5_MovingAFallingPieceTest extends Assert {

    // Step 5: It's your turn now
    // - Remove the @Ignore annotation from this class
    // - The test names have been provided, you just need to fill in the test body
    // - Next step: RotatingAFallingPieceTest

    private final Board board = new Board(6,8);

    public class falling_pieces{

        @Before
        public void drop_a_piece(){
            board.drop(Tetromino.I_SHAPE);
        }

        @Test
        public void enter_board_at_the_top_row(){
            assertEquals(""+
                    "..IIII..\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }
    }

    public class pieces_are_mobile{

        @Before
        public void drop_an_i(){
            board.drop(Tetromino.I_SHAPE);
        }

        @Test
        public void can_be_moved_left(){
            board.moveLeft();
            assertEquals(""+
                    ".IIII...\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }

        @Test
        public void can_be_moved_right(){
            board.moveRight();
            assertEquals(""+
                    "...IIII.\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }

        @Test
        public void can_be_moved_down(){
            board.moveDown();
            assertEquals(""+
                    "........\n" +
                    "..IIII..\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }
    }

    public class board_boundaries_are_respected{

        @Before
        public void drop_a_t(){
            board.drop(Tetromino.T_SHAPE);
        }

        @Test
        public void stops_at_left_edge(){
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            assertEquals("" +
                    ".T......\n" +
                    "TTT.....\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
            board.moveLeft();
            assertEquals("" +
                    ".T......\n" +
                    "TTT.....\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }

        @Test
        public void stops_at_right_edge(){
            board.moveRight();
            board.moveRight();
            assertEquals("" +
                    "......T.\n" +
                    ".....TTT\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
            board.moveRight();
            assertEquals("" +
                    "......T.\n" +
                    ".....TTT\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
        }

        @Test
        public void stops_at_bottom(){
            board.moveDown();
            board.moveDown();
            board.moveDown();
            board.moveDown();
            assertEquals("" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "....T...\n" +
            "...TTT..\n", board.toString());
            assertTrue(board.hasFalling());

            board.moveDown();
            assertEquals("" +
            "........\n" +
            "........\n" +
            "........\n" +
            "........\n" +
            "....T...\n" +
            "...TTT..\n", board.toString());
            assertFalse(board.hasFalling());
        }
    }

    public class collision_detection{

        @Before
        public void setup_the_board(){
            board.drop(Tetromino.I_SHAPE_VERT);
            board.moveRight();
            board.moveRight();
            board.moveRight();
            board.moveDown();
            board.moveDown();
            board.moveDown();
            board.drop(Tetromino.I_SHAPE_VERT);
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.moveDown();
            board.moveDown();
            board.moveDown();
        }

        @Test
        public void board_looks_like_a_hockey_net(){
            assertEquals(""+
            "........\n" +
            "........\n" +
            "I......I\n" +
            "I......I\n" +
            "I......I\n" +
            "I......I\n",board.toString());
        }

        @Test
        public void detects_pieces_to_right(){
            board.drop(Tetromino.T_SHAPE);
            board.tick();
            board.tick();
            board.moveRight();
            board.moveRight();
            assertEquals(""+
                    "........\n" +
                    "........\n" +
                    "I....T.I\n" +
                    "I...TTTI\n" +
                    "I......I\n" +
                    "I......I\n",board.toString());

        }

        @Test
        public void detects_pieces_to_left(){
            board.drop(Tetromino.T_SHAPE);
            board.tick();
            board.tick();
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            assertEquals(""+
                    "........\n" +
                    "........\n" +
                    "I.T....I\n" +
                    "ITTT...I\n" +
                    "I......I\n" +
                    "I......I\n",board.toString());

        }


        @Test
        public void detects_pieces_below(){
            board.drop(Tetromino.T_SHAPE);
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.tick();
            assertEquals(""+
                    ".T......\n" +
                    "TTT.....\n" +
                    "I......I\n" +
                    "I......I\n" +
                    "I......I\n" +
                    "I......I\n",board.toString());
            assertFalse(board.hasFalling());

        }
    }
}
