package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/**
 * Klasa reprezentujaca watek serwera.
 *  @author Wojciech Smaluch
 *  @version 1.0
 */
class WatekSerwera extends Thread {

    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private File serverFile;
    private ArrayList<Integer> bestScores;
    private ArrayList<String> bestNicknames;

    /**
     * Konstruktor watku serwera
     * @param s reprezentuje socket serwera
     */
    public WatekSerwera(Socket s) {

        serverFile = new File("src/Server/HighestScores.txt");

        if (!serverFile.exists())
        {
            try {
                System.out.println("Brak pliku - utworzono");
                PrintStream out = new PrintStream(new FileOutputStream("src/Server/HighestScores.txt"));
                out.println("John;10");
                //out.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


        bestScores = new ArrayList<>();
        bestNicknames = new ArrayList<>();
        socket = s;
        try {
            in =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));

            out =
                    new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(socket.getOutputStream())),true);

        } catch (IOException e) {
        }

    }

    /**
     * Zapisuje dane do pliku znajdujacego sie na dysku serwera
     * @param nick reprezentuje nick gracza .
     * @param points reprezentuje wynik gracza
     */
    private void saveToFile(String nick,String points)
    {
        try
        {
            FileWriter fw = new FileWriter(serverFile,true);
            fw.write(nick+";"+points+"\n");
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());

        }
    }

    /**
     * Czyta dane z pliku znajdujacego sie na dysku serwera
     */
    public void readFromFile() {

        System.out.println("Reading file...");
        try  (BufferedReader reader = new BufferedReader(new FileReader(serverFile)))
        {
            String linia;
            while ((linia = reader.readLine()) != null) {

                String[] dane = linia.split(";");
                bestNicknames.add(dane[0]);
                bestScores.add(Integer.parseInt(dane[1]));
            }
        }
        catch (IOException ex)
        {
            System.out.println(ex);
        }
    }

    /**
     * Czyta plik znajdujacy sie na dysku serwera i przesyla dane o graczach do klineta w celu wyswietlenia najlepszych graczy
     */
    public void showHallOfFame()
    {
        readFromFile();

        for (String b: bestNicknames
             ) {
            out.println(bestNicknames+";"+bestScores);
        }

    }

    /**
     * Uruchomenie watku
     */
    public void run() {
        try {
            while (true) {
                String str = in.readLine();
                if (str.equals("DISCONNECT")) break;


                String[] splitedString = str.split(";");

                System.out.println("Player "+splitedString[0]+" just end game with " + splitedString[1] + " points!");
                boolean save = Boolean.parseBoolean(splitedString[2]);

                if (save)
                {
                    saveToFile(splitedString[0],splitedString[1]);
                }
                showHallOfFame();
            }
            System.out.println("Thread ends work");
            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
        }
    }
}

/**
 * Klasa reprezentujaca serwer gry.
 @author Wojciech Smaluch
 @version 1.0
 */
public class SpaceInvadersServer {
    private static int port = 12345;
    private static Socket socket = null;
    private static ServerSocket s = null;

    public static void main(String[] args) {
        try {
            s = new ServerSocket(port);
            System.out.println("Server just started");
            while (true) {

                socket = s.accept();
                System.out.println("Client: " + socket);

                WatekSerwera watek = new WatekSerwera(socket);
                watek.start();

            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        try {
            s.close();
        } catch (Exception e) {
        }

    }
}
