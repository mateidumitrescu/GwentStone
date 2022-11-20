package game;

public class Action {
    private String command;
    private int handIndex;
    private CardCoordinates cardAttacker;
    private CardCoordinates cardAttacked;
    private int affectedRow;
    private int playerIndex;
    private int x;
    private int y;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getHandIndex() {
        return handIndex;
    }

    public void setHandIndex(int handIndex) {
        this.handIndex = handIndex;
    }

    public CardCoordinates getCardAttacker() {
        return cardAttacker;
    }

    public void setCardAttacker(CardCoordinates cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    public CardCoordinates getCardAttacked() {
        return cardAttacked;
    }

    public void setCardAttacked(CardCoordinates cardAttacked) {
        this.cardAttacked = cardAttacked;
    }

    public int getAffectedRow() {
        return affectedRow;
    }

    public void setAffectedRow(int affectedRow) {
        this.affectedRow = affectedRow;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
