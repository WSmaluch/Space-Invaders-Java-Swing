package classes;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa reprezentujaca kosmite.
 @author Wojciech Smaluch
 @version 1.0
 */
public class Alien implements Runnable{

    private int x,y;
    private final SpaceInvaders sp;
    private final ControllerPlayer cp;
    private boolean alive;
    private boolean stopWork = false;
    private int LocationYAlien;
    private int pointsMultiplier;

    /**
     * Konstruktor kosmity.
     * @param sp reprezentuje obiekt glowny "SpaceInvaders".
     * @param cp reprezentuje obiekt ControllerPlayer.
     */
    public Alien(SpaceInvaders sp,ControllerPlayer cp) {

        this.sp = sp;
        this.cp = cp;
    }

    /**
     * Zwraca wspolrzednia Y kosmity.
     * @return Wspolrzedna Y kosmity.
     */
    public int getLocationYAlien() {
        return LocationYAlien;
    }

    /**
     * Ustawia wspolrzedna Y kosmity.
     * @param locationYAlien wartosc okreslajaca wspolrzedna Y kosmity.
     */
    public void setLocationYAlien(int locationYAlien) {
        LocationYAlien = locationYAlien;
    }

    /**
     * Zwraca zmienna X kosmity.
     * @return wspolrzedna X kosmity.
     */
    public int getX() {
        return x;
    }

    /**
     * Ustawia wartosc okreslajaca stan w jakim jest kosmita.
     * @param alive wartosc okreslajaca czy kosmita zyje - nie zostal trafiony.
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Zwraca mnoznik punktow jakie gracz bedzie zdobywał w trakcie rozgrywki.
     * @return wartosc okreslajaca mnoznik punktow.
     */
    public int getPointsMultiplier() {
        return pointsMultiplier;
    }

    /**
     * W zaleznosci od wybranego poziomu trudnosci ustawia szybkosc poruszania sie kosmity i ustawia mnoznik punktow.
     * @return wartosc wyrazona w milisekundach, szybkosc poruszania sie kosmity.
     */
    public int setDifficult()
    {
        int level = sp.scoreboard.getDifficultLevel();
        if (level == 2)
        {
            pointsMultiplier = 30;
            return 40;
        }
        else if (level == 0)
        {
            pointsMultiplier = 10;
            return 80;
        }
        else
        {
            pointsMultiplier = 20;
            return 60;
        }
    }

    /**
     * Uruchamia watek kosmity - jego wyglad, szybkosc poruszania sie, ruch. Jednoczesnie kontrolujac czy kosmita i gracz nie powoduja kolizji.
     */
    @Override
    public void run() {
        Thread thread = new Thread(() -> {
            JLabel alien;
            x = cp.randomX();
            setAlive(true);

            y = -350;

            alien = new JLabel();

            sp.add(alien);

            ImageIcon iconLogo = new ImageIcon("src/resources/alien.png");


            Image image = iconLogo.getImage();
            Image newimg = image.getScaledInstance(60, 40, Image.SCALE_SMOOTH);
            iconLogo = new ImageIcon(newimg);


            alien.setIcon(iconLogo);

            if (!stopWork) {
                do {
                    try {
                        y = y + 10;
                        alien.setLocation(x + 200, y);
                        setLocationYAlien(y);
                        if (!alive) {
                            alien.setLocation(getX(), 399);
                        }


                        Thread.sleep(setDifficult());
                        if ((cp.playerGetX() - 30) < alien.getX() && alien.getX() < (cp.playerGetX() + 50) && (alien.getY() > (cp.playerGetY() - 260) && (alien.getY() < (cp.playerGetY() - 170)))) {
                            alive = false;

                            lose();

                            stopWork = true;

                        }
                        if (alien.getY() == 400) {
                            lose();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cp.moveToLast();
                } while (y < 400 && !stopWork);
            }
        });
        thread.start();
    }

    /**
     * Metoda, ktora jest wywolywana w momencie przegrania gry - kolizja z rakieta lub gdy Y kosmity bedzie wynosilo 400.
     */
    public void lose()
    {
        sp.c.sendToServer();
        int response = JOptionPane.showConfirmDialog(null, "Your score: " + sp.scoreboard.totalPoints  + " points. Do you want to display leaderboard?" , "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {

            HighScore HSWindow = new HighScore(sp);
            HSWindow.setTitle("Leaderboard");
            HSWindow.setVisible(true);
        }
        if (response == JOptionPane.NO_OPTION) {
            sp.scoreboard.saveToFile();
            System.exit(0);
        }

    }

    /**
     * Metoda zatrzymujaca watek.
     */
    public void stop()
    {
        setAlive(false);
        cp.spawnAlien();
    }
}
