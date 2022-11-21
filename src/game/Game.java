package game;

import cards.*;
import fileio.*;
import interfaces.Hero;

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

    public ArrayList<GamesInput> getGames() {
        return games;
    }

    public void setGames(ArrayList<GamesInput> games) {
        this.games = games;
    }

    private ArrayList<GamesInput> games;

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
    public void transferDecks(DecksInput playerDecks, Player player) {
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
    public void setDecksForPlayers(Input inputData) {
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

    // transfer hero from data
    public Card setHero(CardInput playerHeroToTransfer) {
        Card playerHero = null;
        switch (playerHeroToTransfer.getName()) {
            case "Lord Royce" -> playerHero = new LordRoyce();
            case "Empress Thorina" -> playerHero = new EmpressThorina();
            case "King Mudface" -> playerHero = new KingMudface();
            case "General Kocioraw" -> playerHero = new GeneralKocioraw();
            default -> playerHero = new Card();
        }
        playerHero.setName(playerHeroToTransfer.getName());
        playerHero.setMana(playerHeroToTransfer.getMana());
        playerHero.setAttackDamage(playerHeroToTransfer.getAttackDamage());
        playerHero.setHealth(playerHeroToTransfer.getHealth());
        playerHero.setDescription(playerHeroToTransfer.getDescription());
        playerHero.setColors(playerHeroToTransfer.getColors());
        playerHero.setHasUsedAbility(false);

        return playerHero;

    }

    // transfer startGameInput
    public void setInputOfGame(InputPlayers input, GameInput game) {
        input.setShuffleSeed(game.getStartGame().getShuffleSeed());
        input.setPlayerOneDeckIndex(game.getStartGame().getPlayerOneDeckIdx());
        input.setPlayerTwoDeckIndex(game.getStartGame().getPlayerTwoDeckIdx());
        input.setStartingPlayer(game.getStartGame().getStartingPlayer());
        CardInput playerOneHeroToTransfer = game.getStartGame().getPlayerOneHero();
        CardInput playerTwoHeroToTransfer = game.getStartGame().getPlayerTwoHero();
        Card playerOneHero = setHero(playerOneHeroToTransfer);
        Card playerTwoHero = setHero(playerTwoHeroToTransfer);
        input.setPlayerOneHero(playerOneHero);
        input.setPlayerTwoHero(playerTwoHero);
    }

    // transfer actions from a game
    public void transferActions(ArrayList<Action> gamesActions, GameInput game) {
        Action actionTransferred = new Action();
        for (ActionsInput action : game.getActions()) {
            actionTransferred.setCommand(action.getCommand());
            actionTransferred.setHandIndex(action.getHandIdx());
            actionTransferred.setAffectedRow(action.getAffectedRow());
            actionTransferred.setPlayerIndex(action.getPlayerIdx());
            CardCoordinates cardAttacker = new CardCoordinates(action.getCardAttacker().getX(), action.getCardAttacker().getY());
            CardCoordinates cardAttacked = new CardCoordinates(action.getCardAttacked().getX(), action.getCardAttacked().getY());
            actionTransferred.setCardAttacker(cardAttacker);
            actionTransferred.setCardAttacked(cardAttacked);
        }
    }
    // set games list
    public void setGameInputs(Input inputData) {
        ArrayList<GameInput> games = inputData.getGames();
        ArrayList<GamesInput> gamesStartInputs = new ArrayList<>();

        for (GameInput game : games) {
            // transfer start game data
            GamesInput gameTransferred = new GamesInput();
            InputPlayers input = new InputPlayers();
            setInputOfGame(input, game);
            gameTransferred.setGameStart(input);
            gamesStartInputs.add(gameTransferred);

            // transfer actions
            ArrayList<Action> gamesActions = new ArrayList<>();
            transferActions(gamesActions, game);
            gameTransferred.setActions(gamesActions);
            gamesStartInputs.add(gameTransferred);

        }


    }
}
