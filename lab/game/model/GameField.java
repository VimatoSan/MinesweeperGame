package lab.game.model;

import lab.game.exceptions.CellNotFoundException;


public class GameField {
    private final int height;
    private final int width;
    private Cell[][] gameArea;

    public GameField(int height, int width) {
        this.height = height;
        this.width = width;
        this.gameArea = new Cell[width][height];
    }

    public void resetArea() {
        this.gameArea = new Cell[width][height];
    }

    public void setCell(int x, int y, Cell cell) throws CellNotFoundException{
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new CellNotFoundException();
        gameArea[x][y] = cell;
    }

    public Cell getCell(int x, int y) throws CellNotFoundException{
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new CellNotFoundException();
        return gameArea[x][y];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
