package lab.game.controller;

import lab.game.model.Minesweeper;

public class GraphicController {
    private final Minesweeper model;

    public GraphicController(Minesweeper model) {
        this.model = model;
    }

    public void open(int x, int y) {
        model.openCell(x, y);
    }

    public void setflag(int x, int y) {
        model.markCell(x, y);
    }
}
