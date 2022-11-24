package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ErrorHandler {

    /**
     *
     * @param affectedRow index
     * @param handIndex card
     * @param error string
     * @return object node to output
     */
    public ObjectNode outputErrorEnvironmentCard(final int affectedRow,
                                                 final int handIndex,
                                                 final String error) {
        ObjectNode objectNode = (new ObjectMapper().createObjectNode());
        objectNode.put("command", "useEnvironmentCard");
        objectNode.put("handIdx", handIndex);
        objectNode.put("affectedRow", affectedRow);
        objectNode.put("error", error);

        return objectNode;
    }

    /**
     *
     * @param xAttacker coordinate
     * @param yAttacker coordinate
     * @param xAttacked coordinate
     * @param yAttacked coordinate
     * @param command coordinate
     * @param error coordinate
     * @return object node to output
     */
    public ObjectNode outputErrorCardAttack(final int xAttacker, final int yAttacker,
                                            final int xAttacked, final int yAttacked,
                                            final String command, final String error) {
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

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param error string
     * @return object node to output
     */
    public ObjectNode outputErrorHeroAttack(final int x,
                                            final int y,
                                            final String error) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", "useAttackHero");
        ObjectNode cardAttacker = new ObjectMapper().createObjectNode();
        cardAttacker.put("x", x);
        cardAttacker.put("y", y);
        objectNode.put("cardAttacker", cardAttacker);
        objectNode.put("error", error);
        return objectNode;
    }

    /**
     *
     * @param affectedRow index
     * @param error string
     * @return object node to output
     */
    public ObjectNode outputHeroAbilityError(final int affectedRow,
                                             final String error) {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("command", "useHeroAbility");
        objectNode.put("affectedRow", affectedRow);
        objectNode.put("error", error);

        return objectNode;
    }

    /**
     *
     * @param handIndex card
     * @param error string
     * @return object node to output
     */
    public ObjectNode outputPlaceCardError(final int handIndex,
                                           final String error) {
        ObjectNode objectNode = (new ObjectMapper().createObjectNode());
        objectNode.put("command", "placeCard");
        objectNode.put("handIdx", handIndex);
        objectNode.put("error", error);
        return objectNode;
    }
}
