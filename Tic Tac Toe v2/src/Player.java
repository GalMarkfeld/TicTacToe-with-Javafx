import java.util.LinkedList;

public class Player {

    public LinkedList<Integer> positions;
    private char character;

    public Player() {
        this.positions = new LinkedList<>();
        this.character = ' ';
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }
}
