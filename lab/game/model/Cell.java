package lab.game.model;

public class Cell {
    // private final int x, y;
    private final int value;
    private boolean isVisible;

    public boolean isHasFlag() {
        return isHasFlag;
    }

    public void setHasFlag(boolean hasFlag) {
        isHasFlag = hasFlag;
    }

    private boolean isHasFlag;

    public static int BOMB = -1;
    public static int EMPTY = 0;

    public Cell(int value) {
        this.value = value;
    }

//    public Cell(boolean hasBomb) {
//        if (hasBomb)
//            value = BOMB;
//        // this.hasBomb = hasBomb;
//        isVisible = false;
//    }

    public int getValue() {
        return value;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isVisible() {
        return isVisible;
    }



    public boolean isHasBomb() {
        return value == BOMB;
    }

    @Override
    public String toString() {
        if (isHasBomb()) return "X";
        else return String.valueOf(value);
    }

//    @Override
//    public int hashCode() {
//        return (int) (Math.pow(2, x) * Math.pow(3, y));
//    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj.getClass() != this.getClass())
//            return false;
//        else return this.y == ((Cell) obj).getY() && this.x == ((Cell) obj).getX() && this.hasBomb == ((Cell) obj).hasBomb;
//    }
}