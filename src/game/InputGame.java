package game;

import java.util.ArrayList;

public class InputGame {
    private StartInputGame gameStart;
    private ArrayList<Action> actions;

    /**
     *
     * @return start game input
     */
    public StartInputGame getGameStart() {
        return gameStart;
    }

    /**
     *
     * @param gameStart input set
     */
    public void setGameStart(final StartInputGame gameStart) {
        this.gameStart = gameStart;
    }

    /**
     *
     * @return array of actions
     */
    public ArrayList<Action> getActions() {
        return actions;
    }

    /**
     *
     * @param actions set
     */
    public void setActions(final ArrayList<Action> actions) {
        this.actions = actions;
    }
}
