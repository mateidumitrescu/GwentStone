package cards;

import java.util.ArrayList;

public class Card {
    private int mana;
    private int attackDamage;
    private int health;

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

    private boolean hasUsedAbility;

    public boolean getHasUsedAbility() {
        return hasUsedAbility;
    }

    public void setHasUsedAbility(boolean hasUsedAbility) {
        this.hasUsedAbility = hasUsedAbility;
    }
}
