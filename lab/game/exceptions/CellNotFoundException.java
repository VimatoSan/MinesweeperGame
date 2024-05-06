package lab.game.exceptions;

public class CellNotFoundException extends GameException {
    public CellNotFoundException(String message) {
        super(message);
    }
    public CellNotFoundException() {
        this("Cell with this coordinates not found");
    }
}
