package classes;
/**
 * Klasa reprezentujaca element listy.
 @author Wojciech Smaluch
 @version 1.0
 */
public class ElementOfList {
    private final String nick;
    private final Integer score;

    /**
     * Konstruktor elementu listy.
     * @param nick reprezentuje nick gracza
     * @param score reprezentuje punkty gracza
     */
    public ElementOfList(String nick, String score) {
        this.nick = nick;
        this.score = Integer.parseInt(score);
    }

    /**
     * Zwraca nick elementu listy
     * @return nick elementu listy
     */
    public String getNick() {
        return nick;
    }

    /**
     * Zwraca punkty elementu listy
     * @return punkty elemntu listy
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Nadpisuje metode toString, tak aby mozna bylo odczytac nick i punkty
     * @return zwraca nick i punkty gracza
     */

    @Override
    public String toString() {
        return getNick() + " " + getScore();
    }

}
