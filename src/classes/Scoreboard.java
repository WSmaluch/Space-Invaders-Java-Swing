package classes;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/**
 * Klasa reprezentujaca tablice wynikow.
 *  @author Wojciech Smaluch
 *  @version 1.0
 */
public class Scoreboard extends JFrame {
    private final JLabel score;
    /**
     * Pole przechowujace liczbe punktow zdobyta w rozgrywce.
     */
    protected int totalPoints;
    private ArrayList<Integer> scoresArr;
    /**
     * Pole przechowujace nick gracza.
     */
    protected String nickname;
    private final File scores = new File("src/saves/SpaceInvadersSave.txt");

    private int difficultLevel;

    /**
     * Konstruktor tablicy wynikow.
     * @param score reprezentuje JLabel score z klasy glownej.
     */
    public Scoreboard(JLabel score) {
        this.score = score;
        add(score);
        score.setForeground(Color.WHITE);
        scoresArr = new ArrayList<>();
    }

    /**
     * Odczytuje z pliku najwyzszy wynik gracza i pokazuje go na ekranie wraz z aktualnym wynikiem.
     */
    public void showOnScreen()
    {
        readFromFile();
        //score.setText("Your points: " + totalPoints + "\tYour last score: " + scoresArr.get(scoresArr.size()-1));
        score.setText("Your points: " + totalPoints + "                                  " +
                "             " +
                "                                                                         " +
                "                                                                 Your highest score: " + showHighestScore());
    }

    /**
     * Dodaje punkty.
     * @param pnt wartosc dodanych punktow.
     */
    public void addPoint(int pnt)
    {
        totalPoints += pnt;
    }

    /**
     * Zapisuje wynik do pliku.
     */
    public void saveToFile()
    {
        try
        {
            FileWriter fw = new FileWriter(scores,true);
            fw.write(totalPoints+"\n");
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    /**
     * Odczytuje nick i wyniki gracza.
     */
    public void readFromFile() {

        if (scores.exists() && scores.isFile()) {
            try {
                Scanner s = new Scanner(scores);
                scoresArr = new ArrayList<Integer>();
                String lineOfText = s.nextLine();
                while (s.hasNext()) {
                    scoresArr.add(s.nextInt());
                    s.nextLine();
                }
                s.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * Ustawia nick gracza odczytując go z pliku. Jeżeli go nie znajdzie to wyswietla pole do wpisania nicku.
     */
    public void setNickname() {
        if(scores.exists() && scores.isFile())
        {
            try  (BufferedReader reader = new BufferedReader(new FileReader("src/saves/SpaceInvadersSave.txt")))
            {
                    this.nickname = reader.readLine();
            }
            catch (IOException ex) { System.out.println(ex); }
        }
        else
        {
            this.nickname = (JOptionPane.showInputDialog("Insert your nickname:"));
            try (PrintStream out = new PrintStream(new FileOutputStream("src/saves/SpaceInvadersSave.txt")))
            {
                out.println(nickname);
                out.println(0);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * Zwraca najwiekszy wynik gracza.
     * @return najwiekszy wynik gracza
     */
    public int showHighestScore()
    {
        return Collections.max(scoresArr);
    }

    /**
     * Pokazuje okno z wyborem trudnosci rozgrywki.
     */
    public void setDifficultLevel()
    {
        Object[] options = {"Easy",
                "Medium",
                "Hard"};
        difficultLevel = JOptionPane.showOptionDialog(null,
                "Hello " + nickname + " ! \nChoose your difficult level",
                "",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
    }

    /**
     * Zwraca wybrany poziom trudnosci.
     * @return poziom trudnosci.
     */
    public int getDifficultLevel() {
        return difficultLevel;
    }
}
