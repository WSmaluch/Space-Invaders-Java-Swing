package classes;

import javax.swing.*;
import java.net.*;
import java.io.*;
/**
 * Klasa reprezentujaca watek klienta, zajmuje sie odczytem informacji od serwera.
 @author Wojciech Smaluch
 @version 1.0
 */
class WatekKlienta extends Thread {

    /**
     * Konstruktor watku klienta
     * @param socket reprezentuje socket serwera
     */
    public WatekKlienta(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println(e);
        }
    }


}

/**
 * Klasa reprezentujaca klienta, czyli gracza.
 */
public class Client {
    private Socket socket = null;
    private PrintWriter out = null;
    private final SpaceInvaders sp;

    /**
     * Konstruktor klienta.
     * @param sp reprezentuje obiekt glowny "SpaceInvaders".
     */
    public Client(SpaceInvaders sp) {
        this.sp = sp;
    }

    /**
     * Inicjalizuje klienta. Laczy sie z serwerem. Tworzy watek klienta.
     */
    public void init() {
        try {
            String adresSerwera = "127.0.0.1";
            System.out.println("Connecting to server = " + adresSerwera);
            int portSerwera = 12345;
            socket = new Socket(adresSerwera, portSerwera);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Cannot connect with server");
            System.exit(1);
        } catch (Exception e) {
            System.exit(0);
        }

        //Startuję wątek nasłuchujący
        Thread watek = new WatekKlienta(socket);
        watek.start();

    }

    /**
     * Wysyla informacje dotyczaca wyniku koncowego rozgrywki.
     */
    public void sendToServer()
    {
            try {
                out =
                        new PrintWriter(
                                new BufferedWriter(
                                        new OutputStreamWriter(socket.getOutputStream())),
                                true);
            } catch (Exception e) {
                System.out.println(e);
            }

            String info;
            boolean isHigher = sp.scoreboard.totalPoints > sp.scoreboard.showHighestScore();
        info = sp.scoreboard.nickname+";"+sp.scoreboard.totalPoints+";"+isHigher;
                    out.println(info);

        }

    /**
     * Odbiera informacje na temat najlepszych graczy.
     * @return str string zawiera informacje na temat najlepszych graczy - ich nicki i wynik.
     * @throws IOException w przypadku bledu poinformuje uzytkownika.
     */
        public String reciveHallOfFame() throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String str;
            while ((str = in.readLine()) != null) {
                return str;
            }


            in.close();
            return null;
        }
}