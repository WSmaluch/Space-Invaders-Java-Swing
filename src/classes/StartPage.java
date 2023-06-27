package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Klasa reprezentujaca widok startowy.
 *  @author Wojciech Smaluch
 *  @version 1.0
 */
public class StartPage extends JDialog implements ActionListener{
    private final JButton play = new JButton("Play");
    private final JButton howToPlay = new JButton("How to play?");
    private final JButton exit = new JButton("Exit");
    private final SpaceInvaders sp;

    /**
     * Konstruktor klasy StartPage.
     * @param sp reprezentuje obiekt glowny "SpaceInvaders".
     */
    public StartPage(SpaceInvaders sp) {

        setSize(800,600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2,1));
        setUndecorated(true);
        setLayout(null);



        JPanel upperPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        upperPanel.setLayout(new GridLayout(2,1,50,50));
        JLabel przerwa = new JLabel();
        przerwa.setPreferredSize(new Dimension(100,100));
        upperPanel.add(przerwa);

        JLabel title = new JLabel("SPACE INVADERS",SwingConstants.CENTER);
        title.setFont(new Font("MONOSPACED", Font.BOLD, 60));
        title.setForeground(Color.WHITE);
        upperPanel.setBackground(new Color(0,0,0,125));
        upperPanel.add(title);


        Dimension buttonDimension = new Dimension(150,50);
        bottomPanel.setLayout(new GridLayout(3,1,0,10));
        bottomPanel.setBackground(new Color(0,0,0,125));
        play.setPreferredSize(buttonDimension);
        howToPlay.setPreferredSize(buttonDimension);
        exit.setPreferredSize(buttonDimension);
        bottomPanel.add(play);
        bottomPanel.add(howToPlay);
        bottomPanel.add(exit);

        this.sp = sp;


        JLabel backgroundImage = new JLabel("", new ImageIcon("src/resources/background.gif"), JLabel.CENTER);
        backgroundImage.setBounds(0,0,800,600);
        add(backgroundImage);

        backgroundImage.setLayout(new FlowLayout());
        upperPanel.setBounds(100,100,100,100);
        upperPanel.setPreferredSize(new Dimension(700,200));
        backgroundImage.add(upperPanel);
        JLabel d = new JLabel();
        d.setPreferredSize(new Dimension(700,100));
        backgroundImage.add(d);
        backgroundImage.add(bottomPanel);

        play.addActionListener(this);
        howToPlay.addActionListener(this);
        exit.addActionListener(this);
    }

    /**
     * Sluzy do obslugi dzialania przyciskow.
     * @param e wydarzenie do przetworzenia
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == exit) {
            System.exit(0);
        } else if (source == play) {
            sp.scoreboard.setDifficultLevel();
            sp.sterownikPlayer.startGame();
            dispose();
        } else if (source == howToPlay){
            JOptionPane.showMessageDialog(null, "Your task is to shoot the incoming aliens. \nThe number of points and the speed of the opponents' movement \ndepends on the selected difficulty level. \nYou can move with arrows and shoot with the space bar.","How to play?",JOptionPane.PLAIN_MESSAGE);
        }
    }
}