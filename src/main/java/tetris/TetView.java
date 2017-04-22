package tetris;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * Created by jdub on 21/04/17.
 */
public class TetView extends JFrame {

    BoardCell[][] blocks;
    JPanel panel;
    public TetView() {
        this(20, 10);
    }

    public TetView(int rows, int columns) {
        this.setLayout(new BorderLayout());
        blocks = new BoardCell[rows][columns];
        this.setSize(400, 800);
        panel = new JPanel(new GridLayout(rows, columns));
        this.add(panel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setBlocks(String s) {
        panel.removeAll();
        String str = s.replaceAll("\n", "");
        for (int row = 0; row < blocks.length; row++) {
            for (int column = 0; column < blocks[0].length; column++) {
                char type = str.charAt(column + (row * blocks[0].length));
                blocks[row][column] = new BoardCell(type);
               panel.add(blocks[row][column]);
            }

        }
        this.revalidate();
        this.repaint();
    }


    public void addTetListener(KeyListener t) {
        this.addKeyListener(t);
    }

    public void addScoreBoard(ScoreBoard s){
        this.add(s,BorderLayout.NORTH);
    }
}

class BoardCell extends JLabel {
    Color color;

    BoardCell(char type) {
        setColor(type);
        setBackground(color);
        setSize(40, 40);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(
                BevelBorder.RAISED,
                color.brighter(),
                color.darker()));
    }

    private void setColor(char type) {
        switch (type) {
            case '.':
                color = Color.BLACK;
                break;
            case 'J':
                color = Color.BLUE;
                break;
            case 'L':
                color = Color.CYAN;
                break;
            case 'T':
                color = Color.RED;
                break;
            case 'I':
                color = Color.ORANGE;
                break;
            case 'O':
                color = Color.WHITE;
                break;
            default:
                color = Color.MAGENTA;
        }
    }
}