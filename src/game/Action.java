package game;

import fileio.Coordinates;

public class Action {
    private String command;
    private int handIndex;

    /**
     *
     * @return cardAttacker coordinates
     */
    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    /**
     *
     * @return cardAttacked coordinates
     */
    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    private Coordinates cardAttacker;
    private Coordinates cardAttacked;

    /**
     *
     * @param cardAttacker coorinates
     */
    public void setCardAttacker(final Coordinates cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    /**
     *
     * @param cardAttacked coordinates
     */
    public void setCardAttacked(final Coordinates cardAttacked) {
        this.cardAttacked = cardAttacked;
    }

    private int affectedRow;
    private int playerIndex;
    private int x;
    private int y;

    /**
     *
     * @return command for action
     */
    public String getCommand() {
        return command;
    }

    /**
     *
     * @param command set for action
     */
    public void setCommand(final String command) {
        this.command = command;
    }

    /**
     *
     * @return card index in hand
     */
    public int getHandIndex() {
        return handIndex;
    }

    /**
     *
     * @param handIndex card index
     */
    public void setHandIndex(final int handIndex) {
        this.handIndex = handIndex;
    }


    /**
     *
     * @return affected row index
     */
    public int getAffectedRow() {
        return affectedRow;
    }

    /**
     *
     * @param affectedRow index
     */
    public void setAffectedRow(final int affectedRow) {
        this.affectedRow = affectedRow;
    }

    /**
     *
     * @return player index
     */
    public int getPlayerIndex() {
        return playerIndex;
    }

    /**
     *
     * @param playerIndex set
     */
    public void setPlayerIndex(final int playerIndex) {
        this.playerIndex = playerIndex;
    }

    /**
     *
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @param x set coordinate
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     *
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param y set
     */
    public void setY(final int y) {
        this.y = y;
    }
}
