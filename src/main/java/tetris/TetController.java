package tetris;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by jdub on 22/04/17.
 */
public class TetController {

    public static final int BASE_SPEED = 400;
    public static final float SPEED_MULTIPLIER = 0.8f;
    public static final int THRESHOLD = 200;
    public static final double RATE_OF_CHANGE = 1.5;

    enum GameState {PAUSED, RUNNING, OVER}

    private TetView view;
    private Board model;

    public Board getModel() {
        return model;
    }

    public Timer getClock() {
        return clock;
    }

    private Timer clock;

    public ScoreBoard getScoreKeeper() {
        return scoreKeeper;
    }

    private ScoreBoard scoreKeeper;
    private int level = 0;
    private GameState gameState;


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
        clock.start();
        drawBoard();
        view.setVisible(true);
        gameState = GameState.RUNNING;

    }

    private void setSpeed(int level) {
        clock.setDelay((int) (BASE_SPEED * Math.pow(SPEED_MULTIPLIER, level)));
    }

    private void drawBoard() {
        view.setBlocks(model.toString());
    }

    private void doTurn() {

        try {
            model.doTurn();
        } catch (IllegalStateException e) {
            System.out.println("It's all over, buddy!");
            clock.stop();
            gameState = GameState.OVER;
            scoreKeeper.setText("GAME OVER!  Final Score: " + scoreKeeper.getScore()
                    + ", on level " + (level + 1));
        }

        drawBoard();

        if (scoreKeeper.getScore() >= THRESHOLD * Math.pow(RATE_OF_CHANGE, level)) {
            setSpeed(++level);
        }

    }

    public int getLevel() {
        return level;
    }

    class TetListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            char key = e.getKeyChar();
            if (gameState == GameState.RUNNING) {
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
                }
            }
            if (key == 'p') {
                togglePause();
            }
            drawBoard();
        }

        private void togglePause() {
            if (gameState == GameState.RUNNING) {
                gameState = GameState.PAUSED;
                clock.stop();
            } else if (gameState == GameState.PAUSED) {
                gameState = GameState.RUNNING;
                clock.start();
            }
        }


        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}
