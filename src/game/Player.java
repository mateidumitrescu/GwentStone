package game;

import cards.Card;
import fileio.DecksInput;
import fileio.GameInput;
import fileio.Input;

import java.util.ArrayList;

public class Player {
    private int mana;

    public int getMana() {
        return mana;
    }
    private boolean tankOnTable;

    public boolean isTankOnTable() {
        return tankOnTable;
    }

    public void setTankOnTable(boolean tankOnTable) {
        this.tankOnTable = tankOnTable;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void substractMana(int mana) {
        this.mana = this.mana - mana;
    }

    private int nrCardsInDeck;
    private int nrDecks;

    private ArrayList<Card> handCards;

    public ArrayList<Card> getHandCards() {
        if (handCards == null) {
            handCards = new ArrayList<>();
        }
        return handCards;
    }

    public void setHandCards(ArrayList<Card> handCards) {
        this.handCards = handCards;
    }

    private ArrayList<ArrayList<Card>> decks;

    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    public void setNrCardsInDeck(int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    public int getNrDecks() {
        return nrDecks;
    }

    public void setNrDecks(int nrDecks) {
        this.nrDecks = nrDecks;
    }

    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }

    void addCardInDeck(Card card) {
        this.decks.get(1).add(card);
    }

    void addMana(int mana) {
        this.mana += mana;
    }


}
