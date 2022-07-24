import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessboardUI implements Runnable, ActionListener {

    private ChessGame game;
    private SquareButton[][] squareButtons;
    private JPanel boardPanel;
    private JFrame chessFrame;

    public ChessboardUI() {
        SwingUtilities.invokeLater(this);
    }

    public void init() {
        game = new ChessGame();
        this.squareButtons = new SquareButton[Chessboard.NBROW][Chessboard.NBCOL];
        boardPanel = new JPanel();
        this.boardPanel.setLayout(new GridLayout(Chessboard.NBROW, Chessboard.NBCOL));
        for (int i=0;i<Chessboard.NBCOL;++i) {
            for (int j=0;j<Chessboard.NBROW;++j) {
                this.squareButtons[i][j] = new SquareButton(game.getBoard().getSquare(i,j));
                this.squareButtons[i][j].addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        SquareButton squareButton = (SquareButton) actionEvent.getSource();
        Square square = squareButton.getSquare();
        if(this.game.movePiece == null) {
            Piece piece = square.getOccupant();
            if ((piece != null) &&
                    ((this.game.isWhiteTurn && piece.getColor() == Color.WHITE) ||
                            (!this.game.isWhiteTurn && piece.getColor() == Color.BLACK))) {
                this.game.moveSquare = square;
                this.game.movePiece = piece;
                squareButton.setBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY, 3));
                this.boardPanel.validate();
                this.boardPanel.repaint();
            } else {
                System.out.println("Please, select a " + (game.isWhiteTurn ? "white" : "black") + " piece");
            }
        } else {
            try {
                if(square.equals(this.game.moveSquare)) {
                    throw new ForbiddenMoveException("The destination has to be different from the origin");
                }
                if (!this.game.movePiece.checkMove(this.game.moveSquare, square)) {
                    throw new ForbiddenMoveException("Invalid move");
                }
                if(!(this.game.movePiece instanceof Knight) && this.game.collide(this.game.moveSquare,square)){
                    throw new ForbiddenMoveException("There is another piece on the way");
                }
                if((this.game.movePiece instanceof Pawn) && square.getOccupant()!=null &&
                        this.game.moveSquare.getColumn()==square.getColumn()) {
                    throw new ForbiddenMoveException("Pawn only capture diagonally");
                }
                Square from = this.game.moveSquare;
                square.occupy(from.removeOccupant());
                squareButton.paintIcon();
                this.squareButtons[from.getRow()][from.getColumn()].paintIcon();
                this.game.isWhiteTurn = !this.game.isWhiteTurn;
            } catch(ForbiddenMoveException ex) {
                System.out.println("Forbidden move (from "+this.game.moveSquare+" to "+square+"): "+ex.getMessage());
            } finally {
                this.squareButtons[this.game.moveSquare.getRow()][this.game.moveSquare.getColumn()].setBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY, 1));
                this.game.moveSquare = null;
                this.game.movePiece = null;
                this.boardPanel.validate();
                this.boardPanel.repaint();
            }
        }
    }

    public static void main(String[] args) {
        ChessboardUI view = new ChessboardUI();
    }

}
