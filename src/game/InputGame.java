package game;

import java.util.ArrayList;

public class InputGame {
    private StartInputGame gameStart;
    private ArrayList<Action> actions;

    public StartInputGame getGameStart() {
        return gameStart;
    }

    public void setGameStart(StartInputGame gameStart) {
        this.gameStart = gameStart;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }
}
