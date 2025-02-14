package game.PLayer.Heroes;

import game.Castle.Buildings.Buy;

public class Hero extends Buy {
    private int movementRange;

    public Hero(String name, int cost) {
        super(name, cost);
        this.movementRange = 2;
    }

    public void increaseMovementRange(int amount) {
        movementRange += amount;
    }

    public int getMovementRange() {
        return movementRange;
    }
}