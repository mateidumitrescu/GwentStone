package game;

import cards.Card;

public class InputPlayers {
    private int startingPlayer;
    private Card playerOneHero;
    private Card playerTwoHero;
    private int playerOneDeckIndex;
    private int playerTwoDeckIndex;

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    public Card getPlayerOneHero() {
        return playerOneHero;
    }

    public void setPlayerOneHero(Card playerOneHero) {
        this.playerOneHero = playerOneHero;
    }

    public Card getPlayerTwoHero() {
        return playerTwoHero;
    }

    public void setPlayerTwoHero(Card playerTwoHero) {
        this.playerTwoHero = playerTwoHero;
    }

    public int getPlayerOneDeckIndex() {
        return playerOneDeckIndex;
    }

    public void setPlayerOneDeckIndex(int playerOneDeckIndex) {
        this.playerOneDeckIndex = playerOneDeckIndex;
    }

    public int getPlayerTwoDeckIndex() {
        return playerTwoDeckIndex;
    }

    public void setPlayerTwoDeckIndex(int playerTwoDeckIndex) {
        this.playerTwoDeckIndex = playerTwoDeckIndex;
    }
}
