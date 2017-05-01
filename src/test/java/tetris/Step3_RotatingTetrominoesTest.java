// Copyright (c) 2008-2015  Esko Luontola <www.orfjackal.net>
// You may use and modify this source code freely for personal non-commercial use.
// This source code may NOT be used as course material without prior written agreement.

package tetris;

import net.orfjackal.nestedjunit.NestedJUnit;
import org.junit.*;
import org.junit.runner.RunWith;

/**
 * @author Esko Luontola
 */
@RunWith(NestedJUnit.class)
public class Step3_RotatingTetrominoesTest extends Assert {

    // Step 3: The actual rotation algorithms
    // - Remove the @Ignore annotation from this class
    // - See README for how "Tetromino" is different from "Piece"
    // - Next step: FallingPiecesTest


    private Tetromino shape;


    public class All_shape_instances {

        @Before
        public void createAnyShape() {
            shape = Tetromino.T_SHAPE;
        }

        @Test
        public void are_immutable() {
            String original = shape.toString();
            shape.rotateRight();
            assertEquals(original, shape.toString());
            shape.rotateLeft();
            assertEquals(original, shape.toString());
        }
    }


    public class The_T_shape {

        @Before
        public void createTShape() {
            shape = Tetromino.T_SHAPE;
        }

        @Test
        public void is_shaped_like_T() {
            assertEquals("" +
                    ".T.\n" +
                    "TTT\n" +
                    "...\n", shape.toString());
        }

        @Test
        public void can_be_rotated_right_3_times() {
            shape = shape.rotateRight();
            assertEquals("" +
                    ".T.\n" +
                    ".TT\n" +
                    ".T.\n", shape.toString());
            shape = shape.rotateRight();
            assertEquals("" +
                    "...\n" +
                    "TTT\n" +
                    ".T.\n", shape.toString());
            shape = shape.rotateRight();
            assertEquals("" +
                    ".T.\n" +
                    "TT.\n" +
                    ".T.\n", shape.toString());
        }

        @Test
        public void can_be_rotated_left_3_times() {
            shape = shape.rotateLeft();
            assertEquals("" +
                    ".T.\n" +
                    "TT.\n" +
                    ".T.\n", shape.toString());
            shape = shape.rotateLeft();
            assertEquals("" +
                    "...\n" +
                    "TTT\n" +
                    ".T.\n", shape.toString());
            shape = shape.rotateLeft();
            assertEquals("" +
                    ".T.\n" +
                    ".TT\n" +
                    ".T.\n", shape.toString());
        }

        @Test
        public void rotating_it_4_times_will_go_back_to_the_original_shape() {
            String originalShape = shape.toString();
            shape = shape.rotateRight().rotateRight().rotateRight().rotateRight();
            assertEquals(originalShape, shape.toString());
            shape = shape.rotateLeft().rotateLeft().rotateLeft().rotateLeft();
            assertEquals(originalShape, shape.toString());
        }
    }


    public class The_I_shape {

        @Before
        public void createIShape() {
            shape = Tetromino.I_SHAPE;
        }

        @Test
        public void is_shaped_like_I() {
            assertEquals("" +
                    ".....\n" +
                    ".....\n" +
                    "IIII.\n" +
                    ".....\n" +
                    ".....\n", shape.toString());
        }

        @Test
        public void can_be_rotated_right_once() {
            shape = shape.rotateRight();
            assertEquals("" +
                    "..I..\n" +
                    "..I..\n" +
                    "..I..\n" +
                    "..I..\n" +
                    ".....\n", shape.toString());
        }

        @Test
        public void can_be_rotated_left_once() {
            shape = shape.rotateLeft();
            assertEquals("" +
                    "..I..\n" +
                    "..I..\n" +
                    "..I..\n" +
                    "..I..\n" +
                    ".....\n", shape.toString());
        }

        @Test
        public void rotating_it_twice_will_get_back_to_the_original_shape() {
            String originalShape = shape.toString();
            shape = shape.rotateRight().rotateRight();
            assertEquals(originalShape, shape.toString());
            shape = shape.rotateLeft().rotateLeft();
            assertEquals(originalShape, shape.toString());
        }
    }


    public class The_O_shape {

        @Before
        public void createOShape() {
            shape = Tetromino.O_SHAPE;
        }

        @Test
        public void is_shaped_like_O() {
            assertEquals("" +
                    ".OO\n" +
                    ".OO\n" +
                    "...\n", shape.toString());
        }

        @Test
        public void cannot_be_rotated_right() {
            shape = shape.rotateRight();
            assertEquals("" +
                    ".OO\n" +
                    ".OO\n" +
                    "...\n", shape.toString());
        }

        @Test
        public void cannot_be_rotated_left() {
            shape = shape.rotateLeft();
            assertEquals("" +
                    ".OO\n" +
                    ".OO\n" +
                    "...\n", shape.toString());
        }
    }

    public class The_J_shape {
        @Before
        public void create_J_shape() {
            shape = Tetromino.J_SHAPE;
        }

        @Test
        public void is_shaped_like_a_J() {
            assertEquals("" +
                    "..J\n" +
                    "..J\n" +
                    ".JJ\n", shape.toString());
        }

