package lab.game.view;

import lab.game.model.GameField;
import lab.game.model.Minesweeper;
import lab.game.model.GameTimer;

public interface Observer {
    public void update(GameField gameField, Minesweeper.State state);
    public void updateTimer(String time);
    public void handleException(Exception e);
}
