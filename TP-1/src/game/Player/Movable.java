package game.Player;

import game.Castle.Castle;

public class Movable {
    private int x;
    private int y;

    public Movable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }
}
