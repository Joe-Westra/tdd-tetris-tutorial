package tetris;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by jdub on 22/04/17.
 *
 */
public class TetController {

    private TetView view;
    private Board model;
    private Timer clock;
    private ScoreBoard scoreKeeper;


    public static void main(String args[]) {
        TetController game = new TetController();
        game.startGame();
    }

    TetController() {
        view = new TetView();
        model = new Board(20, 10);
        view.addTetListener(new TetListener());
        model.addScoreBoard(scoreKeeper = new ScoreBoard());
        view.addScoreBoard(scoreKeeper);
        clock = new Timer(300, e -> doTurn());

        startGame();
    }

    private void startGame() {
        drawBoard();
        view.setVisible(true);
        clock.start();
    }

    private void drawBoard() {
        view.setBlocks(model.toString());
    }

    private void doTurn() {
        try {
            model.doTurn();
        } catch (IllegalStateException e){

        }
        drawBoard();
    }

    class TetListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            char key = e.getKeyChar();
            switch (key) {
                case 'a':
                    model.moveLeft();
                    break;
                case 'd':
                    model.moveRight();
                    break;
                case 'w':
                    model.rotateRight();
                    break;
                case 's':
                    model.rotateLeft();
                    break;
                case ' ':
                    model.dropToBottom();
                    break;
                case 'p':
                    if (clock.isRunning()) {
                        clock.stop();
                    } else {
                        clock.start();
                    }
            }
            drawBoard();
        }

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}
    }
}
