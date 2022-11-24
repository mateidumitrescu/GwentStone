package game;

import cards.Card;

import java.util.ArrayList;

public class Player {
    private int mana;

    /**
     *
     * @return mana
     */
    public int getMana() {
        return mana;
    }
    private boolean tankOnTable;

    /**
     *
     * @return if tank
     */
    public boolean isTankOnTable() {
        return tankOnTable;
    }

    /**
     *
     * @param tankOnTable true / false
     */
    public void setTankOnTable(final boolean tankOnTable) {
        this.tankOnTable = tankOnTable;
    }

    /**
     *
     * @param mana to set
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     *
     * @param mana subtract
     */
    public void subtractMana(final int manaToSubtract) {
        this.mana = this.mana - manaToSubtract;
    }

    private int nrCardsInDeck;
    private int nrDecks;

    private ArrayList<Card> handCards;

    /**
     *
     * @return hand cards
     */
    public ArrayList<Card> getHandCards() {
        if (handCards == null) {
            handCards = new ArrayList<>();
        }
        return handCards;
    }

    private ArrayList<ArrayList<Card>> decks;

    /**
     *
     * @return nr cards in deck
     */
    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    /**
     *
     * @param nrCardsInDeck to set
     */
    public void setNrCardsInDeck(final int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    /**
     *
     * @return nr of decks
     */
    public int getNrDecks() {
        return nrDecks;
    }

    /**
     *
     * @param nrDecks to set
     */
    public void setNrDecks(final int nrDecks) {
        this.nrDecks = nrDecks;
    }

    /**
     *
     * @return decks
     */
    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    /**
     *
     * @param decks to set
     */
    public void setDecks(final ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }

    /**
     *
     * @param mana to add
     */
    void addMana(final int manaToAdd) {
        this.mana += manaToAdd;
    }


}
