package game;

import cards.Card;

public class StartInputGame {
    private int startingPlayer;
    private Card playerOneHero;
    private Card playerTwoHero;
    private int playerOneDeckIndex;
    private int playerTwoDeckIndex;

    private int shuffleSeed;

    /**
     *
     * @return shuffle seed to shuffle cards
     */
    public int getShuffleSeed() {
        return shuffleSeed;
    }

    /**
     *
     * @param shuffleSeed set
     */
    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    /**
     *
     * @return starting player index
     */
    public int getStartingPlayer() {
        return startingPlayer;
    }

    /**
     *
     * @param startingPlayer set starting player
     */
    public void setStartingPlayer(final int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    /**
     *
     * @return Card hero for player One
     */
    public Card getPlayerOneHero() {
        return playerOneHero;
    }

    /**
     *
     * @param playerOneHero set
     */
    public void setPlayerOneHero(final Card playerOneHero) {
        this.playerOneHero = playerOneHero;
    }

    /**
     *
     * @return Card hero for player Two
     */
    public Card getPlayerTwoHero() {
        return playerTwoHero;
    }

    /**
     *
     * @param playerTwoHero set
     */
    public void setPlayerTwoHero(final Card playerTwoHero) {
        this.playerTwoHero = playerTwoHero;
    }

    /**
     *
     * @return index for player one deck
     */
    public int getPlayerOneDeckIndex() {
        return playerOneDeckIndex;
    }

    /**
     *
     * @param playerOneDeckIndex set
     */
    public void setPlayerOneDeckIndex(final int playerOneDeckIndex) {
        this.playerOneDeckIndex = playerOneDeckIndex;
    }

    /**
     *
     * @return index for player two deck
     */
    public int getPlayerTwoDeckIndex() {
        return playerTwoDeckIndex;
    }

    /**
     *
     * @param playerTwoDeckIndex set
     */
    public void setPlayerTwoDeckIndex(final int playerTwoDeckIndex) {
        this.playerTwoDeckIndex = playerTwoDeckIndex;
    }
}
