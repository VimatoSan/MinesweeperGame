package lab.game.model;

import lab.game.exceptions.CellAlreadyOpenedException;
import lab.game.exceptions.CellNotFoundException;
import lab.game.exceptions.OpeningCellWithFlagException;
import lab.game.exceptions.UserInputException;
import lab.game.view.Observer;
// import java.lab.game.view.Observer;

import java.util.*;

public class Minesweeper extends Observable {
    private static final int DEFAULT_MINES = 10;
    private static final int DEFAULT_HEIGHT = 9;
    private static final int DEFAULT_WIDTH = 9;

    private final GameField gameField;
    private final int mines;
    private final GameTimer timer = new GameTimer();
    private State state = State.START;

    public Minesweeper() {
        this.mines = DEFAULT_MINES;
        this.gameField = new GameField(DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }

    public Minesweeper(int height, int width, int mines) {
        this.gameField = new GameField(height, width);
        this.mines = mines;
    }

    public GameField getGameField() {
        return gameField;
    }

    public enum State {
        START,
        RUNNING,
        WIN,
        LOSE
    }

    public void startGame() {
        fillArea();
        notifyObservers();
    }

    public void restartGame() {
        gameField.resetArea();
        startGame();
    }

    public void handleInputException(Exception e) {
        // Пересылка сообщений во View
        warnObservers(e);
    }

    public void markCell(int x, int y) {
        Cell cell;
        try {
            cell = getGameField().getCell(x, y);
        }
        catch (CellNotFoundException e) {
            warnObservers(e);
            return;
        }
        if (cell.isVisible()) {
            warnObservers(new CellAlreadyOpenedException("This cell already opened"));
            return;
        }
        cell.setHasFlag(!cell.isHasFlag());
        notifyObservers();
    }

    public void openCell(int x, int y) {
        if (state != State.RUNNING && state != State.START) {
            warnObservers(new UserInputException("Game not running"));
            return;
        }
        Cell cell;
        try {
            cell = gameField.getCell(x, y);
        } catch (CellNotFoundException e) {
            warnObservers(e);
            return;
        }
        if (cell.isVisible()) {
            warnObservers(new CellAlreadyOpenedException());
            return;
        }
        if (cell.isHasFlag()) {
            warnObservers(new OpeningCellWithFlagException());
            return;
        }
        open(x, y);
        
        if (state == State.START) {
            timer.setObservers(this.getObservers());
            state = State.RUNNING;
            timer.start();
        }
        notifyObservers();
    }

    private void open(int x, int y) {
        Cell cell;
        try {
            cell = getGameField().getCell(x, y);
        }
        catch (CellNotFoundException e) {
            throw new RuntimeException("Cell not found in recursive open");
            // warnObservers(e);
        }

        if (cell.isVisible()) {
            // warnObservers(new CellAlreadyOpenedException());
            return;
        }

        cell.setVisible(true);
        if (cell.isHasFlag()) {
            cell.setHasFlag(false);
        }

        // gameField.getLastOpenedCoordinates().add(new int[] {x, y});
        if (cell.isHasBomb()) {
            gameOver();
        }
        if (cell.getValue() == Cell.EMPTY) {
            // gameField.getCell(x, y).setVisible(true);
            for (int[] coords : getNeighbourCellsCoordinates(x, y))
                open(coords[0], coords[1]);
        }
        if (state == State.RUNNING && isGameWin()) {
            state = State.WIN;
        }
    }

    private void gameOver() {
        timer.stop();
        state = State.LOSE;
    }

    private boolean isGameWin() {
        int closedCellNum = 0;
        for (int x = 0; x < gameField.getWidth(); x++) {
            for (int y = 0; y < gameField.getHeight(); y++) {
                try {
                    if (!gameField.getCell(x, y).isVisible()) {
                        closedCellNum++;
                    }
                } catch (CellNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if (closedCellNum > mines)
                    return false;
            }
        }
        return closedCellNum == mines;
    }

    private ArrayList<int[]> getNeighbourCellsCoordinates(int x, int y) {
        ArrayList<int[]> enableNeighboursCoordinates = new ArrayList<>();
        int[] allNeighboursCoordinates = new int[] {
                x - 1, y - 1,
                x - 1, y,
                x - 1, y + 1,
                x, y - 1,
                x, y + 1,
                x + 1, y - 1,
                x + 1, y,
                x + 1, y + 1
        };
        int newX, newY;

        for (int i = 0; i < allNeighboursCoordinates.length; i++) {
            newX = allNeighboursCoordinates[i];
            newY = allNeighboursCoordinates[++i];
            try {
                Cell cell = gameField.getCell(newX, newY);
                if (cell == null || !cell.isVisible())
                    enableNeighboursCoordinates.add(new int[] {newX, newY});
            }
            catch (CellNotFoundException ignored) {}
        }
        return enableNeighboursCoordinates;
    }

    private void generateMines() {
        // Если мин больше половины поля то искать совбодные места под них
        if (mines > gameField.getHeight() * gameField.getWidth())
            throw new RuntimeException("So much mines in area!");
        int selectedCellsNumber = mines;
        if (mines > gameField.getHeight() * gameField.getWidth() / 2)
            selectedCellsNumber = gameField.getHeight() * gameField.getWidth() - mines;

        HashSet<Coordinate> selectedCoordinates = new HashSet<>();
        Random random = new Random();
        while (selectedCoordinates.size() < selectedCellsNumber) {
            int x = random.nextInt(gameField.getWidth());
            int y = random.nextInt(gameField.getHeight());
            selectedCoordinates.add(new Coordinate(x, y));
        }

        // System.out.println(minedCoords.size());
        if (mines == selectedCellsNumber) {
            for (Coordinate coords : selectedCoordinates) {
                Cell minedCell = new Cell(Cell.BOMB);
                try {
                    gameField.setCell(coords.getX(), coords.getY(), minedCell);
                } catch (CellNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        else {
            for (int x = 0; x < gameField.getWidth(); ++x) {
                for (int y = 0; y < gameField.getHeight(); ++y) {
                    if (!selectedCoordinates.contains(new Coordinate(x, y))) {
                        Cell minedCell = new Cell(Cell.BOMB);
                        try {
                            gameField.setCell(x, y, minedCell);
                        } catch (CellNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        // return minedCells;
    }

    private ArrayList<Cell> getNeighbourCells(int x, int y) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        for (int[] coords : getNeighbourCellsCoordinates(x, y)) {
            try {
                neighbours.add(gameField.getCell(coords[0], coords[1]));
            } catch (CellNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return neighbours;
    }

    private void fillArea() {
        generateMines();
        for (int x = 0; x < gameField.getWidth(); x++) {
            for (int y = 0; y < gameField.getHeight(); y++) {
                try {
                    if (gameField.getCell(x, y) == null) {
                        Cell cell = new Cell((int) getNeighbourCells(x, y).stream().filter(arg -> arg != null && arg.isHasBomb()).count());
                       // cell.setValue((int) getNeighbourCells(x, y).stream().filter(arg -> arg != null && arg.isHasBomb()).count());
                        gameField.setCell(x, y, cell);
                    }
                } catch (CellNotFoundException e) {
                    throw new RuntimeException();
                }
            }
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(gameField, state);
        }
    }


    public void warnObservers(Exception e) {
        for (Observer observer : observers) {
            observer.handleException(e);
        }
    }
}
