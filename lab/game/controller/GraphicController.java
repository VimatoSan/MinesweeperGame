package lab.game.controller;

import lab.game.model.Minesweeper;

import javax.swing.*;
import java.awt.event.*;

public class GraphicController {
    private final Minesweeper model;

    public GraphicController(Minesweeper model) {
        this.model = model;
    }

    public MouseListener handleClickOnGameCell(int x, int y) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    open(x, y);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    setflag(x, y);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        };
    }

    public void open(int x, int y) {
        model.openCell(x, y);
    }

    public void setflag(int x, int y) {
        model.markCell(x, y);
    }
}
