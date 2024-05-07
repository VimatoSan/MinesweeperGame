package lab.game.view;

import lab.game.controller.ConsoleController;
import lab.game.exceptions.CellNotFoundException;
import lab.game.exceptions.GameException;
import lab.game.model.Cell;
import lab.game.model.GameField;
import lab.game.model.Minesweeper;
import lab.game.model.GameTimer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ConsoleView implements Observer {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    private String savedTime;
    private final ConsoleController controller;

    public ConsoleView(ConsoleController controller) {
        this.controller = controller;
    }

    public void printGameField(GameField gameField) {
        for (int x = 0; x < gameField.getWidth(); x++)  {
            for (int y = 0; y < gameField.getHeight(); y++) {
                try {
                    Cell cell = gameField.getCell(x, y);

                    if (cell.isVisible()) {
                        if (cell.isHasBomb())
                            System.out.print(String.format("%s%4s %s", ANSI_RED, cell, ANSI_RESET));
                        else
                            System.out.print(String.format("%s%4s %s", ANSI_GREEN, cell, ANSI_RESET));
                    }
                    else {
                        if (cell.isHasFlag()) {
                            System.out.print(String.format("%s%3dF %s", ANSI_YELLOW, x * gameField.getWidth() + y, ANSI_RESET));
                        }
                        else
                            System.out.print(String.format("%4d ", x * gameField.getWidth() + y));
                    }
                } catch (CellNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
            System.out.println();
        }
    }

    public String enterData() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            return reader.readLine();
        }
    }

    @Override
    public void update(GameField gameField, Minesweeper.State state) {
        if (state != Minesweeper.State.START)
            System.out.println("TIME: " + savedTime);
        printGameField(gameField);
        if (state == Minesweeper.State.LOSE) {
            System.out.println("\nGAME OVER!");
        }
        else if (state == Minesweeper.State.WIN) {
            System.out.println("\nCONGRATULATIONS!");
        }
        else {
            System.out.println("\nEnter number of cell:");
            controller.readUserInput();
        }
    }

    @Override
    public void handleException(Exception e) {
        if (e instanceof GameException) {
            System.err.println(e.getMessage());
            System.out.println("\nRetry enter number of cell:");
            controller.readUserInput();
        }
        else
            System.err.println(e);
        // Если модель вернула исключение его надо обработать
    }

    @Override
    public void updateTimer(String time) {
        savedTime = time;
        // Сохранение значения таймера в переменную
    }


}
