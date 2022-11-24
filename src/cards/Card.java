package cards;

import game.ActionHandler;
import game.Constant;
import game.Game;

import java.util.ArrayList;

public class Card {
    private int mana;
    private int attackDamage;
    private int health;

    private boolean frontRow;

    private boolean tank;

    /**
     * get if card is tank
     * @return true if card is type tank
     */
    public boolean isTank() {
        return tank;
    }

    /**
     * use environment card ability
     * @param affectedRow index
     * @param table with cards
     * @param game played
     * @return what happened after ability
     */
    public String useAbilityEnvironment(final int affectedRow,
                                        final ArrayList<ArrayList<Card>> table,
                                        final Game game) {
        switch (this.getName()) {
            case "Firestorm":
                for (int i = 0; i < table.get(affectedRow).size(); i++) {
                    table.get(affectedRow).get(i).subtractHealth(1);
                    if (table.get(affectedRow).get(i).getHealth() <= 0) {
                        ActionHandler.deleteCardOnTable(affectedRow, i, game);
                        i--;
                    }
                }
                break;
            case "Winterfell":
                for (int i = 0; i < table.get(affectedRow).size(); i++) {
                    table.get(affectedRow).get(i).setFrozen(true);
                }
                break;
            case "Heart Hound":
                Constant helper = new Constant();
                if (affectedRow == helper.getReverseRow0()) {
                    helper.setReverseRow(helper.getReverseRow3());
                } else if (affectedRow == helper.getReverseRow1()) {
                    helper.setReverseRow(helper.getReverseRow2());
                } else if (affectedRow == helper.getReverseRow2()) {
                    helper.setReverseRow(helper.getReverseRow1());
                } else if (affectedRow == helper.getReverseRow3()) {
                    helper.setReverseRow(helper.getReverseRow0());
                }
                if (table.get(helper.getReverseRow()).size() == helper.getMaxCardsOnRow()) {
                    return "cannot steal";
                }
                for (int i = 0; i < table.get(affectedRow).size(); i++) {
                    Card card = table.get(affectedRow).get(i);
                    if (card.getHealth() > helper.getMaxHealth()) {
                        helper.setMaxHealth(card.getHealth());
                        helper.setIndexCardToSteal(i);
                    }
                }
                Card cardToAdd = table.get(affectedRow).get(helper.getIndexCardToSteal());
                table.get(helper.getReverseRow()).add(cardToAdd);
                ActionHandler.deleteCardOnTable(affectedRow, helper.getIndexCardToSteal(), game);
                break;
            default: return "card type is wrong";
        }
        return "ability used";
    }

    /**
     * use minion card ability
     * @param x coordinate
     * @param y coordinate
     * @param table with cards
     * @param game played
     */
    public void useAbilityMinion(final int x, final int y,
                                 final ArrayList<ArrayList<Card>> table,
                                 final Game game) {
        Card cardToApply = table.get(x).get(y);
        switch (this.getName()) {
            case "The Ripper" -> cardToApply.setAttackDamage(cardToApply.getAttackDamage() - 2);
            case "Miraj" -> {
                int aux = cardToApply.getHealth();
                cardToApply.setHealth(this.health);
                this.setHealth(aux);
            }
            case "The Cursed One" -> {
                int aux1 = cardToApply.getHealth();
                cardToApply.setHealth(cardToApply.getAttackDamage());
                cardToApply.setAttackDamage(aux1);
                if (cardToApply.getHealth() <= 0) {
                    ActionHandler.deleteCardOnTable(x, y, game);
                }
            }
            case "Disciple" -> {
                System.out.println(cardToApply.getHealth());
                cardToApply.increaseHealth(2);
            }
            default -> {
                return;
            }
        }
        this.setHasAttacked(true);
    }

