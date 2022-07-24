import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Classe pour implémenter un bouton de l'interface graphique (étend JButton de l'API Swing)
 * Chaque bouton représente une case du plateau et est associé à l'objet Square correspondant.
 */
public class SquareButton extends JButton {

    /** Un objet Square représentant la case du plateau qui est associée à ce bouton */
    private Square square;

    /**
     * Constructeur qui initialise la case associée au bouton à l'aide de l'objet Square fournit en argument
     * Ce constructeur fait appel au constructeur par défaut de la classe JButton
     * Il déclenche également un appel à la méthode paintIcon pour initialiser son rendu graphique
     */
    public SquareButton(Square square) {
        super();
        this.square = square;
        this.paintIcon();
    }

    /**
     * Retourne l'objet Square représentant la case du plateau associé à ce bouton
     */
    public Square getSquare() {
        return this.square;
    }

    /**
     * Configure le rendu graphique du bouton en lui associant une icône à l'effigie de la pièce qui occupe la case, s'il y a lieu.
     * Cette méthode est appelée chaque fois que l'on veut redessiner le bouton, en générale lorsque la pièce qui l'occupe change.
     */
    public void paintIcon() {
        Piece occ = square.getOccupant();
        if(occ != null) { // si la case est occupée par une pièce du jeu, il faut associé au bouton l'icone qui correspond
            String iconFilename = occ.getColor().toString();
            iconFilename += occ.getClass().getSimpleName().toLowerCase();
            try {
                // l'icone est définit dans un fichier du dossier images
                // le nom de chaque icone est constitué du nom de la classe représentant la pièce + sa couleur
                Image img = ImageIO.read(getClass().getResource("images/"+iconFilename+".png"));
                Image resizedImage = img.getScaledInstance(50,50, java.awt.Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(resizedImage));
                this.setMargin(new Insets(1,1,1,1));
            } catch (FileNotFoundException ex) {
                System.out.println("Icon file not found: "+iconFilename);
            } catch (IOException ex) {
                System.out.println("Error while loading file: "+iconFilename);
            }
        } else { // si la pièce est inoccupée le bouton n'est associé à aucune icone
            this.setIcon(null);
        }
        // dessin du plateau en alternant les cases blanches et les cases grises.
        int n = square.getRow()+square.getColumn();
        if(n%2==0) {
            this.setBackground(java.awt.Color.WHITE);
        } else {
            this.setBackground(java.awt.Color.LIGHT_GRAY);
        }
        this.setOpaque(true);
    }
}
