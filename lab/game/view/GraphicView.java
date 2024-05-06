package lab.game.view;


import lab.game.controller.GraphicController;
import lab.game.exceptions.CellNotFoundException;
import lab.game.model.Cell;
import lab.game.model.GameField;
import lab.game.model.Minesweeper;
import lab.game.model.GameTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GraphicView implements Observer {
    private static final int TILE_SIZE = 40;
    private final int X_TILES;
    private final int Y_TILES;
    private final GraphicController controller;
    private Tile[][] tiles;
    private JFrame frame = new JFrame("Minesweeper");
    private final ImageIcon MINE_ICON;
    private final ImageIcon FLAG_ICON;


    public GraphicView(GameField gameField, GraphicController controller) {
        MINE_ICON = new ImageIcon(getClass().getResource("/mine_red.png"));
        FLAG_ICON = new ImageIcon(getClass().getResource("/flag.png"));
        X_TILES = gameField.getWidth();
        Y_TILES = gameField.getHeight();
        tiles = new Tile[X_TILES][Y_TILES];
        this.controller = controller;
        showFrame();
       // Pane
    }

    private void showFrame() {
        frame.setSize(X_TILES * TILE_SIZE, Y_TILES * TILE_SIZE);
        // frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(X_TILES, Y_TILES));
        fillBoard(board);
        frame.add(board);
//        tiles[1][1].setText();
//        tiles[1][1].setEnabled(false);
        frame.setVisible(true);
    }


    private void fillBoard(JPanel board) {
        for (int x = 0; x < X_TILES; x++) {
            for (int y = 0; y < Y_TILES; y++) {
                tiles[x][y] = new Tile(x, y);
                board.add(tiles[x][y]);
            }
        }
    }

    private class Tile extends JButton {
        public Tile(int x, int y) {
            super();
            this.setBackground(Color.GRAY);
            this.setMargin(new Insets(0, 0, 0, 0));
            // System.out.println(this.getFont());
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // System.out.println(String.format("%d %d", x, y));
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        controller.open(x, y);
                    }
                    if (e.getButton() == MouseEvent.BUTTON2) {
                        controller.setflag(x, y);
                    }
                }
            });
        }
    }

    @Override
    public void update(GameField gameField, Minesweeper.State state) {
        // ArrayList<Tile> openedTiles = new ArrayList<>();
        for (int x = 0; x < gameField.getWidth(); x++) {
            for (int y = 0; y < gameField.getHeight(); y++) {
                Tile tile = tiles[x][y];
                Cell cell;
                try {
                    cell = gameField.getCell(x, y);
                } catch (CellNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if (tile.isEnabled() && cell.isVisible()) {
                    tile.setEnabled(false);
                    if (cell.isHasBomb()) {
                        // Установка иконки бомбы
                        //tile.setIcon(MINE_ICON);

                        tile.setBackground(Color.RED);
                    }
                    else {
                        tile.setBackground(Color.lightGray);
                        tile.setText(cell.toString());
                    }
                }
                // Установка флажков
                if (tile.getIcon() == null && cell.isHasFlag()) {
                    tile.setIcon(FLAG_ICON);
                }
                if (tile.getIcon() != null && !cell.isHasFlag()) {
                    tile.setIcon(null);
                }
            }
        }
        if (state == Minesweeper.State.LOSE) {
            // Помечать мину красным и блокировать все кнопки поля
            for (Tile[] xTiles : tiles) {
                for (Tile tile : xTiles) {
                    if (tile.getIcon() != null)
                        tile.setIcon(null);
                    if (tile.isEnabled())
                        tile.setEnabled(false);
                }
            }
        }
        if (state == Minesweeper.State.WIN) {
            // Сообщение о победе
        }
    }

    @Override
    public void updateTimer(String time) {

    }

    @Override
    public void handleException(Exception e) {

    }
}
