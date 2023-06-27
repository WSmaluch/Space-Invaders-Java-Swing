package classes;

import javax.swing.*;
import java.awt.*;
/**
 * Klasa laczaca klasy skladowe. Jest ona sercem aplikacji.
 *  @author Wojciech Smaluch
 *  @version 1.0
 */
public class SpaceInvaders extends JFrame{
    private final Board board;
    /**
     * Pole reprezentujace klase Scoreboard.
     */
    protected Scoreboard scoreboard;

    private final JLabel playerLabel;
    private final JLabel scoreboardLabel;

    private final WindowListener WL = new WindowListener(this);
    /**
     * Pole reprezentujace klase ControllerPlayer.
     */

    protected ControllerPlayer sterownikPlayer;
    /**
     * Pole reprezentujace klase Client.
     */
    protected Client c;
    private final StartPage startPage;

    /**
     * Konstruktor klasy glownej SpaceInvaders. Ustawia opcje aplikacji, tworzy potrzebne obiekty do dzialania aplikacji.
     */

    public SpaceInvaders()
    {
        super("Space Invaders");


        //SETTING ICON OF APLICATION
        Image icon = Toolkit.getDefaultToolkit().getImage("src/resources/alien.png");
        setIconImage(icon);


        //IMPLEMENTATION START PAGE
        startPage = new StartPage(this);


        //IMPLEMENTATION BACKGROUND
        board = new Board(this);


        //SETTING UP JFRAME
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setLayout(new BorderLayout());

        //ADDING WINDOWLISTENER
        addWindowListener(WL);


        //IMPLEMENTATION OF SERVER
        c = new Client(this);


        //IMPLEMENTATION OF PLAYER
        playerLabel = new JLabel();
        sterownikPlayer = new ControllerPlayer(playerLabel,this);
        addKeyListener(sterownikPlayer);

        //IMPLEMENTATION OF SCOREBOARD
        scoreboardLabel = new JLabel();
        scoreboard = new Scoreboard(scoreboardLabel);
        scoreboard.setNickname();
        scoreboard.showOnScreen();



        //CONTINUE OF IMPLEMENTATION OF PLAYER
        add(playerLabel,BorderLayout.SOUTH);
        playerLabel.setLocation(360,505);

        //CONTINUE OF IMPLEMENTATION OF SCOREBOARD
        add(scoreboardLabel,BorderLayout.NORTH);

        //CONTINUE OF SERVER
        c.init();

        //CONTINUE OF STARTPAGE
        startPage.setVisible(true);


        revalidate();
    }

}