    /**
     * use hero ability
     * @param affectedRow index
     * @param table with cards
     * @param game played
     */
    public void useAbilityHero(final int affectedRow,
                               final ArrayList<ArrayList<Card>> table,
                               final Game game) {
        switch (this.getName()) {
            case "Lord Royce" -> {
                int maxAttack = 0;
                for (Card card : table.get(affectedRow)) {
                    if (card.getAttackDamage() > maxAttack) {
                        maxAttack = card.getAttackDamage();
                    }
                }
                for (Card card : table.get(affectedRow)) {
                    if (card.getAttackDamage() == maxAttack) {
                        card.setFrozen(true);
                        break;
                    }
                }
            }
            case "Empress Thorina" -> {
                int maxHealth = 0;
                for (Card card : table.get(affectedRow)) {
                    if (card.getHealth() > maxHealth) {
                        maxHealth = card.getHealth();
                    }
                }
                for (int i = 0; i < table.get(affectedRow).size(); i++) {
                    Card card = table.get(affectedRow).get(i);
                    if (card.getHealth() == maxHealth) {
                        ActionHandler.deleteCardOnTable(affectedRow, i, game);
                        break;
                    }
                }
            }
            case "King Mudface" -> {
                for (Card card : table.get(affectedRow)) {
                    card.setHealth(card.getHealth() + 1);
                }
            }
            case "General Kocioraw" -> {
                for (Card card : table.get(affectedRow)) {
                    card.setAttackDamage(card.getAttackDamage() + 1);
                }
            }
            default -> {
                return;
            }
        }
        // updating mana and card has attacked
        this.setHasAttacked(true);
        if (game.getPlayerTurn() == 1) {
            game.getPlayerOne().setMana(game.getPlayerOne().getMana() - this.getMana());
        } else if (game.getPlayerTurn() == 2) {
            game.getPlayerTwo().setMana(game.getPlayerTwo().getMana() - this.getMana());
        }

    }

    /**
     * increase health for card
     * @param health plus
     */
    public void increaseHealth(final int healthPlus) {
        this.health = this.health + healthPlus;
    }

    /**
     *
     * @param damageDone to card
     */
    public void subtractHealth(final int damageDone) {
        this.health = this.health - damageDone;
    }

    /**
     * set if card is tank or not
     * @param tank (true / false)
     */
    public void setTank(final boolean tank) {
        this.tank = tank;
    }

    private boolean backRow;

    /**
     * verify if card should be placed on front row
     * @return true if card should be placed front row
     */
    public boolean isFrontRow() {
        return frontRow;
    }

    /**
     * set if card should be placed front row
     * @param frontRow (true / false)
     */
    public void setFrontRow(final boolean frontRow) {
        this.frontRow = frontRow;
    }

    /**
     * set if card should be places on back row
     * @param backRow (true / false)
     */
    public void setBackRow(final boolean backRow) {
        this.backRow = backRow;
    }

    /**
     * verify if card should be place on back row
     * @return true if card should be placed on back row
     */
    public boolean isBackRow() {
        return backRow;
    }

    /**
     *
     * @return get mana of card
     */
    public int getMana() {
        return mana;
    }

    /**
     * set mana for card
     * @param mana of card
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     *
     * @return attack damage of card
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * set attack damage for card
     * @param attackDamage of card
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
        if (this.attackDamage < 0) {
            this.attackDamage = 0;
        }
    }

    /**
     *
     * @return health of card
     */
    public int getHealth() {
        return health;
    }

    /**
     * set health for card
     * @param health for card
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     *
     * @return description of card
     */
    public String getDescription() {
        return description;
    }

    /**
     * set the description for card
     * @param description of card
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     *
     * @return colors of card
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * set colors for card
     * @param colors of card
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     *
     * @return card name
     */
    public String getName() {
        return name;
    }

    /**
     * set the card name
     * @param name of card
     */
    public void setName(final String name) {
        this.name = name;
        switch (getName()) {
            case "Sentinel", "Berserker", "Goliath", "Warden", "Miraj",
                    "The Ripper", "Disciple", "The Cursed One" -> setType("minion");
            case "Firestorm", "Winterfell", "Heart Hound" -> setType("environment");
            case "Lord Royce", "Empress Thorina",
                    "King Mudface", "General Kocioraw" -> setType("hero");
            default -> setType("no type");
        }
    }

    private String description;
    private ArrayList<String> colors;

    /**
     *
     * @return card type (minion / environment / hero)
     */
    public String getType() {
        return type;
    }

    /**
     * set card type
     * @param type of card
     */
    public void setType(final String type) {
        this.type = type;
    }

    private String name;

    private String type;

    private boolean isFrozen;

    /**
     *
     * @return true if card is frozen
     */
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * set card frozen / unfrozen
     * @param frozen (true / false)
     */
    public void setFrozen(final boolean frozen) {
        isFrozen = frozen;
    }

    private boolean hasAttacked = false;


    /**
     *
     * @return if card has attacked on one round
     */
    public boolean hasAttacked() {
        return hasAttacked;
    }

    /**
     * set if card has attacked
     * @param hasAttacked card
     */
    public void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

}
