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
    public static final int BASE_SPEED = 300;
    public static final float SPEED_MULTIPLIER = 0.8f;
    public static final int THRESHOLD = 200;
    private int level = 0;

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
        clock = new Timer(BASE_SPEED, e -> doTurn());

        startGame();
    }

    private void startGame() {
        drawBoard();
        view.setVisible(true);
        clock.start();
    }

    private void setSpeed(){
        clock.setDelay((int)(BASE_SPEED * Math.pow(SPEED_MULTIPLIER, level)));
    }
    private void drawBoard() {
        view.setBlocks(model.toString());
    }

    private void doTurn() {
        try {
            model.doTurn();
        } catch (IllegalStateException e){
            System.out.println("It's all over, buddy!");
            clock.stop();
            clock = null;
        }
        drawBoard();
        if(scoreKeeper.getScore() >= THRESHOLD * Math.pow(1.5,level)){
            level++;
            setSpeed();
        }

    }

    class TetListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            char key = e.getKeyChar();
            switch (key) {
                case 'a':
                    if(clock.isRunning())
                    model.moveLeft();
                    break;
                case 'd':
                    if(clock.isRunning())
                        model.moveRight();
                    break;
                case 'w':
                    if(clock.isRunning())
                    model.rotateRight();
                    break;
                case 's':
                    if(clock.isRunning())
                    model.rotateLeft();
                    break;
                case ' ':
                    if(clock.isRunning())
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
