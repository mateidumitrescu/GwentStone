package game;

import java.util.ArrayList;

public class GamesInput {
    private InputPlayers gameStart;
    private ArrayList<Action> actions;

    public InputPlayers getGameStart() {
        return gameStart;
    }

    public void setGameStart(InputPlayers gameStart) {
        this.gameStart = gameStart;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }
}
