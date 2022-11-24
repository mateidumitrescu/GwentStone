package game;

import cards.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {

    private int playerTurn;
    private Player playerOne;
    private Player playerTwo;

    private static int playerOneWins;
    private static int playerTwoWins;
    private static int gamesPlayed;

    /**
     * increase wins of player one
     */
    public void increasePlayerOneWins() {
        Game.playerOneWins += 1;
    }

    /**
     * increase wins of player two
     */
    public void incresePlayerTwoWins() {
        Game.playerTwoWins += 1;
    }

    /**
     * increase the number of games played
     */
    public void increaseGamesPlayed() {
        Game.gamesPlayed += 1;
    }

    /**
     *
     * @return player one nr of wins
     */
    public int getPlayerOneWins() {
        return playerOneWins;
    }

    /**
     *
     * @param playerOneWins set
     */
    public static void setPlayerOneWins(int playerOneWins) {
        Game.playerOneWins = playerOneWins;
    }

    /**
     *
     * @return player two number of wins
     */
    public int getPlayerTwoWins() {
        return playerTwoWins;
    }

    /**
     *
     * @param playerTwoWins set
     */
    public static void setPlayerTwoWins(int playerTwoWins) {
        Game.playerTwoWins = playerTwoWins;
    }

    /**
     *
     * @return nr of games played
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     *
     * @param gamesPlayed set
     */
    public static void setGamesPlayed(int gamesPlayed) {
        Game.gamesPlayed = gamesPlayed;
    }

    /**
     *
     * @return table of cards
     */
    public ArrayList<ArrayList<Card>> getTable() {
        return Table;
    }

    private ArrayList<ArrayList<Card>> Table;

    /**
     *
     * @return player turn
     */
    public int getPlayerTurn() {
        return playerTurn;
    }

    /**
     *
     * @param playerTurn set
     */
    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    /**
     *
     * @return player one object
     */
    public Player getPlayerOne() {
        return playerOne;
    }

    /**
     *
     * @return player two object
     */
    public Player getPlayerTwo() {
        return playerTwo;
    }

    /**
     *
     * @return games played
     */
    public ArrayList<InputGame> getGames() {
        return games;
    }

    /**
     *
     * @param games set
     */
    public void setGames(ArrayList<InputGame> games) {
        this.games = games;
    }

    private ArrayList<InputGame> games;

    /**
     * constructor
     */
    public Game() {
        this.increaseGamesPlayed();
    }

    /**
     *
     * @param name of card
     * @return card created
     */
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
            case "Heart Hound" -> {
                return new HeartHound();
            }
            case "Miraj" -> {
                return new Miraj();
            }
            case "Sentinel" -> {
                return new Sentinel();
            }
            case "The Cursed One" -> {
                return new TheCursedOne();
            }
            case "The Ripper" -> {
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


    /**
     *
     * @param cardTransfered info
     * @param cardToTransfer info
     */
    public void setCardCharacteristics(Card cardTransfered, CardInput cardToTransfer) {
        cardTransfered.setName(cardToTransfer.getName());
        cardTransfered.setColors(cardToTransfer.getColors());
        cardTransfered.setAttackDamage(cardToTransfer.getAttackDamage());
        cardTransfered.setDescription(cardToTransfer.getDescription());
        cardTransfered.setFrozen(false);
        cardTransfered.setHealth(cardToTransfer.getHealth());
        cardTransfered.setMana(cardToTransfer.getMana());
        cardTransfered.setHasAttacked(false);
    }

    /**
     *
     * @param playerDecks -
     * @param player -
     */
    public void transferDecks(DecksInput playerDecks, Player player) {
        ArrayList<ArrayList<CardInput>> decks = playerDecks.getDecks();
        ArrayList<ArrayList<Card>> decksPlayerOne = new ArrayList<>();

        for (int i = 0; i < playerDecks.getNrDecks(); i++) {
            ArrayList<Card> deckTransfered = new ArrayList<>();
            ArrayList<CardInput> deckToTransfer = decks.get(i);

            for (int j = 0; j < playerDecks.getNrCardsInDeck(); j++) {
                CardInput cardToTransfer = deckToTransfer.get(j);
                Card cardTransfered = returnCard(cardToTransfer.getName());
                setCardCharacteristics(cardTransfered, cardToTransfer);
                deckTransfered.add(cardTransfered);
            }
            decksPlayerOne.add(deckTransfered);
        }
        player.setDecks(decksPlayerOne);
    }

    /**
     *
     * @param inputData info
     */
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

        transferDecks(playerOneDecks, this.playerOne);
        transferDecks(playerTwoDecks, this.playerTwo);
    }

    /**
     *
     * @param playerHeroToTransfer card
     * @return hero transfered
     */
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

        return playerHero;

    }

    /**
     *
     * @param input info
     * @param game info
     */
    public void setInputOfGame(StartInputGame input, GameInput game) {
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
        input.getPlayerOneHero().setHealth(30);
        input.getPlayerTwoHero().setHealth(30);
    }

    /**
     *
     * @param gamesActions actions of a game
     * @param game info
     */
    public void transferActions(ArrayList<Action> gamesActions, GameInput game) {
        for (ActionsInput action : game.getActions()) {
            Action actionTransferred = new Action();
            actionTransferred.setCommand(action.getCommand());
            actionTransferred.setHandIndex(action.getHandIdx());
            actionTransferred.setAffectedRow(action.getAffectedRow());
            actionTransferred.setPlayerIndex(action.getPlayerIdx());
            actionTransferred.setCardAttacked(action.getCardAttacked());
            actionTransferred.setCardAttacker(action.getCardAttacker());
            actionTransferred.setX(action.getX());
            actionTransferred.setY(action.getY());
            gamesActions.add(actionTransferred);
        }

    }

    /**
    public InputGame setGameInputs(GameInput game) {
        InputGame gameToAdd = new InputGame();
        StartInputGame startInput = new StartInputGame();
        setInputOfGame(startInput, game);
        ArrayList<Action> actions = new ArrayList<>();
        transferActions(actions, game);
        gameToAdd.setGameStart(startInput);
        gameToAdd.setActions(actions);
        return gameToAdd;
    }

    // shuffle decks for players
    public void shuffleDecks(ArrayList<Card> deckPlayerOne, ArrayList<Card> deckPlayerTwo, InputGame game) {
        Random rndObj = new Random();
        rndObj.setSeed(game.getGameStart().getShuffleSeed());
        Collections.shuffle(deckPlayerOne, rndObj);

        Random randObj2 = new Random();
        randObj2.setSeed(game.getGameStart().getShuffleSeed());
        Collections.shuffle(deckPlayerTwo, randObj2);
    }

    // get player one total wins
    public ObjectNode outputPlayerWins(String command) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", command);
        if (command.equals("getPlayerOneWins")) {
            objectNode.put("output", this.getPlayerOneWins());
        } else {
            objectNode.put("output", this.getPlayerTwoWins());
        }
        return objectNode;
    }
    // get total games played
    public ObjectNode outputTotalGamesPlayed() {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", "getTotalGamesPlayed");
        objectNode.put("output", this.getGamesPlayed());
        return objectNode;
    }

    // iterate through actions
    public void startActions(InputGame game,
                             ArrayNode output,
                             ArrayList<Card> deckPlayerOne, ArrayList<Card> deckPlayerTwo) {
        this.Table = new ArrayList<>(4); // new table for game
        for (int i = 0; i < 4; i++) {
            ArrayList<Card> columns = new ArrayList<>();
            this.getTable().add(i, columns);
        }

        int countTurns = 1;
        int countRound = 1;
        for (Action action : game.getActions()) {
            ActionHandler actionHandler = new ActionHandler();
            switch (action.getCommand()) {
                case "getPlayerDeck" ->
                        output.add(actionHandler.outputDecksForPlayer(action.getPlayerIndex(), deckPlayerOne, deckPlayerTwo, "getPlayerDeck"));
                case "getPlayerHero" -> output.add(actionHandler.outputHeroForPlayer(action.getPlayerIndex(), game.getGameStart()));
                case "getPlayerTurn" -> output.add(actionHandler.outputPlayerTurn(this.getPlayerTurn()));
                case "endPlayerTurn" -> {
                    countTurns++;
                    countRound = actionHandler.endPlayerTurn(countTurns, countRound, deckPlayerOne, deckPlayerTwo, game.getGameStart(), this);

                }
                case "placeCard" -> actionHandler.placeCard(action.getHandIndex(), output, this);
                case "getCardsInHand" -> output.add(actionHandler.outputCardsInHand(action.getPlayerIndex(), this.getPlayerOne(), this.getPlayerTwo()));
                case "getPlayerMana" -> output.add(actionHandler.outputPlayerMana(action.getPlayerIndex(), this.getPlayerOne().getMana(), this.getPlayerTwo().getMana()));
                case "getCardsOnTable" -> output.add(actionHandler.outputCardsOnTable(this));
                case "getEnvironmentCardsInHand" -> output.add(actionHandler.outputEnvironmentCardsInHand(action.getPlayerIndex(),
                        this.getPlayerOne().getHandCards(), this.getPlayerTwo().getHandCards()));
                case "useEnvironmentCard" -> actionHandler.useEnvironmentCard(action.getAffectedRow(), action.getHandIndex(), output, this);
                case "getCardAtPosition" -> output.add(actionHandler.getCardAtPosition(action.getX(), action.getY(), this));
                case "getFrozenCardsOnTable" -> output.add(actionHandler.getFrozenCardsOnTable(this));
                case "cardUsesAttack" -> actionHandler.cardUsesAttack(output, action.getCardAttacker().getX(), action.getCardAttacker().getY(),
                        action.getCardAttacked().getX(), action.getCardAttacked().getY(), this);
                case "cardUsesAbility" -> actionHandler.cardUsesAbility(output, action.getCardAttacker().getX(), action.getCardAttacker().getY(),
                        action.getCardAttacked().getX(), action.getCardAttacked().getY(), this);
                case "useAttackHero" -> actionHandler.useAttackHero(output, action.getCardAttacker().getX(), action.getCardAttacker().getY(), game.getGameStart(), this);
                case "useHeroAbility" -> actionHandler.useHeroAbility(output, action.getAffectedRow(), game.getGameStart(), this);
                case "getTotalGamesPlayed" -> output.add(outputTotalGamesPlayed());
                case "getPlayerOneWins", "getPlayerTwoWins" -> output.add(outputPlayerWins(action.getCommand()));
            }
        }
    }

    public void startGames(ArrayNode output, InputGame gamePlayed) {
        ArrayList<Card> deckPlayerOne = playerOne.getDecks().get(gamePlayed.getGameStart().getPlayerOneDeckIndex());
        ArrayList<Card> deckPlayerTwo = playerTwo.getDecks().get(gamePlayed.getGameStart().getPlayerTwoDeckIndex());
        shuffleDecks(deckPlayerOne, deckPlayerTwo, gamePlayed);

        playerOne.setMana(1);
        playerTwo.setMana(1);
        if (gamePlayed.getGameStart().getStartingPlayer() == 1) {
            this.setPlayerTurn(1);
        } else if (gamePlayed.getGameStart().getStartingPlayer() == 2) {
            this.setPlayerTurn(2);
        }
        playerOne.getHandCards().add(deckPlayerOne.get(0));
        deckPlayerOne.remove(0);
        playerTwo.getHandCards().add(deckPlayerTwo.get(0));
        deckPlayerTwo.remove(0);
        // starting actions iterations
        startActions(gamePlayed, output, deckPlayerOne, deckPlayerTwo);
    }
}

