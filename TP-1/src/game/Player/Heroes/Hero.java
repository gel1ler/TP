package game.Player.Heroes;

import game.Castle.MovableBuy;
import game.Player.Units.Unit;

import java.util.ArrayList;
import java.util.List;

public class Hero extends MovableBuy {
    private int movementRange;
    private final List<Unit> units = new ArrayList<>();

    public Hero(int x, int y, String name, int cost) {
        super(x, y, name, cost);
        this.movementRange = 2;
    }

    public void increaseMovementRange(int amount) {
        movementRange += amount;
    }

    public int getMovementRange() {
        return movementRange;
    }

    public void addUnit(Unit unit) {
        this.units.add(unit);
    }

    public void display() {
        System.out.println(getName());
        if(units.isEmpty()) {
            System.out.println("\t-Без юнитов.");
        }
        else {
            units.forEach(i -> System.out.println("\t-" + i.getName()));
        }
    }
}