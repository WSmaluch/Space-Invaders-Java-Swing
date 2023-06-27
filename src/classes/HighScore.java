package classes;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Klasa reprezentujaca okno z najlepszymi wynikami.
 *  @author Wojciech Smaluch
 *  @version 1.0
 */

public class HighScore extends JDialog {

    /**
     * Konstruktor Highscore
     * @param sp reprezentuje obiekt glowny "SpaceInvaders".
     */
    public HighScore(SpaceInvaders sp) {
        sp.scoreboard.saveToFile();
        setSize(340, 280);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2,1));


        ArrayList<ElementOfList> elementArr = new ArrayList<>();

        JLabel maintext = new JLabel("HIGHSCORE",SwingConstants.CENTER);
        maintext.setFont(new Font("Calibri", Font.BOLD, 23));

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(5,1));
        JLabel nickname = new JLabel("Your nickname: "+sp.scoreboard.nickname);
        JLabel score = new JLabel("Your score is: " + sp.scoreboard.totalPoints);
        JPanel hallOfFame = new JPanel();
        hallOfFame.setLayout(new FlowLayout());
        JLabel topNameLabel = new JLabel("TOP 5 PLAYERS",SwingConstants.CENTER);
        topNameLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        JList highList = new JList();
        JScrollPane scrollPane = new JScrollPane(highList);




        try {
            String[] splitted = sp.c.reciveHallOfFame().split(";");



            String[] nicknames = splitted[0].split(",");
            String[] scores = splitted[1].split(",");

            nicknames[0] = nicknames[0].substring(1);
            nicknames[nicknames.length-1] = nicknames[nicknames.length-1].substring(0,nicknames[nicknames.length-1].length() - 1);
            scores[0] = scores[0].substring(1);
            scores[scores.length-1] = scores[scores.length-1].substring(0,scores[scores.length-1].length() - 1);

            for (int i=0;i<nicknames.length;i++)
            {
                String nick = nicknames[i];
                String scc = scores[i];


                if(nick.startsWith(" ") || scc.startsWith(" "))
                {
                    nicknames[i] = nick.substring(1);
                    scores[i] = scc.substring(1);
                }


                ElementOfList e = new ElementOfList(nicknames[i],scores[i]);
                elementArr.add(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        order(elementArr);
        Collections.reverse(elementArr);

        ArrayList<ElementOfList> top5 = new ArrayList<>();

        int elementNumber = Math.min(elementArr.size(), 5);

        for (int i=0;i<elementNumber;i++)
        {
            top5.add(elementArr.get(i));
        }
        highList.setEnabled(false);

        highList.setListData(top5.toArray());

        center.add(maintext);
        center.add(nickname);
        center.add(score);
        center.add(topNameLabel);

        add(center);
        add(scrollPane);
    }

    /**
     * Uklada w kolejnosci rosnacej Arrayliste z elementami listy wg. punktow.
     * @param element ulozony w kolejnosci element.
     */
    public void order(ArrayList<ElementOfList> element) {

        Collections.sort(element, (Comparator) (o1, o2) -> {

            Integer x1 = ((ElementOfList) o1).getScore();
            Integer x2 = ((ElementOfList) o2).getScore();
            int sComp = x1.compareTo((x2));

            if (sComp != 0) {
                return sComp;
            }

            return x1.compareTo(x2);
        });
    }
}
