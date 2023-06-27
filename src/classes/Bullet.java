package classes;

import javax.swing.*;
import java.awt.*;
import java.util.ConcurrentModificationException;
/**
 * Klasa reprezentujaca pocisk, ktorym gracz bedzie strzeal.
 @author Wojciech Smaluch
 @version 1.0
 */
public class Bullet {
    private int x,y;
    private final SpaceInvaders sp;
    private final ControllerPlayer cp;
    private ImageIcon iconLogo;


    /**
     * Kontruktor pocisku.
     * @param sp reprezentuje obiekt glowny "SpaceInvaders".
     * @param cp reprezentuje obiekt ControllerPlayer.
     */
    public Bullet(SpaceInvaders sp, ControllerPlayer cp) {
        this.sp = sp;
        this.cp = cp;
        iconLogo = new ImageIcon("src/resources/bullet.png");
    }

    /**
     * Metoda sluzaca do poruszania pocisku. Pobiera aktualne wspolrzedne gracza, nastepnie na ich podstawie ustawia pocisk .Zawiera watek, ktory zmienia wartosc Y pocisku, przez co nadaje mu dynamiki.
     */
    public void moveBullet()
    {
        JLabel bullet;
        x=cp.playerGetX();
        y=cp.playerGetY();
        bullet = new JLabel();
        sp.add(bullet);




        Image image = iconLogo.getImage();
        Image newimg = image.getScaledInstance(40, 30,  java.awt.Image.SCALE_SMOOTH);
        iconLogo = new ImageIcon(newimg);

        bullet.setIcon(iconLogo);


        Thread thread = new Thread(() -> {
            do {

                try {
                    y--;
                    bullet.setLocation(x + 20, y - 250);
                    Thread.sleep(1);

                    if (cp.getLocationXAlien() + 180 < bullet.getX() && cp.getLocationXAlien() + 250 > bullet.getX() && cp.alien.getLocationYAlien() == bullet.getY()) {
                        sp.scoreboard.addPoint(cp.alien.getPointsMultiplier());
                        sp.scoreboard.showOnScreen();
                        cp.alien.stop();
                        cp.moveToLast();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ConcurrentModificationException exa) {
                    System.out.println(exa);
                }
                cp.moveToLast();
            } while (y > -10);
        });
        thread.start();

    }
}
