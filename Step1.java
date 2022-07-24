import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessboardUI implements Runnable {

    private Chessboard board;
    private JButton[][] squareButtons;
    private JPanel boardPanel;
    private JFrame chessFrame;

    public ChessboardUI() {
        SwingUtilities.invokeLater(this);
    }

    public void init() {
        board = new Chessboard();
        this.squareButtons = new JButton[Chessboard.NBROW][Chessboard.NBCOL];
        boardPanel = new JPanel();
        this.boardPanel.setLayout(new GridLayout(Chessboard.NBROW, Chessboard.NBCOL));
        for (int i=0;i<Chessboard.NBCOL;++i) {
            for (int j=0;j<Chessboard.NBROW;++j) {
                this.squareButtons[i][j] = new JButton(board.getSquare(i,j).toString());
                this.squareButtons[i][j].setPreferredSize(new Dimension(70, 70));
                if((i+j)%2==0) {
                    this.squareButtons[i][j].setBackground(java.awt.Color.WHITE);
                } else {
                    this.squareButtons[i][j].setBackground(java.awt.Color.LIGHT_GRAY);
                }
                this.squareButtons[i][j].setOpaque(true);
                this.boardPanel.add(this.squareButtons[i][j]);
            }
        }
        this.chessFrame = new JFrame();
        this.chessFrame.add(this.boardPanel);
        this.chessFrame.setTitle("SID Chess");
        this.chessFrame.setSize(400,400);
        this.chessFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.chessFrame.setResizable(false);
        this.chessFrame.pack();
        this.chessFrame.setVisible(true);
    }

    @Override
    public void run() {
        this.init();
    }

    public static void main(String[] args) {
        ChessboardUI view = new ChessboardUI();
    }

}
