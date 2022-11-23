package game;

import cards.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;

import java.sql.SQLOutput;
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

    public void increasePlayerOneWins() {
        Game.playerOneWins += 1;
    }

    public void incresePlayerTwoWins() {
        Game.playerTwoWins += 1;
    }

    public void increaseGamesPlayed() {
        Game.gamesPlayed += 1;
    }

    public static int getPlayerOneWins() {
        return playerOneWins;
    }

    public static void setPlayerOneWins(int playerOneWins) {
        Game.playerOneWins = playerOneWins;
    }

    public static int getPlayerTwoWins() {
        return playerTwoWins;
    }

    public static void setPlayerTwoWins(int playerTwoWins) {
        Game.playerTwoWins = playerTwoWins;
    }

    public static int getGamesPlayed() {
        return gamesPlayed;
    }

    public static void setGamesPlayed(int gamesPlayed) {
        Game.gamesPlayed = gamesPlayed;
    }

    public ArrayList<ArrayList<Card>> getTable() {
        return Table;
    }

    public void setTable(ArrayList<ArrayList<Card>> table) {
        Table = table;
    }

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

    public ArrayList<InputGame> getGames() {
        return games;
    }

    public void setGames(ArrayList<InputGame> games) {
        this.games = games;
    }

    private ArrayList<InputGame> games;

    public Game() {
        games = new ArrayList<>();
    }

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
                Card cardTransfered = returnCard(cardToTransfer.getName());
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

        transferDecks(playerOneDecks, this.playerOne);
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

        return playerHero;

    }

    // transfer startGameInput
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

    // transfer actions from a game
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

    // set games list
    public void setGameInputs(Input inputData) {
        ArrayList<GameInput> games = inputData.getGames();
        this.games = new ArrayList<>();
        for (GameInput game : games) {
            InputGame gameToAdd = new InputGame();
            StartInputGame startInput = new StartInputGame();
            setInputOfGame(startInput, game);
            ArrayList<Action> actions = new ArrayList<>();
            transferActions(actions, game);
            gameToAdd.setGameStart(startInput);
            gameToAdd.setActions(actions);
            this.games.add(gameToAdd);
        }
    }

    // output players decks
    public ObjectNode outputDecksForPlayer(int playerIndex, ArrayList<Card> deckPlayerOne, ArrayList<Card> deckPlayerTwo, String command) {
        ArrayList<Card> deck;
        if (playerIndex == 1) {
            deck = deckPlayerOne;
        } else {
            deck = deckPlayerTwo;
        }

        ObjectNode objNode = (new ObjectMapper()).createObjectNode();

        objNode.put("command", command);
        objNode.put("playerIdx", playerIndex);

        ArrayNode nodes = objNode.putArray("output");
        for (Card card : deck) {
            ObjectNode node = (new ObjectMapper()).createObjectNode();
            node.put("mana", card.getMana());
            if (card.getType().equals("minion")) {
                node.put("attackDamage", card.getAttackDamage());
                node.put("health", card.getHealth());
            }
            node.put("description", card.getDescription());
            nodes.add(node);
            ArrayNode nodeColors = node.putArray("colors");
            for (int color = 0; color < card.getColors().size(); color++) {
                nodeColors.add(card.getColors().get(color));
            }
            node.put("name", card.getName());
        }
        return objNode;
    }

    // output hero of player
    public ObjectNode outputHeroForPlayer(int playerIndex, StartInputGame gameStart) {
        Card hero = null;
        if (playerIndex == 1) {
            hero = gameStart.getPlayerOneHero();
        } else hero = gameStart.getPlayerTwoHero();
        ObjectNode objectNode = (new ObjectMapper().createObjectNode());
        objectNode.put("command", "getPlayerHero");
        objectNode.put("playerIdx", playerIndex);

        ObjectNode nodeOutput = (new ObjectMapper().createObjectNode());
        nodeOutput.put("mana", hero.getMana());
        nodeOutput.put("description", hero.getDescription());
        ArrayNode colorNodes = nodeOutput.putArray("colors");
        for (int i = 0; i < hero.getColors().size(); i++) {
            colorNodes.add(hero.getColors().get(i));
        }
        nodeOutput.put("name", hero.getName());
        nodeOutput.put("health", hero.getHealth());
        JsonNode output = objectNode.put("output", nodeOutput);

        return objectNode;


    }

    // output player turn
    public ObjectNode outputPlayerTurn(int playerTurn) {
        ObjectNode objectNode = (new ObjectMapper().createObjectNode());
        objectNode.put("command", "getPlayerTurn");
        objectNode.put("output", playerTurn);
        return objectNode;
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

    // ending player turn and returning round counter
    public int endPlayerTurn(int countTurns, int countRound,
                             ArrayList<Card> deckPlayerOne, ArrayList<Card> deckPlayerTwo,
                             StartInputGame game) {
        // making all cards on table unfrozen
        if (this.getPlayerTurn() == 1) {
            for (int i = 2; i < 4; i++) {
                for (int j = 0; j < this.getTable().get(i).size(); j++) {
                    this.Table.get(i).get(j).setFrozen(false);
                }

            }
            this.setPlayerTurn(2);
        } else {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < this.getTable().get(i).size(); j++) {
                    this.getTable().get(i).get(j).setFrozen(false);
                }
            }
            this.setPlayerTurn(1);
        }
        if (countTurns % 2 == 1) {
            game.getPlayerOneHero().setHasAttacked(false);
            game.getPlayerTwoHero().setHasAttacked(false);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < this.getTable().get(i).size(); j++) {
                    this.Table.get(i).get(j).setHasAttacked(false);
                }
            }
            countRound++;
            if (countRound <= 10) {
                this.playerOne.addMana(countRound);
                this.playerTwo.addMana(countRound);
            } else {
                this.playerOne.addMana(10);
                this.playerTwo.addMana(10);
            }

            if (!deckPlayerOne.isEmpty()) {
                this.playerOne.getHandCards().add(deckPlayerOne.get(0));
                deckPlayerOne.remove(0);
            }
            if (!deckPlayerTwo.isEmpty()) {
                this.playerTwo.getHandCards().add(deckPlayerTwo.get(0));
                deckPlayerTwo.remove(0);
            }
        }
        return countRound;
    }

    // output for placeCard command if there is an error
    public ObjectNode outputPlaceCardError(int handIndex, String error) {
        ObjectNode objectNode = (new ObjectMapper().createObjectNode());
        objectNode.put("command", "placeCard");
        objectNode.put("handIdx", handIndex);
        objectNode.put("error", error);
        return objectNode;
    }

    // place card on table
    public void placeCard(int handIndex, ArrayNode output) {
        if (this.getPlayerTurn() == 1) {

            Card cardToPlace = playerOne.getHandCards().get(handIndex);
            if (cardToPlace.getType().equals("environment")) {
                output.add(outputPlaceCardError(handIndex, "Cannot place environment card on table."));
            } else if (playerOne.getMana() < cardToPlace.getMana()) {
                output.add(outputPlaceCardError(handIndex, "Not enough mana to place card on table."));
            } else if ((cardToPlace.isFrontRow() && (this.getTable().get(2).size() == 5)) ||
                    (cardToPlace.isBackRow() && (this.getTable().get(3).size() == 5))) {
                output.add(outputPlaceCardError(handIndex, "Cannot place card on table since row is full."));
            } else {
                if (cardToPlace.isFrontRow()) {
                    this.getTable().get(2).add(cardToPlace);
                    if (cardToPlace.isTank()) {
                        playerOne.setTankOnTable(true);
                    }
                    playerOne.getHandCards().remove(handIndex);
                    playerOne.substractMana(cardToPlace.getMana());

                } else if (cardToPlace.isBackRow()) {
                    this.getTable().get(3).add(cardToPlace);
                    playerOne.getHandCards().remove(handIndex);
                    playerOne.substractMana(cardToPlace.getMana());
                }
            }
        } else {
            Card cardToPlace = playerTwo.getHandCards().get(handIndex);
            if (cardToPlace.getType().equals("environment")) {
                output.add(outputPlaceCardError(handIndex, "Cannot place environment card on table."));
            } else if (playerTwo.getMana() < cardToPlace.getMana()) {
                output.add(outputPlaceCardError(handIndex, "Not enough mana to place card on table."));
            } else if ((cardToPlace.isFrontRow() && (this.getTable().get(1).size() == 5)) ||
                    (cardToPlace.isBackRow() && (this.getTable().get(0).size() == 5))) {
                output.add(outputPlaceCardError(handIndex, "Cannot place card on table since row is full."));
            } else {
                if (cardToPlace.isFrontRow()) {
                    this.getTable().get(1).add(cardToPlace);
                    if (cardToPlace.isTank()) {
                        playerTwo.setTankOnTable(true);
                    }
                    playerTwo.getHandCards().remove(handIndex);
                    playerTwo.substractMana(cardToPlace.getMana());
                } else if (cardToPlace.isBackRow()) {
                    this.getTable().get(0).add(cardToPlace);
                    playerTwo.getHandCards().remove(handIndex);
                    playerTwo.substractMana(cardToPlace.getMana());
                }
            }
        }
    }

    // output getCardsInHand
    public ObjectNode outputCardsInHand(int playerIndex) {
        // no need to write the same function again
        return outputDecksForPlayer(playerIndex, playerOne.getHandCards(), playerTwo.getHandCards(), "getCardsInHand");
    }

    // output player's mana
    public ObjectNode outputPlayerMana(int playerIndex, int manaPlayerOne, int manaPlayerTwo) {
        int mana;
        if (playerIndex == 1)
            mana = manaPlayerOne;
        else mana = manaPlayerTwo;
        ObjectNode objectNode = (new ObjectMapper().createObjectNode());
        objectNode.put("command", "getPlayerMana");
        objectNode.put("playerIdx", playerIndex);
        objectNode.put("output", mana);

        return objectNode;
    }

    // output cards on table
    public ObjectNode outputCardsOnTable() {
        ObjectNode objectNode = (new ObjectMapper().createObjectNode());
        objectNode.put("command", "getCardsOnTable");
        ArrayNode nodes = objectNode.putArray("output");
        for (ArrayList<Card> cards : this.getTable()) {
            ArrayNode cardNodes = (new ObjectMapper().createArrayNode());
            for (Card cardToOutput : cards) {
                ObjectNode node = (new ObjectMapper().createObjectNode());
                node.put("mana", cardToOutput.getMana());
                node.put("attackDamage", cardToOutput.getAttackDamage());
                node.put("health", cardToOutput.getHealth());
                node.put("description", cardToOutput.getDescription());
                ArrayNode nodeColors = node.putArray("colors");
                for (int k = 0; k < cardToOutput.getColors().size(); k++) {
                    nodeColors.add(cardToOutput.getColors().get(k));
                }
                node.put("name", cardToOutput.getName());
                cardNodes.add(node);
            }
            nodes.add(cardNodes);
        }
        return objectNode;
    }

    // output environment cards in hand
    public ObjectNode outputEnvironmentCardsInHand(int playerIndex, ArrayList<Card> handPlayerOne, ArrayList<Card> handPlayerTwo) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", "getEnvironmentCardsInHand");
        objectNode.put("playerIdx", playerIndex);
        ArrayNode outputNodes = objectNode.putArray("output");

        if (playerIndex == 1) {
            return getJsonNodes(handPlayerOne, objectNode, outputNodes);
        } else {
            return getJsonNodes(handPlayerTwo, objectNode, outputNodes);
        }

    }

    private ObjectNode getJsonNodes(ArrayList<Card> handPlayerTwo, ObjectNode objectNode, ArrayNode outputNodes) {
        for (Card card : handPlayerTwo) {
            if (card.getType().equals("environment")) {
                ObjectNode node = new ObjectMapper().createObjectNode();
                node.put("mana", card.getMana());
                node.put("description", card.getDescription());
                ArrayNode colorNodes = node.putArray("colors");
                for (int i = 0; i < card.getColors().size(); i++) {
                    colorNodes.add(card.getColors().get(i));
                }
                node.put("name", card.getName());
                outputNodes.add(node);
            }
        }
        return objectNode;
    }

    // handle errors for environment cards
    public ObjectNode outputErrorEnvironmentCard(int affectedRow, int handIndex, String error) {
        ObjectNode objectNode = (new ObjectMapper().createObjectNode());
        objectNode.put("command", "useEnvironmentCard");
        objectNode.put("handIdx", handIndex);
        objectNode.put("affectedRow", affectedRow);
        objectNode.put("error", error);

        return objectNode;
    }

    // use environment card on affected row
    public void useEnvironmentCard(int affectedRow, int handIndex, ArrayNode output) {
        if (this.getPlayerTurn() == 1) {
            if (!this.getPlayerOne().getHandCards().get(handIndex).getType().equals("environment")) {
                output.add(outputErrorEnvironmentCard(affectedRow, handIndex, "Chosen card is not of type environment."));
            } else if (this.getPlayerOne().getMana() < this.getPlayerOne().getHandCards().get(handIndex).getMana()) {
                output.add(outputErrorEnvironmentCard(affectedRow, handIndex, "Not enough mana to use environment card."));
            } else if ((affectedRow != 0) && (affectedRow != 1)) {
                output.add(outputErrorEnvironmentCard(affectedRow, handIndex, "Chosen row does not belong to the enemy."));
            } else {
                String abilityOutput = this.getPlayerOne().getHandCards().get(handIndex).useAbilityEnvironment(affectedRow, this.getTable(), this);
                if (abilityOutput.equals("cannot steal")) {
                    output.add(outputErrorEnvironmentCard(affectedRow, handIndex, "Cannot steal enemy card since the player's row is full."));
                } else if (abilityOutput.equals("ability used")) {
                    this.playerOne.substractMana(this.getPlayerOne().getHandCards().get(handIndex).getMana());
                    this.getPlayerOne().getHandCards().remove(handIndex);
                }
            }

        } else {
            if (!this.getPlayerTwo().getHandCards().get(handIndex).getType().equals("environment")) {
                output.add(outputErrorEnvironmentCard(affectedRow, handIndex, "Chosen card is not of type environment."));
            } else if (this.getPlayerTwo().getMana() < this.getPlayerTwo().getHandCards().get(handIndex).getMana()) {
                output.add(outputErrorEnvironmentCard(affectedRow, handIndex, "Not enough mana to use environment card."));
            } else if ((affectedRow != 2) && (affectedRow != 3)) {
                output.add(outputErrorEnvironmentCard(affectedRow, handIndex, "Chosen row does not belong to the enemy."));
            } else {
                String abilityOutput = this.getPlayerTwo().getHandCards().get(handIndex).useAbilityEnvironment(affectedRow, this.getTable(), this);
                if (abilityOutput.equals("cannot steal")) {
                    output.add(outputErrorEnvironmentCard(affectedRow, handIndex, "Cannot steal enemy card since the player's row is full."));
                } else if (abilityOutput.equals("ability used")) {
                    this.getPlayerTwo().substractMana(this.getPlayerTwo().getHandCards().get(handIndex).getMana());
                    this.getPlayerTwo().getHandCards().remove(handIndex);
                }
            }

        }
    }

    // output card at the given position
    public ObjectNode getCardAtPosition(int x, int y) {
        ObjectNode objectNode = (new ObjectMapper().createObjectNode());
        objectNode.put("command", "getCardAtPosition");
        objectNode.put("x", x);
        objectNode.put("y", y);
        if (y >= this.getTable().get(x).size()) {
            objectNode.put("output", "No card available at that position.");
            return objectNode;
        }
        Card card = this.getTable().get(x).get(y);

        ObjectNode outputNodes = new ObjectMapper().createObjectNode();
        outputNodes.put("mana", card.getMana());
        outputNodes.put("attackDamage", card.getAttackDamage());
        outputNodes.put("health", card.getHealth());
        outputNodes.put("description", card.getDescription());
        ArrayNode colorNodes = outputNodes.putArray("colors");
        for (int i = 0; i < card.getColors().size(); i++) {
            colorNodes.add(card.getColors().get(i));
        }
        outputNodes.put("name", card.getName());
        objectNode.put("output", outputNodes);
        return objectNode;
    }

    // output frozen cards on table
    public ObjectNode getFrozenCardsOnTable() {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", "getFrozenCardsOnTable");
        ArrayNode outputNodes = objectNode.putArray("output");
        for (int i = 0; i < this.getTable().size(); i++) {
            for (int j = 0; j < this.getTable().get(i).size(); j++) {
                Card card = this.getTable().get(i).get(j);
                if (card.isFrozen()) {
                    ObjectNode node = new ObjectMapper().createObjectNode();
                    node.put("mana", card.getMana());
                    node.put("attackDamage", card.getAttackDamage());
                    node.put("health", card.getHealth());
                    node.put("description", card.getDescription());
                    ArrayNode colorNodes = node.putArray("colors");
                    for (int k = 0; k < card.getColors().size(); k++) {
                        colorNodes.add(card.getColors().get(k));
                    }
                    node.put("name", card.getName());
                    outputNodes.add(node);
                }

            }
        }
        return objectNode;
    }

    // output error for attack card
    public ObjectNode outputErrorCardAttack(int xAttacker, int yAttacker, int xAttacked, int yAttacked, String command, String error) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", command);
        ObjectNode attacker = new ObjectMapper().createObjectNode();
        ObjectNode attacked = new ObjectMapper().createObjectNode();
        attacker.put("x", xAttacker);
        attacker.put("y", yAttacker);
        attacked.put("x", xAttacked);
        attacked.put("y", yAttacked);
        objectNode.put("cardAttacker", attacker);
        objectNode.put("cardAttacked", attacked);
        objectNode.put("error", error);
        return objectNode;
    }

    // delete card from table
    public void deleteCardOnTable(int x, int y) {
        Card cardToRemove = this.getTable().get(x).remove(y);
        boolean playerOneHasTank = false;
        boolean playerTwoHasTank = false;
        if (cardToRemove.isTank()) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < this.getTable().get(i).size(); j++) {
                    if (this.getTable().get(i).get(j).isTank()) {
                        this.playerTwo.setTankOnTable(true);
                        playerTwoHasTank = true;
                        break;
                    }
                }
            }
            if (!playerTwoHasTank) {
                this.getPlayerTwo().setTankOnTable(false);
            }
            for (int i = 2; i < 4; i++) {
                for (int j = 0; j < this.getTable().get(i).size(); j++) {
                    if (this.getTable().get(i).get(j).isTank()) {
                        this.playerOne.setTankOnTable(true);
                        playerOneHasTank = true;
                        break;
                    }
                }
            }
            if (!playerOneHasTank) {
                this.getPlayerOne().setTankOnTable(false);
            }
        }
    }

    // card uses attack on another card
    public void cardUsesAttack(ArrayNode output, int xAttacker, int yAttacker, int xAttacked, int yAttacked) {
        if (this.getPlayerTurn() == 1) {
            if (((xAttacked == 2) || (xAttacked == 3)) ||
                    (yAttacked >= this.getTable().get(xAttacked).size())) {
                output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAttack", "Attacked card does not belong to the enemy."));
            } else if (this.getTable().get(xAttacker).get(yAttacker).hasAttacked()) {
                output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAttack", "Attacker card has already attacked this turn."));
            } else if (this.getTable().get(xAttacker).get(yAttacker).isFrozen()) {
                output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAttack", "Attacker card is frozen."));
            } else if (this.getPlayerTwo().isTankOnTable() &&
                    !this.getTable().get(xAttacked).get(yAttacked).isTank()) {
                output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAttack", "Attacked card is not of type 'Tank'."));
            } else {
                this.getTable().get(xAttacked).get(yAttacked).subtractHealth(this.getTable().get(xAttacker).get(yAttacker).getAttackDamage());
                if (this.getTable().get(xAttacked).get(yAttacked).getHealth() <= 0) {
                    deleteCardOnTable(xAttacked, yAttacked);
                }
                this.getTable().get(xAttacker).get(yAttacker).setHasAttacked(true);
            }
        } else {
            if (((xAttacked == 0) || (xAttacked == 1)) ||
                    (yAttacked >= this.getTable().get(xAttacked).size())) {
                output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAttack", "Attacked card does not belong to the enemy."));
            } else if (this.getTable().get(xAttacker).get(yAttacker).hasAttacked()) {
                output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAttack", "Attacker card has already attacked this turn."));
            } else if (this.getTable().get(xAttacker).get(yAttacker).isFrozen()) {
                output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAttack", "Attacker card is frozen."));
            } else if (this.getPlayerOne().isTankOnTable() &&
                    !this.getTable().get(xAttacked).get(yAttacked).isTank()) {
                output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAttack", "Attacked card is not of type 'Tank'."));
            } else {
                this.getTable().get(xAttacked).get(yAttacked).subtractHealth(this.getTable().get(xAttacker).get(yAttacker).getAttackDamage());
                if (this.getTable().get(xAttacked).get(yAttacked).getHealth() <= 0) {
                    deleteCardOnTable(xAttacked, yAttacked);
                }
                this.getTable().get(xAttacker).get(yAttacker).setHasAttacked(true);
            }
        }
    }

    // use ability for card
    public void cardUsesAbility(ArrayNode output, int xAttacker, int yAttacker, int xAttacked, int yAttacked) {
        Card cardUse = this.getTable().get(xAttacker).get(yAttacker);
        if (cardUse.isFrozen()) {
            output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAbility", "Attacker card is frozen."));
            return;
        }
        if (cardUse.hasAttacked()) {
            output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAbility", "Attacker card has already attacked this turn."));
            return;
        }

        if (cardUse.getName().equals("Disciple")) {
            if (this.getPlayerTurn() == 1 && xAttacked != 2 && xAttacked != 3) {
                output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAbility", "Attacked card does not belong to the current player."));
                return;
            }
            if (this.getPlayerTurn() == 2 && xAttacked != 0 && xAttacked != 1) {
                output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAbility", "Attacked card does not belong to the current player."));
                return;
            }
            cardUse.useAbilityMinion(xAttacked, yAttacked, this.getTable(), this);
            return;
        }

        if (this.getPlayerTurn() == 1) {
            if (((xAttacked == 2) || (xAttacked == 3)) ||
                    (yAttacked >= this.getTable().get(xAttacked).size())) {
                output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAbility", "Attacked card does not belong to the enemy."));
            } else if (this.getPlayerTwo().isTankOnTable() &&
                    !this.getTable().get(xAttacked).get(yAttacked).isTank()) {
                output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAbility", "Attacked card is not of type 'Tank'."));
            } else {
                cardUse.useAbilityMinion(xAttacked, yAttacked, this.getTable(), this);
            }
            return;
        }

        if (((xAttacked == 0) || (xAttacked == 1))) {
            output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAbility", "Attacked card does not belong to the enemy."));
        } else if (this.getPlayerOne().isTankOnTable() &&
                !this.getTable().get(xAttacked).get(yAttacked).isTank()) {
            output.add(outputErrorCardAttack(xAttacker, yAttacker, xAttacked, yAttacked, "cardUsesAbility", "Attacked card is not of type 'Tank'."));
        } else {
            cardUse.useAbilityMinion(xAttacked, yAttacked, this.getTable(), this);
        }
    }

    // output hero attack error
    public ObjectNode outputErrorHeroAttack(int x, int y, String error) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", "useAttackHero");
        ObjectNode cardAttacker = new ObjectMapper().createObjectNode();
        cardAttacker.put("x", x);
        cardAttacker.put("y", y);
        objectNode.put("cardAttacker", cardAttacker);
        objectNode.put("error", error);
        return objectNode;
    }

    // output game ended
    public ObjectNode gameEnded(int winner) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        if (winner == 1) {
            objectNode.put("gameEnded", "Player one killed the enemy hero.");
        } else {
            objectNode.put("gameEnded", "Player two killed the enemy hero.");

        }
        return objectNode;
    }

    // use attack on hero
    public void useAttackHero(ArrayNode output, int xAttacker, int yAttacker, StartInputGame hero) {
        Card cardAttacker = this.getTable().get(xAttacker).get(yAttacker);
        if (cardAttacker.isFrozen()) {
            output.add(outputErrorHeroAttack(xAttacker, yAttacker, "Attacker card is frozen."));
        } else if (cardAttacker.hasAttacked()) {
            output.add(outputErrorHeroAttack(xAttacker, yAttacker, "Attacker card has already attacked this turn."));
        } else if (this.getPlayerTurn() == 1) {
            if (this.getPlayerTwo().isTankOnTable()) {
                output.add(outputErrorHeroAttack(xAttacker, yAttacker, "Attacked card is not of type 'Tank'."));
            } else {
                hero.getPlayerTwoHero().subtractHealth(cardAttacker.getAttackDamage());
                if (hero.getPlayerTwoHero().getHealth() <= 0) {
                    output.add(gameEnded(1));
                }
                cardAttacker.setHasAttacked(true);
            }
        } else {
            if (this.getPlayerOne().isTankOnTable()) {
                output.add(outputErrorHeroAttack(xAttacker, yAttacker, "Attacked card is not of type 'Tank'."));
            } else {
                hero.getPlayerOneHero().subtractHealth(cardAttacker.getAttackDamage());
                if (hero.getPlayerOneHero().getHealth() <= 0) {
                    output.add(gameEnded(2));
                }
                cardAttacker.setHasAttacked(true);
            }
        }
    }

    // output use hero ability errors
    public ObjectNode outputHeroAbilityError(int affectedRow, String error) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", "useHeroAbility");
        objectNode.put("affectedRow", affectedRow);
        objectNode.put("error", error);

        return objectNode;
    }

    // use hero ability on affected row
    public void useHeroAbility(ArrayNode output, int affectedRow, StartInputGame game) {
        Card heroPlayerOne = game.getPlayerOneHero();
        Card heroPlayerTwo = game.getPlayerTwoHero();
        System.out.println(this.getPlayerTurn());
        if (this.getPlayerTurn() == 1) {
            if (heroPlayerOne.getMana() > this.getPlayerOne().getMana()) {
                output.add(outputHeroAbilityError(affectedRow, "Not enough mana to use hero's ability."));
                return;
            }
            if (heroPlayerOne.hasAttacked()) {
                output.add(outputHeroAbilityError(affectedRow, "Hero has already attacked this turn."));
                return;
            }
            if (heroPlayerOne.getName().equals("Lord Royce") || heroPlayerOne.getName().equals("Empress Thorina")) {
                if (affectedRow == 2 || affectedRow == 3) {
                    output.add(outputHeroAbilityError(affectedRow, "Selected row does not belong to the enemy."));
                    return;
                } else {
                    heroPlayerOne.useAbilityHero(affectedRow, this.getTable(), this);
                    return;
                }
            }
            if (heroPlayerOne.getName().equals("General Kocioraw") || heroPlayerOne.getName().equals("King Mudface")) {
                if (affectedRow == 0 || affectedRow == 1) {
                    output.add(outputHeroAbilityError(affectedRow, "Selected row does not belong to the current player."));
                    return;
                } else {
                    heroPlayerOne.useAbilityHero(affectedRow, this.getTable(), this);
                    return;
                }
            }
        }
        if (this.getPlayerTurn() == 2) {
            if (heroPlayerTwo.getMana() > this.getPlayerTwo().getMana()) {
                output.add(outputHeroAbilityError(affectedRow, "Not enough mana to use hero's ability."));
                return;
            }
            if (heroPlayerTwo.hasAttacked()) {
                output.add(outputHeroAbilityError(affectedRow, "Hero has already attacked this turn."));
                return;
            }
            if (heroPlayerTwo.getName().equals("Lord Royce") || heroPlayerTwo.getName().equals("Empress Thorina")) {
                if (affectedRow == 0 || affectedRow == 1) {
                    output.add(outputHeroAbilityError(affectedRow, "Selected row does not belong to the enemy."));
                    return;
                } else {
                    heroPlayerTwo.useAbilityHero(affectedRow, this.getTable(), this);
                }
            }
            if (heroPlayerTwo.getName().equals("General Kocioraw") || heroPlayerTwo.getName().equals("King Mudface")) {
                if (affectedRow == 2 || affectedRow == 3) {
                    output.add(outputHeroAbilityError(affectedRow, "Selected row does not belong to the current player."));
                    return;
                } else {
                    heroPlayerTwo.useAbilityHero(affectedRow, this.getTable(), this);
                    return;
                }
            }
        }
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
        int ct = 1;
        for (Action action : game.getActions()) {
            System.out.println(ct + " " + action.getCommand());
            ct++;
            switch (action.getCommand()) {
                case "getPlayerDeck" ->
                        output.add(outputDecksForPlayer(action.getPlayerIndex(), deckPlayerOne, deckPlayerTwo, "getPlayerDeck"));
                case "getPlayerHero" -> output.add(outputHeroForPlayer(action.getPlayerIndex(), game.getGameStart()));
                case "getPlayerTurn" -> output.add(outputPlayerTurn(this.getPlayerTurn()));
                case "endPlayerTurn" -> {
                    countTurns++;
                    countRound = endPlayerTurn(countTurns, countRound, deckPlayerOne, deckPlayerTwo, game.getGameStart());

                }
                case "placeCard" -> placeCard(action.getHandIndex(), output);
                case "getCardsInHand" -> output.add(outputCardsInHand(action.getPlayerIndex()));
                case "getPlayerMana" -> output.add(outputPlayerMana(action.getPlayerIndex(), this.getPlayerOne().getMana(), this.getPlayerTwo().getMana()));
                case "getCardsOnTable" -> output.add(outputCardsOnTable());
                case "getEnvironmentCardsInHand" -> output.add(outputEnvironmentCardsInHand(action.getPlayerIndex(),
                        this.getPlayerOne().getHandCards(), this.getPlayerTwo().getHandCards()));
                case "useEnvironmentCard" -> useEnvironmentCard(action.getAffectedRow(), action.getHandIndex(), output);
                case "getCardAtPosition" -> output.add(getCardAtPosition(action.getX(), action.getY()));
                case "getFrozenCardsOnTable" -> output.add(getFrozenCardsOnTable());
                case "cardUsesAttack" -> cardUsesAttack(output, action.getCardAttacker().getX(), action.getCardAttacker().getY(),
                        action.getCardAttacked().getX(), action.getCardAttacked().getY());
                case "cardUsesAbility" -> cardUsesAbility(output, action.getCardAttacker().getX(), action.getCardAttacker().getY(),
                        action.getCardAttacked().getX(), action.getCardAttacked().getY());
                case "useAttackHero" -> useAttackHero(output, action.getCardAttacker().getX(), action.getCardAttacker().getY(), game.getGameStart());
                case "useHeroAbility" -> useHeroAbility(output, action.getAffectedRow(), game.getGameStart());
            }
        }
    }

    public void startGames(ArrayNode output) {
        for (InputGame game : games) {
            ArrayList<Card> deckPlayerOne = playerOne.getDecks().get(game.getGameStart().getPlayerOneDeckIndex());
            ArrayList<Card> deckPlayerTwo = playerTwo.getDecks().get(game.getGameStart().getPlayerTwoDeckIndex());
            shuffleDecks(deckPlayerOne, deckPlayerTwo, game);

            playerOne.setMana(1);
            playerTwo.setMana(1);
            if (game.getGameStart().getStartingPlayer() == 1) {
                this.setPlayerTurn(1);
            } else if (game.getGameStart().getStartingPlayer() == 2) {
                this.setPlayerTurn(2);
            }
            playerOne.getHandCards().add(deckPlayerOne.get(0));
            deckPlayerOne.remove(0);
            playerTwo.getHandCards().add(deckPlayerTwo.get(0));
            deckPlayerTwo.remove(0);
            // starting actions iterations
            startActions(game, output, deckPlayerOne, deckPlayerTwo);
        }
    }
}

