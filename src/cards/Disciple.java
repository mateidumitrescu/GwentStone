package cards;



public class Disciple extends Card {
    public Disciple() {
        super.setFrontRow(false);
        super.setBackRow(true);
        super.setHealth(0);
        super.setTank(false);
        super.setHasAttacked(false);
    }
}
