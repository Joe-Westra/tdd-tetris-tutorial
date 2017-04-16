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

    public final Board board = new Board(6, 8);


    /**
     * After re-reading the rules of TDD it's evident that I have broken
     * pretty much all of them.  At this point it in the introduction
     * it would be wise to learn more about the basics.
     */


    public class full_rows_are_removed_and_scored {

        @Before
        public void load_the_board() {
            board.addScoreBoard(new ScoreBoard());
            board.drop(Tetromino.I_SHAPE);
            board.moveLeft();
            board.moveLeft();
            board.dropToBottom();
            board.drop(Tetromino.I_SHAPE);
            board.tick();
            board.tick();
            board.tick();
        }

        @Test
        public void the_score_starts_at_0() {
            assertTrue(board.getScore() == 0);
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
        public void multiple_rows_are_cleared() {
            board.moveLeft();
            board.moveLeft();
            board.dropToBottom();
            board.drop(Tetromino.O_SHAPE);
            board.moveRight();
            board.dropToBottom();
            board.drop(Tetromino.O_SHAPE);
            board.moveRight();
            board.moveRight();
            board.moveRight();
            board.dropToBottom();
            assertEquals("" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n", board.toString());
            assertTrue(board.getScore() == 2);

        }

    }

    public class cleared_rows_drop_above_rows {

        @Before
        public void setup_board() {
            board.addScoreBoard(new ScoreBoard());

            board.drop(Tetromino.O_SHAPE);
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.dropToBottom();
            board.drop(Tetromino.O_SHAPE);
            board.moveLeft();
            board.dropToBottom();
            board.drop(Tetromino.O_SHAPE);
            board.moveRight();
            board.dropToBottom();
            board.drop(Tetromino.T_SHAPE);
            board.moveLeft();
            board.moveLeft();
            board.moveLeft();
            board.dropToBottom();
            board.drop(Tetromino.O_SHAPE);
            board.moveRight();
            board.moveRight();
            board.moveRight();
            board.tick();
            board.tick();
            board.tick();
            board.tick();
        }

        @Test
        public void multiple_level_drop() {
            board.tick();
            assertEquals("" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    "........\n" +
                    ".T......\n" +
                    "TTT.....\n", board.toString());
            assertTrue(board.getScore() == 2);
        }
    }


    public class the_game_can_be_lost {

        @Before
        public void setup_board() {
            board.drop(Tetromino.O_SHAPE);
            board.dropToBottom();
            board.drop(Tetromino.O_SHAPE);
            board.dropToBottom();
            board.drop(Tetromino.O_SHAPE);
        }

        @Test
        public void if_there_is_no_room_to_drop() {
            MyAsserts.assertThrows(IllegalStateException.class, "Game Over",
                    () -> board.tick());
        }
    }
}
