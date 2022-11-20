package game;

import cards.*;
import fileio.*;

import java.util.ArrayList;

public class Game {
    private int countRound;

    public int getCountRound() {
        return countRound;
    }

    private int playerTurn;
    private Player playerOne;
    private Player playerTwo;

    private ArrayList<ArrayList<Card>> Table;

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public ArrayList<GameRound> getGames() {
        return games;
    }

    public void setGames(ArrayList<GameRound> games) {
        this.games = games;
    }

    private ArrayList<GameRound> games;

    public Card returnCard(String name) {
        switch (name) {
            case "Berserker" -> {
                return new Berserker();
            }
            case "Disciple" -> {
                return new Disciple();
            }
            case "Firestorm" -> {
                return new Firestorm();
            }
            case "Goliath" -> {
                return new Goliath();
            }
            case "HeartHound" -> {
                return new HeartHound();
            }
            case "Miraj" -> {
                return new Miraj();
            }
            case "Sentinel" -> {
                return new Sentinel();
            }
            case "TheCursedOne" -> {
                return new TheCursedOne();
            }
            case "TheRipper" -> {
                return new TheRipper();
            }
            case "Warden" -> {
                return new Warden();
            }
            case "Winterfell" -> {
                return new Winterfell();
            }
        }
        return null;
    }


    // set card characteristics from input data
    public void setCardCharacteristics(Card cardTransfered, CardInput cardToTransfer) {
        cardTransfered.setName(cardToTransfer.getName());
        cardTransfered.setColors(cardToTransfer.getColors());
        cardTransfered.setAttackDamage(cardToTransfer.getAttackDamage());
        cardTransfered.setDescription(cardToTransfer.getDescription());
        cardTransfered.setFrozen(false);
        cardTransfered.setHealth(cardToTransfer.getHealth());
        cardTransfered.setMana(cardToTransfer.getMana());
        cardTransfered.setHasAttacked(false);
        cardTransfered.setHasUsedAbility(false);
    }

    // transfer input decks to players
    void transferDecks(DecksInput playerDecks, Player player) {
        ArrayList<ArrayList<CardInput>> decks = playerDecks.getDecks();
        ArrayList<ArrayList<Card>> decksPlayerOne = new ArrayList<>();

        for (int i = 0; i < playerDecks.getNrDecks(); i++) {
            ArrayList<Card> deckTransfered = new ArrayList<>();
            ArrayList<CardInput> deckToTransfer = decks.get(i);

            for (int j = 0; j < playerDecks.getNrCardsInDeck(); j++) {
                CardInput cardToTransfer = deckToTransfer.get(j);
                Card  cardTransfered = returnCard(cardToTransfer.getName());
                setCardCharacteristics(cardTransfered, cardToTransfer);
                deckTransfered.add(cardTransfered);
            }
            decksPlayerOne.add(deckTransfered);
        }
        player.setDecks(decksPlayerOne);
    }

    // set the data from input file to this Game class
    void setDecksForPlayers(Input inputData) {
        DecksInput playerOneDecks = inputData.getPlayerOneDecks();
        DecksInput playerTwoDecks = inputData.getPlayerTwoDecks();

        // setting decks and number of cards
        this.playerOne = new Player();
        this.playerTwo = new Player();
        this.playerOne.setNrDecks(playerOneDecks.getNrDecks());
        this.playerTwo.setNrDecks(playerTwoDecks.getNrDecks());
        this.playerOne.setNrCardsInDeck(playerOneDecks.getNrCardsInDeck());
        this.playerTwo.setNrCardsInDeck(playerTwoDecks.getNrCardsInDeck());

        transferDecks(playerOneDecks,  this.playerOne);
        transferDecks(playerTwoDecks, this.playerTwo);
    }

    void setGameInput(GameInput gameInput) {
        StartGameInput startGameInput = gameInput.getStartGame();

    }
}
