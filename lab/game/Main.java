package lab.game;

import lab.game.controller.ConsoleController;
import lab.game.controller.GraphicController;
import lab.game.model.Minesweeper;
import lab.game.view.ConsoleView;
import lab.game.view.GraphicView;
import lab.game.view.Observer;

// TODO: Разные цвета для разных цифр
//       Кнопка перезапуска
//       Таймер на экране GUI

public class Main {
    public static void main(String[] args) {
        Minesweeper model;

        if (args.length != 1 && args.length != 4) {
            System.err.println("Not enough parameters");
            return;
        }

        if (args.length == 4) {
            model = new Minesweeper(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        }
        else {
            model = new Minesweeper();
        }
        Observer view;
        if (args[0].equals("-g")) {
            GraphicController graphicController = new GraphicController(model);
            view = new GraphicView(model.getGameField(), graphicController);
        } else if (args[0].equals("-t")) {
            ConsoleController controller = new ConsoleController(model);
            view = new ConsoleView(controller);
        }
        else {
            System.err.println("Incorrect first parameter");
            return;
        }
        model.addObserver(view);
        model.startGame();
    }
}