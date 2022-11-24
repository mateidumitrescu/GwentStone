package game;

public class Constant {
    /**
     *
     * @return 5
     */
    public int getMaxCardsOnRow() {
        return maxCardsOnRow;
    }

    /**
     *
     * @return 10
     */
    public int getMaxManaToGet() {
        return maxManaToGet;
    }

    /**
     *
     * @return 0
     */
    public int getFirstIndex() {
        return firstIndex;
    }

    /**
     *
     * @return 4
     */
    public int getMaxRowPlayerOne() {
        return maxRowPlayerOne;
    }

    /**
     *
     * @return 2
     */
    public int getMaxRowPlayerTwo() {
        return maxRowPlayerTwo;
    }

    /**
     *
     * @return 30
     */
    public int getHeroHealth() {
        return heroHealth;
    }

    public Constant() {
        this.reverseRow = 0;
        this.reverseRow1 = 1;
        this.reverseRow2 = 2;
        this.reverseRow3 = 3;
        this.reverseRow0 = 4;
        this.maxHealth = -1;
        this.indexCardToSteal = -1;
        this.maxCardsOnRow = 5;
        this.maxManaToGet = 10;
        this.firstIndex = 0;
        this.maxRowPlayerOne = 4;
        this.maxRowPlayerTwo = 2;
        this.heroHealth = 30;

    }
    private final int heroHealth;
    private final int maxRowPlayerTwo;
    private final int maxRowPlayerOne;
    private final int firstIndex;

    /**
     *
     * @param reverseRow change
     */
    public void setReverseRow(final int reverseRow) {
        this.reverseRow = reverseRow;
    }

    private final int maxCardsOnRow;
    private final int maxManaToGet;
    private int reverseRow;
    private final int reverseRow1;
    private final int reverseRow2;
    private final int reverseRow3;
    private final int reverseRow0;

    /**
     *
     * @param maxHealth change
     */
    public void setMaxHealth(final int maxHealth) {
        this.maxHealth = maxHealth;
    }

    private int maxHealth;

    /**
     *
     * @param indexCardToSteal change
     */
    public void setIndexCardToSteal(final int indexCardToSteal) {
        this.indexCardToSteal = indexCardToSteal;
    }

    private int indexCardToSteal;

    /**
     *
     * @return reverse row
     */
    public int getReverseRow() {
        return reverseRow;
    }

    /**
     *
     * @return 1
     */
    public int getReverseRow1() {
        return reverseRow1;
    }

    /**
     *
     * @return 2
     */
    public int getReverseRow2() {
        return reverseRow2;
    }

    /**
     *
     * @return 3
     */
    public int getReverseRow3() {
        return reverseRow3;
    }

    /**
     *
     * @return 0
     */
    public int getReverseRow0() {
        return reverseRow0;
    }

    /**
     *
     * @return max health
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     *
     * @return index
     */
    public int getIndexCardToSteal() {
        return indexCardToSteal;
    }
}
