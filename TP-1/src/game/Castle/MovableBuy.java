package game.Castle;

import game.Castle.Buy;

public class MovableBuy extends Buy {
    private int x;
    private int y;

    public MovableBuy(int x, int y, String name, int cost) {
        super(name, cost);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
