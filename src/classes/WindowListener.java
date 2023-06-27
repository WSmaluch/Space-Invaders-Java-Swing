package classes;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * Klasa nasluchujca okno.
 *  @author Wojciech Smaluch
 *  @version 1.0
 */
public class WindowListener extends WindowAdapter {

    private final SpaceInvaders sp;

    /**
     * Konstruktor nasluchwiacza okna.
     * @param sp reprezentuje obiekt glowny "SpaceInvaders".
     */
    public WindowListener(SpaceInvaders sp) {
        this.sp = sp;
    }

    /**
     * Obsluguje zdrzenie podczas zamkyania aplikacji.
     * @param e the event to be processed
     */
    @Override
    public void windowClosing(WindowEvent e) {
        try {
            sp.scoreboard.saveToFile();
        }catch (NullPointerException ex)
        {
            System.exit(0);
        }
    }




}