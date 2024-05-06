package lab.game.controller;

import lab.game.exceptions.UserInputException;
import lab.game.model.Minesweeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleController {
    Minesweeper model;
    private static final int WRONG_USER_INPUT = -1;

    public ConsoleController(Minesweeper model) {
        this.model = model;
    }


    public void readUserInput() {
        String input;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = reader.readLine();
        } catch (IOException e) {
            model.handleInputException(e);
            return;
        }
        String[] args = input.split(" ");
        if (args.length != 1 && args.length != 2) {
            // model.handleInputException();
            // Отправлять в модель исключение
        }
        int value;
        if (args.length == 2) {
            if (!args[0].equals("-f")) {
                model.handleInputException(new UserInputException("Incorrect command to set flag"));
            }
            else {
                if ((value = parseToInt(args[1])) == WRONG_USER_INPUT) {
                    model.handleInputException(new UserInputException("You enter not integer value"));
                    return;
                }
                // Заменить исключение
                int[] coords = decodeCoords(value);
                model.markCell(coords[0], coords[1]);
            }
        }
        else {
            if ((value = parseToInt(args[0])) == WRONG_USER_INPUT) {
                model.handleInputException(new UserInputException("You enter not integer value"));
                return;
            }
            int[] coords = decodeCoords(value);
            model.openCell(coords[0], coords[1]);
        }
    }

    private int[] decodeCoords(int value) {
        int x = value / model.getGameField().getWidth();
        int y = value % model.getGameField().getWidth();
        return new int[] {x, y};
    }


    private int parseToInt(String input) {
        int value = WRONG_USER_INPUT;
        try {
            value = Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            // model.handleInputException(e);
        }
        return value;
    }

//    public ConsoleController() {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        String line;
//        while (true) {
//            try {
//                if ((line = reader.readLine()) != null && !model.isStart()) {
//                    int cellNumber = Integer.parseInt(line);
//                };
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//    }

}
