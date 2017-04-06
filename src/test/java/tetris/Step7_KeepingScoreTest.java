// A Joe Westra Original

package tetris;

import net.orfjackal.nestedjunit.NestedJUnit;
import org.junit.*;
import org.junit.runner.RunWith;

/**
 * Created by jdub on 05/04/17.
 */
@RunWith(NestedJUnit.class)
public class Step7_KeepingScoreTest extends Assert {

    public static Board board = new Board(6, 8);

    public class full_rows_are_removed {

        @Before
        public void load_the_board() {
            board.drop(Tetromino.I_SHAPE);
            board.moveLeft();
            board.moveLeft();
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            board.drop(Tetromino.I_SHAPE);
            board.tick();
            board.tick();
            board.tick();
        }

        @Test
        public void clears_one_full_row() {
            board.moveRight();
            board.moveRight();
            board.tick();
            board.tick();
            assertEquals("" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "IIIIIIII\n", board.toString());
            board.tick();
            assertEquals("" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
            assertTrue(board.getScore() == 1);
        }

        @Test
        public void multiple_rows_are_cleared(){
            board.moveLeft();
            board.moveLeft();
            board.tick();
            board.tick();
            board.drop(Tetromino.O_SHAPE);
            board.moveRight();
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            board.drop(Tetromino.O_SHAPE);
            board.moveRight();
            board.moveRight();
            board.moveRight();
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            board.tick();
            assertEquals("" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n",board.toString());
        }

        @Test
        public void rows_above_are_dropped(){
            board.rotateRight();
            board.tick();
        }
    }


/*        @Test
        public void the_score_starts_at_0() {
            assertTrue(board.getScore() == 0);
        }*/
}
