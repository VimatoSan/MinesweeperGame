package lab.game.exceptions;

public class OpeningCellWithFlagException extends GameException {
    public OpeningCellWithFlagException(String message) {
        super(message);
    }

    public OpeningCellWithFlagException() {
        this("This cell have flag, remove it to open");
    }
}
