package cards;

import interfaces.Minion;

public class Goliath extends Card {
    private boolean justJoined;

    public boolean isJustJoined() {
        return justJoined;
    }

    public void setJustJoined(boolean justJoined) {
        this.justJoined = justJoined;
    }
}
