package classes;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
/**
 * Klasa reprezentujaca plansze(tablice), tło okna.
 @author Wojciech Smaluch
 @version 1.0
 */
class Board {
    /**
     * Kontruktor planszy
     * @param s reprezentuje obiekt glowny "SpaceInvaders"
     */
    public Board(SpaceInvaders s) {

        try {
            s.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src/resources/background.gif")))));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}