        @Test
        public void can_rotate_right() {
            shape = shape.rotateRight();
            assertEquals("" +
                    "...\n" +
                    "J..\n" +
                    "JJJ\n", shape.toString());
            shape = shape.rotateRight();
            assertEquals("" +
                    "JJ.\n" +
                    "J..\n" +
                    "J..\n", shape.toString());
            shape = shape.rotateRight();
            assertEquals("" +
                    "JJJ\n" +
                    "..J\n" +
                    "...\n", shape.toString());
            shape = shape.rotateRight();
            assertEquals("" +
                    "..J\n" +
                    "..J\n" +
                    ".JJ\n", shape.toString());
        }

        @Test
        public void can_rotate_left() {
            shape = shape.rotateLeft();
            assertEquals("" +
                    "JJJ\n" +
                    "..J\n" +
                    "...\n", shape.toString());
            shape = shape.rotateLeft();
            assertEquals("" +
                    "JJ.\n" +
                    "J..\n" +
                    "J..\n", shape.toString());
            shape = shape.rotateLeft();
            assertEquals("" +
                    "...\n" +
                    "J..\n" +
                    "JJJ\n", shape.toString());
            shape = shape.rotateLeft();
            assertEquals("" +
                    "..J\n" +
                    "..J\n" +
                    ".JJ\n", shape.toString());
        }
    }

    public class The_L_shape {
        @Before
        public void set_as_L() {
            shape = Tetromino.L_SHAPE;
        }

        @Test
        public void is_shaped_like_an_L() {
            assertEquals("" +
                    "L..\n" +
                    "L..\n" +
                    "LL.\n", shape.toString());
        }

        @Test
        public void can_rotate_left() {
            shape = shape.rotateLeft();
            assertEquals("" +
                    "...\n" +
                    "..L\n" +
                    "LLL\n", shape.toString());
            shape = shape.rotateLeft();
            assertEquals("" +
                    ".LL\n" +
                    "..L\n" +
                    "..L\n", shape.toString());
            shape = shape.rotateLeft();
            assertEquals("" +
                    "LLL\n" +
                    "L..\n" +
                    "...\n", shape.toString());
            shape = shape.rotateLeft();
            assertEquals("" +
                    "L..\n" +
                    "L..\n" +
                    "LL.\n", shape.toString());
        }

        @Test
        public void can_rotate_right() {
            shape = shape.rotateRight();
            assertEquals("" +
                    "LLL\n" +
                    "L..\n" +
                    "...\n", shape.toString());
            shape = shape.rotateRight();
            assertEquals("" +
                    ".LL\n" +
                    "..L\n" +
                    "..L\n", shape.toString());
            shape = shape.rotateRight();
            assertEquals("" +
                    "...\n" +
                    "..L\n" +
                    "LLL\n", shape.toString());
            shape = shape.rotateRight();
            assertEquals("" +
                    "L..\n" +
                    "L..\n" +
                    "LL.\n", shape.toString());
        }
    }

    public class The_S_shape {
        @Before
        public void set_as_S() {
            shape = Tetromino.S_SHAPE;
        }

        @Test
        public void is_shaped_like_an_S() {
            assertEquals("" +
                    "...\n" +
                    ".SS\n" +
                    "SS.\n", shape.toString());
        }

        @Test
        public void can_rotate_left() {
            shape = shape.rotateLeft();
            assertEquals(shape, Tetromino.S_SHAPE_VERT);
            shape = shape.rotateLeft();
            assertEquals(shape, Tetromino.S_SHAPE);
            shape = shape.rotateLeft();
            assertEquals(shape, Tetromino.S_SHAPE_VERT);
        }

        @Test
        public void can_rotate_right() {
            shape = shape.rotateRight();
            assertEquals(shape, Tetromino.S_SHAPE_VERT);
            shape = shape.rotateRight();
            assertEquals(shape, Tetromino.S_SHAPE);
            shape = shape.rotateRight();
            assertEquals(shape, Tetromino.S_SHAPE_VERT);
        }
    }

    public class The_Z_shape {
        @Before
        public void set_as_Z() {
            shape = Tetromino.Z_SHAPE;
        }

        @Test
        public void is_shaped_like_an_Z() {
            assertEquals("" +
                    "...\n" +
                    "ZZ.\n" +
                    ".ZZ\n", shape.toString());
        }

        @Test
        public void can_rotate_left() {
            shape = shape.rotateLeft();
            assertEquals(shape.toString(), Tetromino.Z_SHAPE_VERT.toString());
            shape = shape.rotateLeft();
            assertEquals(shape.toString(), Tetromino.Z_SHAPE.toString());
            shape = shape.rotateLeft();
            assertEquals(shape.toString(), Tetromino.Z_SHAPE_VERT.toString());
        }

        @Test
        public void can_rotate_right() {
            shape = shape.rotateRight();
            assertEquals(shape, Tetromino.Z_SHAPE_VERT);
            shape = shape.rotateRight();
            assertEquals(shape, Tetromino.Z_SHAPE);
            shape = shape.rotateRight();
            assertEquals(shape, Tetromino.Z_SHAPE_VERT);
        }
    }
}
