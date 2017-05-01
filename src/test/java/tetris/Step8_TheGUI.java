// A Joe Westra Original
package tetris;

import net.orfjackal.nestedjunit.NestedJUnit;
import org.junit.*;
import org.junit.runner.RunWith;


/**
 * Created by jdub on 14/04/17.
 * <p>
 * TODO:
 * The game works. Up next:
 * <p>
 * <p>
 * -Implementing the missing tests
 * DONE: for speed changes
 * for pausing & game overs
 * - Serious refactoring
 * Implement a better for-each for pieces
 * Add colours to the Tetromino Class Types
 * -DONE: Clean up keyEvents
 * -DONE: Increase scoring (bonus points for dropping to bottom)
 * -DONE: Implement speed changes
 * -Add Highscore list
 * <p>
 * -Fix known bugs:
 * FIXED: FIGURE OUT WHY THE 7th TEST SUITE ISN'T WORKING.
 * ~I changed the scoring mechanism, and test scores were innaccurate.
 * ~NOTE: using assertEquals rather than assertTrue displayed more information.
 */

@RunWith(NestedJUnit.class)
public class Step8_TheGUI extends Assert {

    private final TetController controller = new TetController();

    public class The_speed {

        @Test
        public void is_initally_zero() {
            assertEquals(controller.getLevel(), 0);
            assertEquals(controller.BASE_SPEED, controller.getClock().getDelay());
        }

        @Test
        public void increments_at_appropriate_intervals() {
            for (int i = 0; i < 19; i++) {
                controller.getScoreKeeper().setScore((int) (controller.THRESHOLD * Math.pow(controller.RATE_OF_CHANGE, i) - 1));
                controller.getModel().drop(Tetromino.I_SHAPE);
                controller.getModel().tick();
                controller.getModel().dropToBottom();
                assertEquals((long) (controller.BASE_SPEED * Math.pow(1.5, controller.getLevel())),
                        (long) controller.getClock().getDelay());
            }
        }
    }


}
