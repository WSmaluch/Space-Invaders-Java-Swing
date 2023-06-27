package classes;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa reprezentujaca klase gracza.
 *  @author Wojciech Smaluch
 *  @version 1.0
 */
public class Player extends JFrame {
    private JLabel p;

    /**
     * Konstruktor gracza. Ustawia jego wyglad.
     * @param player dodaje go do JLabel w klasie glownej.
     */
    public Player(JLabel player) {
        this.p = player;

        ImageIcon iconLogo = new ImageIcon("src/resources/player.png");


        Image image = iconLogo.getImage();
        Image newimg = image.getScaledInstance(80, 60,  java.awt.Image.SCALE_SMOOTH);
        iconLogo = new ImageIcon(newimg);

        player.setIcon(iconLogo);

    }
}
