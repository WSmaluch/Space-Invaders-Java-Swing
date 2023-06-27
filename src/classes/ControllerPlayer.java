package classes;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa reprezentujaca kontroler gracza. Klasa głównie odpowiada za sterowanie graczem, ale też za tworzenie kosmity.
 @author Wojciech Smaluch
 @version 1.0
 */
public class ControllerPlayer extends KeyAdapter {

    private final JLabel player;
    /**
     * Pole klasy Alien.
     */
    protected Alien alien;
    private Player p;
    private final SpaceInvaders sp;
    private Bullet pocisk;
    private int x,y,lastX,lastY;
    private int LocationXAlien;
    private static final ArrayList <Integer> Loc = new ArrayList<>();
    private final ArrayList<Integer> al;


    /**
     * Konstruktor kontrolera gracza.
     * @param p reprezentuje JLabel gracza.
     * @param sp reprezentuje obiekt glowny "SpaceInvaders".
     */
    public ControllerPlayer(JLabel p, SpaceInvaders sp) {
        this.player = p;
        this.sp = sp;
        al = new ArrayList<>();
    }

    /**
     * Zwraca wspolrzedna X gracza.
     * @return wartosc okreslajaca wspolrzedna x gracza.
     */
    public int getX() {
        return x;
    }

    /**
     * Metoda obslugujaca wypisany klawisz.
     * @param e wydarzenie do przetworzenia
     */

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Startuje gre. Tworzy gracza, ustawia go na planszy, zapisuje jego lokalizacje, tworzy kosmite.
     */
    public void startGame()
    {
        p = new Player(player);
        player.setLocation(360,505);
        saveLastXY();
        spawnAlien();
        getLoc().add(getX());
    }

    /**
     * Obsluguje nacisniety klawisz. Glownym zadaniem tej funkcji jest poruszanie rakieta gracza w zaleznosci od wcisnietej strzalki na klawiaturze.
     * @param e wydarzenie do przetworzenia
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        x = player.getX();
        y = player.getY();

        if (key == KeyEvent.VK_LEFT)
        {
            if (player.getX() >-15)
            {
                player.setLocation(x-12,y);
                saveLastXY();
            }
            else if(player.getX() < -30)
            {
                player.setLocation(x+12,y);
                saveLastXY();
            }
        }

        else if(key == KeyEvent.VK_RIGHT)
        {
            if (player.getX() < 720)
            {
                player.setLocation(x+12,y);
                saveLastXY();
            }
            else if (player.getX() > 720)
            {
                player.setLocation(x-12,y);
                saveLastXY();
            }
        }

        else if(key == KeyEvent.VK_UP)
        {
            player.setLocation(x,y-12);
            saveLastXY();
        }

        else if(key == KeyEvent.VK_DOWN)
        {
            if (player.getY() < 505)
            {
                player.setLocation(x, y + 12);
                saveLastXY();
            }
            else if (player.getY() > 510)
            {
                player.setLocation(x,y-12);
                saveLastXY();
            }
        }
        else if (key == KeyEvent.VK_SPACE)
        {
            fire();
        }
    }

    /**
     * Zwraca zmienna X gracza.
     * @return wspolrzedna X gracza.
     */
    public int playerGetX()
    {
        return player.getX();
    }

    /**
     * Zwraca zmienna Y gracza.
     * @return wspolrzedna Y gracza.
     */
    public int playerGetY()
    {
        return player.getY();
    }

    /**
     * Zapisuje ostatnia lokalizacje gracza.
     */
    public void saveLastXY()
    {
        lastX = player.getX();
        lastY = player.getY();
    }

    /**
     * Ustawia gracza na ostatniej zapisanej lokalizacji(wspolrzednych).
     */
    public void moveToLast()
    {
        player.setLocation(lastX,lastY);
    }

    /**
     * Tworzy pocisk i uzywa metody z klasy Bullet.
     */
    private void fire()
    {
        pocisk = new Bullet(sp,this);
        pocisk.moveBullet();
    }

    /**
     * Losuje liczbe, aby kosmita byl tworzony na roznych lokalizacjach
     * @return wspolrzedna x kosmity
     */
    public int randomX()
    {
        int x;
        Random r = new Random();
        x = r.nextInt(730)-220;
        LocationXAlien = x;
        Loc.add(x);
        al.add(x);
        return x ;
    }

    /**
     * Zwraca zmienna X kosmity.
     * @return wspolrzedna X kosmity.
     */
    public int getLocationXAlien() {
        return LocationXAlien;
    }

    /**
     * Tworzy kosmite i uruchamia watek.
     */
    public void spawnAlien()
    {
        alien = new Alien(sp,this);
        Thread thread = new Thread(alien);
        thread.start();

    }

    /**
     * Zwraca ArrayList Loc
     * @return Arrayliste loc
     */
    public ArrayList<Integer> getLoc() {
        return Loc;
    }
}
