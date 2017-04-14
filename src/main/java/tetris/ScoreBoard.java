

package tetris;

/**
 * Created by jdub on 14/04/17.
 */
public class ScoreBoard {
    private int score;

    public void increaseScore(int amount){
        score += amount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
