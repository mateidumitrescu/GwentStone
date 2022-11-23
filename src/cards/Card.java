package cards;

import game.Game;

import java.util.ArrayList;
import java.util.Collections;

public class Card {
    private int mana;
    private int attackDamage;
    private int health;

    private boolean frontRow;

    private boolean tank;

    public boolean isTank() {
        return tank;
    }

    public String useAbilityEnvironment(int affectedRow, ArrayList<ArrayList<Card>> table, Game game) {
        switch (this.getName()) {
            case "Firestorm":
                for (int i = 0; i < table.get(affectedRow).size(); i++) {
                    table.get(affectedRow).get(i).subtractHealth(1);
                    if (table.get(affectedRow).get(i).getHealth() <= 0) {
                        game.deleteCardOnTable(affectedRow, i);
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
                int reverseRow = -1;
                if (affectedRow == 0) {
                    reverseRow = 3;
                } else if (affectedRow == 1) {
                    reverseRow = 2;
                } else if (affectedRow == 2) {
                    reverseRow = 1;
                } else if (affectedRow == 3) {
                    reverseRow = 0;
                }
                if (table.get(reverseRow).size() == 5) {
                    return "cannot steal";
                }
                int maxHealth = 0;
                int indexCardToSteal = -1;
                for (int i = 0; i < table.get(affectedRow).size(); i++) {
                    Card card = table.get(affectedRow).get(i);
                    if (card.getHealth() > maxHealth) {
                        maxHealth = card.getHealth();
                        indexCardToSteal = i;
                    }
                }
                table.get(reverseRow).add(table.get(affectedRow).get(indexCardToSteal));
                game.deleteCardOnTable(affectedRow, indexCardToSteal);
                break;
        }
        return "ability used";
    }

    public void useAbilityMinion(int x, int y, ArrayList<ArrayList<Card>> table, Game game) {
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
                    game.deleteCardOnTable(x, y);
                }
            }
            case "Disciple" -> {
                System.out.println(cardToApply.getHealth());
                cardToApply.increaseHealth(2);
            }
        }
        this.setHasAttacked(true);
    }

    public void useAbilityHero(int affectedRow, ArrayList<ArrayList<Card>> table, Game game) {
        switch(this.getName()) {
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
                        game.deleteCardOnTable(affectedRow, i);
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
        }
        // updating mana and card has attacked
        this.setHasAttacked(true);
        if (game.getPlayerTurn() == 1) {
            game.getPlayerOne().setMana(game.getPlayerOne().getMana() - this.getMana());
        } else if (game.getPlayerTurn() == 2){
            game.getPlayerTwo().setMana(game.getPlayerTwo().getMana() - this.getMana());
        }

    }

    public void increaseHealth(int health) {
        this.health = this.health + health;
    }
    public void subtractHealth(int damage) {
        this.health = this.health - damage;
    }
    public void setTank(boolean tank) {
        this.tank = tank;
    }

    private boolean backRow;

    public boolean isFrontRow() {
        return frontRow;
    }

    public void setFrontRow(boolean frontRow) {
        this.frontRow = frontRow;
    }

    public boolean isBackRow() {
        return backRow;
    }

    public void setBackRow(boolean backRow) {
        this.backRow = backRow;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
        if (this.attackDamage < 0) {
            this.attackDamage = 0;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        switch (getName()) {
            case "Sentinel", "Berserker", "Goliath", "Warden", "Miraj", "The Ripper", "Disciple", "The Cursed One" ->
                    setType("minion");
            case "Firestorm", "Winterfell", "Heart Hound" -> setType("environment");
            case "Lord Royce", "Empress Thorina", "King Mudface", "General Kocioraw" -> setType("hero");
        }
    }

    private String description;
    private ArrayList<String> colors;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String name;

    private String type;

    private boolean isFrozen;

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    private boolean hasAttacked = false;


    public boolean hasAttacked() {
        return hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

}
