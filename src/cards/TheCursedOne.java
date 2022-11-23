package cards;



public class TheCursedOne extends Card {
    public TheCursedOne() {
        super.setFrontRow(false);
        super.setBackRow(true);
        super.setHealth(0);
        super.setTank(false);
        super.setHasAttacked(false);
    }

}
