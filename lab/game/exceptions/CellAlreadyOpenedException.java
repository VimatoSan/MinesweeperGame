package lab.game.exceptions;

public class CellAlreadyOpenedException extends GameException {
   public CellAlreadyOpenedException(String message) {
       super(message);
   }

   public CellAlreadyOpenedException() {
       this("This cell already opened");
   }
}
