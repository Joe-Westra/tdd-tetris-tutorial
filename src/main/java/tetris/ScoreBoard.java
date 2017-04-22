

package tetris;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jdub on 14/04/17.
 */
public class ScoreBoard extends JLabel{
    private int score;

    ScoreBoard(){
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY,5,true));
        this.setText(String.valueOf(score) + " pts");
        this.setForeground(Color.LIGHT_GRAY);
        this.setOpaque(true);
    }

    public void increaseScore(int amount) {
        score += amount;
        this.setText(String.valueOf(score) + " pts");
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
