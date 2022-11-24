package game;

import cards.Card;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class ActionHandler {

    /**
     * output all decks of a player
     * @param playerIndex which player
     * @param deckPlayerOne player one deck
     * @param deckPlayerTwo player two deck
     * @param command for "command" in output
     * @return objectnode to add to output
     */
    public ObjectNode outputDecksForPlayer(final int playerIndex,
                                           final ArrayList<Card> deckPlayerOne,
                                           final ArrayList<Card> deckPlayerTwo,
                                           final String command) {
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

    /**
     * output cards in hand of a player
     * @param playerIndex player to output
     * @param playerOne
     * @param playerTwo
     * @return objectnode to add to output
     */
    public ObjectNode outputCardsInHand(final int playerIndex,
                                        final Player playerOne,
                                        final Player playerTwo) {
        // no need to write the same function again
        return outputDecksForPlayer(playerIndex,
                playerOne.getHandCards(),
                playerTwo.getHandCards(), "getCardsInHand");
    }

    /**
     *
     * @param playerIndex player
     * @param gameStart game input
     * @return objectnode to output
     */
    public ObjectNode outputHeroForPlayer(final int playerIndex,
                                          final StartInputGame gameStart) {
        Card hero;
        if (playerIndex == 1) {
            hero = gameStart.getPlayerOneHero();
        } else {
            hero = gameStart.getPlayerTwoHero();
        }
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

    /**
     *
     * @param playerTurn
     * @return objectnode to add to output
     */
    public ObjectNode outputPlayerTurn(final int playerTurn) {
        ObjectNode objectNode = (new ObjectMapper().createObjectNode());
        objectNode.put("command", "getPlayerTurn");
        objectNode.put("output", playerTurn);
        return objectNode;
    }

    /**
     *
     * @param countTurns of players in game
     * @param countRound in game
     * @param deckPlayerOne deck
     * @param deckPlayerTwo deck
     * @param game input of game
     * @param gamePlayed
     * @return round counter
     */
    public int endPlayerTurn(final int countTurns, int countRound,
                             final ArrayList<Card> deckPlayerOne,
                             final ArrayList<Card> deckPlayerTwo,
                             final StartInputGame game,
                             final Game gamePlayed) {
        Constant helper = new Constant();
        // making all cards on table unfrozen
        if (gamePlayed.getPlayerTurn() == 1) {
            for (int i = 2; i < helper.getMaxRowPlayerOne(); i++) {
                for (int j = 0; j < gamePlayed.getTable().get(i).size(); j++) {
                    gamePlayed.getTable().get(i).get(j).setFrozen(false);
                }

            }
            gamePlayed.setPlayerTurn(2);
        } else {
            for (int i = 0; i < helper.getMaxRowPlayerTwo(); i++) {
                for (int j = 0; j < gamePlayed.getTable().get(i).size(); j++) {
                    gamePlayed.getTable().get(i).get(j).setFrozen(false);
                }
            }
            gamePlayed.setPlayerTurn(1);
        }
        if (countTurns % 2 == 1) {
            game.getPlayerOneHero().setHasAttacked(false);
            game.getPlayerTwoHero().setHasAttacked(false);
            for (int i = 0; i < helper.getMaxRowPlayerOne(); i++) {
                for (int j = 0; j < gamePlayed.getTable().get(i).size(); j++) {
                    gamePlayed.getTable().get(i).get(j).setHasAttacked(false);
                }
            }
            countRound++;
            if (countRound <= helper.getMaxManaToGet()) {
                gamePlayed.getPlayerOne().addMana(countRound);
                gamePlayed.getPlayerTwo().addMana(countRound);
            } else {
                gamePlayed.getPlayerOne().addMana(helper.getMaxManaToGet());
                gamePlayed.getPlayerTwo().addMana(helper.getMaxManaToGet());
            }

            if (!deckPlayerOne.isEmpty()) {
                ArrayList<Card> hand = gamePlayed.getPlayerOne().getHandCards();
                hand.add(deckPlayerOne.get(helper.getFirstIndex()));
                deckPlayerOne.remove(helper.getFirstIndex());
            }
            if (!deckPlayerTwo.isEmpty()) {
                ArrayList<Card> hand = gamePlayed.getPlayerTwo().getHandCards();
                hand.add(deckPlayerTwo.get(helper.getFirstIndex()));
                deckPlayerTwo.remove(helper.getFirstIndex());
            }
        }
        return countRound;
    }

    /**
     * place card on table
     * @param handIndex card
     * @param output to add objectnode
     * @param gamePlayed current game
     */
    public void placeCard(final int handIndex,
                          final ArrayNode output,
                          final Game gamePlayed) {
        ErrorHandler handler = new ErrorHandler();
        Constant helper = new Constant();
        if (gamePlayed.getPlayerTurn() == 1) {
            Card cardToPlace =
                    gamePlayed.getPlayerOne().getHandCards().get(handIndex);
            if (cardToPlace.getType().equals("environment")) {
                output.add(handler.outputPlaceCardError(handIndex,
                        "Cannot place environment card on table."));
            } else if (gamePlayed.getPlayerOne().getMana() < cardToPlace.getMana()) {
                output.add(handler.outputPlaceCardError(handIndex,
                        "Not enough mana to place card on table."));
            } else if ((cardToPlace.isFrontRow()
                    && (gamePlayed.getTable().get(2).size() == helper.getMaxCardsOnRow()))
                    || (cardToPlace.isBackRow()
                    && (gamePlayed.getTable().get(helper.getReverseRow3()).size()
                    == helper.getMaxCardsOnRow()))) {
                output.add(handler.outputPlaceCardError(handIndex,
                        "Cannot place card on table since row is full."));
            } else {
                if (cardToPlace.isFrontRow()) {
                    gamePlayed.getTable().get(2).add(cardToPlace);
                    if (cardToPlace.isTank()) {
                        gamePlayed.getPlayerOne().setTankOnTable(true);
                    }
                    gamePlayed.getPlayerOne().getHandCards().remove(handIndex);
                    gamePlayed.getPlayerOne().subtractMana(cardToPlace.getMana());

                } else if (cardToPlace.isBackRow()) {
                    gamePlayed.getTable().get(helper.getReverseRow3()).add(cardToPlace);
                    gamePlayed.getPlayerOne().getHandCards().remove(handIndex);
                    gamePlayed.getPlayerOne().subtractMana(cardToPlace.getMana());
                }
            }
        } else {
            Card cardToPlace = gamePlayed.getPlayerTwo().getHandCards().get(handIndex);
            if (cardToPlace.getType().equals("environment")) {
                output.add(handler.outputPlaceCardError(handIndex,
                        "Cannot place environment card on table."));
            } else if (gamePlayed.getPlayerTwo().getMana() < cardToPlace.getMana()) {
                output.add(handler.outputPlaceCardError(handIndex,
                        "Not enough mana to place card on table."));
            } else if ((cardToPlace.isFrontRow()
                    && (gamePlayed.getTable().get(1).size() == helper.getMaxCardsOnRow()))
                    || (cardToPlace.isBackRow()
                    && (gamePlayed.getTable().get(0).size() == helper.getMaxCardsOnRow()))) {
                output.add(handler.outputPlaceCardError(handIndex,
                        "Cannot place card on table since row is full."));
            } else {
                if (cardToPlace.isFrontRow()) {
                    gamePlayed.getTable().get(1).add(cardToPlace);
                    if (cardToPlace.isTank()) {
                        gamePlayed.getPlayerTwo().setTankOnTable(true);
                    }
                    gamePlayed.getPlayerTwo().getHandCards().remove(handIndex);
                    gamePlayed.getPlayerTwo().subtractMana(cardToPlace.getMana());
                } else if (cardToPlace.isBackRow()) {
                    gamePlayed.getTable().get(0).add(cardToPlace);
                    gamePlayed.getPlayerTwo().getHandCards().remove(handIndex);
                    gamePlayed.getPlayerTwo().subtractMana(cardToPlace.getMana());
                }
            }
        }
    }

    /**
     * output player mana
     * @param playerIndex to output
     * @param manaPlayerOne mana
     * @param manaPlayerTwo mana
     * @return objectnode to add to output
     */
    public ObjectNode outputPlayerMana(final int playerIndex,
                                       final int manaPlayerOne,
                                       final int manaPlayerTwo) {
        int mana;
        if (playerIndex == 1) {
            mana = manaPlayerOne;
        } else {
            mana = manaPlayerTwo;
        }
        ObjectNode objectNode = (new ObjectMapper().createObjectNode());
        objectNode.put("command", "getPlayerMana");
        objectNode.put("playerIdx", playerIndex);
        objectNode.put("output", mana);

        return objectNode;
    }

    /**
     * output all cards on table
     * @param gamePlayed current game
     * @return objectnode to output
     */
    public ObjectNode outputCardsOnTable(final Game gamePlayed) {
        ObjectNode objectNode = (new ObjectMapper().createObjectNode());
        objectNode.put("command", "getCardsOnTable");
        ArrayNode nodes = objectNode.putArray("output");
        for (ArrayList<Card> cards : gamePlayed.getTable()) {
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

    /**
     *
     * @param playerIndex player
     * @param handPlayerOne hand
     * @param handPlayerTwo hand
     * @return objectnode to output
     */
    public ObjectNode outputEnvironmentCardsInHand(final int playerIndex,
                                                   final ArrayList<Card> handPlayerOne,
                                                   final ArrayList<Card> handPlayerTwo) {
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

    /**
     *
     * @param affectedRow row to use
     * @param handIndex card
     * @param output -
     * @param gamePlayed current game
     */
    public void useEnvironmentCard(final int affectedRow,
                                   final int handIndex,
                                   final ArrayNode output,
                                   final Game gamePlayed) {
        ErrorHandler handler = new ErrorHandler();
        Constant helper = new Constant();
        if (gamePlayed.getPlayerTurn() == 1) {
            Card card = gamePlayed.getPlayerOne().getHandCards().get(handIndex);
            if (!card.getType().equals("environment")) {
                output.add(handler.outputErrorEnvironmentCard(affectedRow, handIndex,
                        "Chosen card is not of type environment."));
            } else if (gamePlayed.getPlayerOne().getMana() < card.getMana()) {
                output.add(handler.outputErrorEnvironmentCard(affectedRow, handIndex,
                        "Not enough mana to use environment card."));
            } else if ((affectedRow != 0) && (affectedRow != 1)) {
                output.add(handler.outputErrorEnvironmentCard(affectedRow, handIndex,
                        "Chosen row does not belong to the enemy."));
            } else {
                String abilityOutput = card.useAbilityEnvironment(affectedRow,
                        gamePlayed.getTable(), gamePlayed);
                if (abilityOutput.equals("cannot steal")) {
                    output.add(handler.outputErrorEnvironmentCard(affectedRow,
                            handIndex,
                            "Cannot steal enemy card since the player's row is full."));
                } else if (abilityOutput.equals("ability used")) {
                    gamePlayed.getPlayerOne().subtractMana(card.getMana());
                    gamePlayed.getPlayerOne().getHandCards().remove(handIndex);
                }
            }

        } else {
            Card card = gamePlayed.getPlayerTwo().getHandCards().get(handIndex);
            if (!card.getType().equals("environment")) {
                output.add(handler.outputErrorEnvironmentCard(affectedRow, handIndex,
                        "Chosen card is not of type environment."));
            } else if (gamePlayed.getPlayerTwo().getMana() < card.getMana()) {
                output.add(handler.outputErrorEnvironmentCard(affectedRow, handIndex,
                        "Not enough mana to use environment card."));
            } else if ((affectedRow != helper.getReverseRow2())
                    && (affectedRow != helper.getReverseRow3())) {
                output.add(handler.outputErrorEnvironmentCard(affectedRow, handIndex,
                        "Chosen row does not belong to the enemy."));
            } else {
                String abilityOutput = card.useAbilityEnvironment(affectedRow,
                        gamePlayed.getTable(), gamePlayed);
                if (abilityOutput.equals("cannot steal")) {
                    output.add(handler.outputErrorEnvironmentCard(affectedRow, handIndex,
                            "Cannot steal enemy card since the player's row is full."));
                } else if (abilityOutput.equals("ability used")) {
                    gamePlayed.getPlayerTwo().subtractMana(card.getMana());
                    gamePlayed.getPlayerTwo().getHandCards().remove(handIndex);
                }
            }
        }
    }

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param gamePlayed current
     * @return objectnode to output
     */
    public ObjectNode getCardAtPosition(final int x,
                                        final int y,
                                        final Game gamePlayed) {
        ObjectNode objectNode = (new ObjectMapper().createObjectNode());
        objectNode.put("command", "getCardAtPosition");
        objectNode.put("x", x);
        objectNode.put("y", y);
        if (y >= gamePlayed.getTable().get(x).size()) {
            objectNode.put("output", "No card available at that position.");
            return objectNode;
        }
        Card card = gamePlayed.getTable().get(x).get(y);

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

    /**
     *
     * @param gamePlayed current game
     * @return objectnode to add to output
     */
    public ObjectNode getFrozenCardsOnTable(final Game gamePlayed) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", "getFrozenCardsOnTable");
        ArrayNode outputNodes = objectNode.putArray("output");
        for (int i = 0; i < gamePlayed.getTable().size(); i++) {
            for (int j = 0; j < gamePlayed.getTable().get(i).size(); j++) {
                Card card = gamePlayed.getTable().get(i).get(j);
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

    /**
     *
     * @param output -
     * @param xAttacker coordinate
     * @param yAttacker coordinate
     * @param xAttacked coordinate
     * @param yAttacked coordinate
     * @param gamePlayed coordinate
     */
    public void cardUsesAttack(final ArrayNode output,
                               final int xAttacker,
                               final int yAttacker,
                               final int xAttacked,
                               final int yAttacked,
                               final Game gamePlayed) {
        ErrorHandler handler = new ErrorHandler();
        Constant helper = new Constant();
        if (gamePlayed.getPlayerTurn() == 1) {
            if (((xAttacked == 2) || (xAttacked == helper.getReverseRow3()))
                    || (yAttacked >= gamePlayed.getTable().get(xAttacked).size())) {
                output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                        xAttacked, yAttacked,
                        "cardUsesAttack", "Attacked card does not belong to the enemy."));
            } else if (gamePlayed.getTable().get(xAttacker).get(yAttacker).hasAttacked()) {
                output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                        xAttacked, yAttacked,
                        "cardUsesAttack", "Attacker card has already attacked this turn."));
            } else if (gamePlayed.getTable().get(xAttacker).get(yAttacker).isFrozen()) {
                output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                        xAttacked, yAttacked,
                        "cardUsesAttack", "Attacker card is frozen."));
            } else if (gamePlayed.getPlayerTwo().isTankOnTable()
                    && !gamePlayed.getTable().get(xAttacked).get(yAttacked).isTank()) {
                output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                        xAttacked, yAttacked,
                        "cardUsesAttack", "Attacked card is not of type 'Tank'."));
            } else {
                Card cardAttacked = gamePlayed.getTable().get(xAttacked).get(yAttacked);
                Card cardAttacker = gamePlayed.getTable().get(xAttacker).get(yAttacker);
                cardAttacked.subtractHealth(cardAttacker.getAttackDamage());
                if (gamePlayed.getTable().get(xAttacked).get(yAttacked).getHealth() <= 0) {
                    deleteCardOnTable(xAttacked, yAttacked, gamePlayed);
                }
                gamePlayed.getTable().get(xAttacker).get(yAttacker).setHasAttacked(true);
            }
        } else {
            if (((xAttacked == 0) || (xAttacked == 1))
                    || (yAttacked >= gamePlayed.getTable().get(xAttacked).size())) {
                output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                        xAttacked, yAttacked,
                        "cardUsesAttack", "Attacked card does not belong to the enemy."));
            } else if (gamePlayed.getTable().get(xAttacker).get(yAttacker).hasAttacked()) {
                output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                        xAttacked, yAttacked,
                        "cardUsesAttack", "Attacker card has already attacked this turn."));
            } else if (gamePlayed.getTable().get(xAttacker).get(yAttacker).isFrozen()) {
                output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                        xAttacked, yAttacked,
                        "cardUsesAttack", "Attacker card is frozen."));
            } else if (gamePlayed.getPlayerOne().isTankOnTable()
                    && !gamePlayed.getTable().get(xAttacked).get(yAttacked).isTank()) {
                output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                        xAttacked, yAttacked,
                        "cardUsesAttack", "Attacked card is not of type 'Tank'."));
            } else {
                Card cardAttacked = gamePlayed.getTable().get(xAttacked).get(yAttacked);
                Card cardAttacker = gamePlayed.getTable().get(xAttacker).get(yAttacker);
                cardAttacked.subtractHealth(cardAttacker.getAttackDamage());
                if (gamePlayed.getTable().get(xAttacked).get(yAttacked).getHealth() <= 0) {
                    deleteCardOnTable(xAttacked, yAttacked, gamePlayed);
                }
                gamePlayed.getTable().get(xAttacker).get(yAttacker).setHasAttacked(true);
            }
        }
    }

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param gamePlayed current game
     */
    public static void deleteCardOnTable(final int x,
                                         final int y,
                                         final Game gamePlayed) {
        Card cardToRemove = gamePlayed.getTable().get(x).remove(y);
        boolean playerOneHasTank = false;
        boolean playerTwoHasTank = false;
        Constant helper = new Constant();
        if (cardToRemove.isTank()) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < gamePlayed.getTable().get(i).size(); j++) {
                    if (gamePlayed.getTable().get(i).get(j).isTank()) {
                        gamePlayed.getPlayerTwo().setTankOnTable(true);
                        playerTwoHasTank = true;
                        break;
                    }
                }
            }
            if (!playerTwoHasTank) {
                gamePlayed.getPlayerTwo().setTankOnTable(false);
            }
            for (int i = 2; i < helper.getMaxRowPlayerOne(); i++) {
                for (int j = 0; j < gamePlayed.getTable().get(i).size(); j++) {
                    if (gamePlayed.getTable().get(i).get(j).isTank()) {
                        gamePlayed.getPlayerOne().setTankOnTable(true);
                        playerOneHasTank = true;
                        break;
                    }
                }
            }
            if (!playerOneHasTank) {
                gamePlayed.getPlayerOne().setTankOnTable(false);
            }
        }
    }

    /**
     *
     * @param handPlayerTwo cards
     * @param objectNode -
     * @param outputNodes -
     * @return objectnode to add
     */
    private ObjectNode getJsonNodes(final ArrayList<Card> handPlayerTwo,
                                    final ObjectNode objectNode,
                                    final ArrayNode outputNodes) {
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

    /**
     *
     * @param output -
     * @param xAttacker coordinate
     * @param yAttacker coordinate
     * @param xAttacked coordinate
     * @param yAttacked coordinate
     * @param gamePlayed coordinate
     */
    public void cardUsesAbility(final ArrayNode output,
                                final int xAttacker,
                                final int yAttacker,
                                final int xAttacked,
                                final int yAttacked,
                                final Game gamePlayed) {
        ErrorHandler handler = new ErrorHandler();
        Card cardUse = gamePlayed.getTable().get(xAttacker).get(yAttacker);
        Constant helper = new Constant();
        if (cardUse.isFrozen()) {
            output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                    xAttacked, yAttacked,
                    "cardUsesAbility", "Attacker card is frozen."));
            return;
        }
        if (cardUse.hasAttacked()) {
            output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                    xAttacked, yAttacked,
                    "cardUsesAbility",
                    "Attacker card has already attacked this turn."));
            return;
        }

        if (cardUse.getName().equals("Disciple")) {
            if (gamePlayed.getPlayerTurn() == 1
                    && xAttacked != 2
                    && xAttacked != helper.getReverseRow3()) {
                output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                        xAttacked, yAttacked,
                        "cardUsesAbility",
                        "Attacked card does not belong to the current player."));
                return;
            }
            if (gamePlayed.getPlayerTurn() == 2 && xAttacked != 0 && xAttacked != 1) {
                output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                        xAttacked, yAttacked,
                        "cardUsesAbility",
                        "Attacked card does not belong to the current player."));
                return;
            }
            cardUse.useAbilityMinion(xAttacked, yAttacked,
                    gamePlayed.getTable(), gamePlayed);
            return;
        }

        if (gamePlayed.getPlayerTurn() == 1) {
            if (((xAttacked == 2) || (xAttacked == helper.getReverseRow3()))
                    || (yAttacked >= gamePlayed.getTable().get(xAttacked).size())) {
                output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                        xAttacked, yAttacked,
                        "cardUsesAbility",
                        "Attacked card does not belong to the enemy."));
            } else if (gamePlayed.getPlayerTwo().isTankOnTable()
                    && !gamePlayed.getTable().get(xAttacked).get(yAttacked).isTank()) {
                output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                        xAttacked, yAttacked,
                        "cardUsesAbility",
                        "Attacked card is not of type 'Tank'."));
            } else {
                cardUse.useAbilityMinion(xAttacked, yAttacked,
                        gamePlayed.getTable(), gamePlayed);
            }
            return;
        }

        if (((xAttacked == 0) || (xAttacked == 1))) {
            output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                    xAttacked, yAttacked,
                    "cardUsesAbility",
                    "Attacked card does not belong to the enemy."));
        } else if (gamePlayed.getPlayerOne().isTankOnTable()
                && !gamePlayed.getTable().get(xAttacked).get(yAttacked).isTank()) {
            output.add(handler.outputErrorCardAttack(xAttacker, yAttacker,
                    xAttacked, yAttacked,
                    "cardUsesAbility", "Attacked card is not of type 'Tank'."));
        } else {
            cardUse.useAbilityMinion(xAttacked, yAttacked, gamePlayed.getTable(), gamePlayed);
        }
    }

    /**
     *
     * @param output -
     * @param xAttacker - coordinate
     * @param yAttacker - coordinate
     * @param hero -
     * @param gamePlayed - current game
     */
    public void useAttackHero(final ArrayNode output,
                              final int xAttacker,
                              final int yAttacker,
                              final StartInputGame hero,
                              final Game gamePlayed) {
        ErrorHandler handler = new ErrorHandler();
        Card cardAttacker = gamePlayed.getTable().get(xAttacker).get(yAttacker);
        if (cardAttacker.isFrozen()) {
            output.add(handler.outputErrorHeroAttack(xAttacker, yAttacker,
                    "Attacker card is frozen."));
        } else if (cardAttacker.hasAttacked()) {
            output.add(handler.outputErrorHeroAttack(xAttacker, yAttacker,
                    "Attacker card has already attacked this turn."));
        } else if (gamePlayed.getPlayerTurn() == 1) {
            if (gamePlayed.getPlayerTwo().isTankOnTable()) {
                output.add(handler.outputErrorHeroAttack(xAttacker, yAttacker,
                        "Attacked card is not of type 'Tank'."));
            } else {
                hero.getPlayerTwoHero().subtractHealth(cardAttacker.getAttackDamage());
                if (hero.getPlayerTwoHero().getHealth() <= 0) {
                    output.add(gameEnded(1));
                    gamePlayed.increasePlayerOneWins();
                }
                cardAttacker.setHasAttacked(true);
            }
        } else {
            if (gamePlayed.getPlayerOne().isTankOnTable()) {
                output.add(handler.outputErrorHeroAttack(xAttacker, yAttacker,
                        "Attacked card is not of type 'Tank'."));
            } else {
                hero.getPlayerOneHero().subtractHealth(cardAttacker.getAttackDamage());
                if (hero.getPlayerOneHero().getHealth() <= 0) {
                    output.add(gameEnded(2));
                    gamePlayed.incresePlayerTwoWins();
                }
                cardAttacker.setHasAttacked(true);
            }
        }
    }

    /**
     *
     * @param winner index
     * @return objectnode to add to output
     */
    public ObjectNode gameEnded(final int winner) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        if (winner == 1) {
            objectNode.put("gameEnded", "Player one killed the enemy hero.");
        } else {
            objectNode.put("gameEnded", "Player two killed the enemy hero.");

        }
        return objectNode;
    }

    /**
     *
     * @param output -
     * @param affectedRow index
     * @param game input of game
     * @param gamePlayed current game
     */
    public void useHeroAbility(final ArrayNode output,
                               final int affectedRow,
                               final StartInputGame game,
                               final Game gamePlayed) {
        ErrorHandler handler = new ErrorHandler();
        Card heroPlayerOne = game.getPlayerOneHero();
        Card heroPlayerTwo = game.getPlayerTwoHero();
        Constant helper = new Constant();
        if (gamePlayed.getPlayerTurn() == 1) {
            if (heroPlayerOne.getMana() > gamePlayed.getPlayerOne().getMana()) {
                output.add(handler.outputHeroAbilityError(affectedRow,
                        "Not enough mana to use hero's ability."));
                return;
            }
            if (heroPlayerOne.hasAttacked()) {
                output.add(handler.outputHeroAbilityError(affectedRow,
                        "Hero has already attacked this turn."));
                return;
            }
            if (heroPlayerOne.getName().equals("Lord Royce")
                    || heroPlayerOne.getName().equals("Empress Thorina")) {
                if (affectedRow == 2 || affectedRow == helper.getReverseRow3()) {
                    output.add(handler.outputHeroAbilityError(affectedRow,
                            "Selected row does not belong to the enemy."));
                } else {
                    heroPlayerOne.useAbilityHero(affectedRow, gamePlayed.getTable(), gamePlayed);
                }
                return;
            }
            if (heroPlayerOne.getName().equals("General Kocioraw")
                    || heroPlayerOne.getName().equals("King Mudface")) {
                if (affectedRow == 0 || affectedRow == 1) {
                    output.add(handler.outputHeroAbilityError(affectedRow,
                            "Selected row does not belong to the current player."));
                } else {
                    heroPlayerOne.useAbilityHero(affectedRow, gamePlayed.getTable(), gamePlayed);
                }
                return;
            }
        }
        if (gamePlayed.getPlayerTurn() == 2) {
            if (heroPlayerTwo.getMana() > gamePlayed.getPlayerTwo().getMana()) {
                output.add(handler.outputHeroAbilityError(affectedRow,
                        "Not enough mana to use hero's ability."));
                return;
            }
            if (heroPlayerTwo.hasAttacked()) {
                output.add(handler.outputHeroAbilityError(affectedRow,
                        "Hero has already attacked this turn."));
                return;
            }
            if (heroPlayerTwo.getName().equals("Lord Royce")
                    || heroPlayerTwo.getName().equals("Empress Thorina")) {
                if (affectedRow == 0 || affectedRow == 1) {
                    output.add(handler.outputHeroAbilityError(affectedRow,
                            "Selected row does not belong to the enemy."));
                    return;
                } else {
                    heroPlayerTwo.useAbilityHero(affectedRow, gamePlayed.getTable(), gamePlayed);
                }
            }
            if (heroPlayerTwo.getName().equals("General Kocioraw")
                    || heroPlayerTwo.getName().equals("King Mudface")) {
                if (affectedRow == 2 || affectedRow == helper.getReverseRow3()) {
                    output.add(handler.outputHeroAbilityError(affectedRow,
                            "Selected row does not belong to the current player."));
                } else {
                    heroPlayerTwo.useAbilityHero(affectedRow,
                            gamePlayed.getTable(), gamePlayed);
                }
            }
        }
    }
}
