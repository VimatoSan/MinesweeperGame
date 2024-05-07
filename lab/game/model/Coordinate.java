package lab.game.model;

public class Coordinate {
    private int x;
    private int y;

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int pow(int base, int exp) {
        int res = 1;
        for (int i = 0; i < exp; i++) {
            res *= base;
        }
        return res;
    }

    @Override
    public int hashCode() {
        return pow(2, x) * pow(3, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinate coordinate))
            return false;
        else {
            return (coordinate.getX() == this.x && coordinate.getY() == this.y);
        }
    }
}
