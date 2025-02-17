package game.Player.Entities;

import game.OwnerType;

import java.util.ArrayList;
import java.util.List;

public class Hero extends Entity {
    private int movementRange;
    private final List<Unit> units = new ArrayList<>();

    public Hero(HeroType heroType, OwnerType owner) {
        super(heroType.getName(), heroType.getCost(), owner);
        this.movementRange = heroType.getMovementRange();
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

    public int getUnitsCount() {
        return units.size();
    }

    public void display() {
        System.out.println(getName() + "(" + getX() + ", " + getY() + ")");
        if (units.isEmpty()) {
            System.out.println("\t-Без юнитов.");
        } else {
            units.forEach(i -> System.out.println("\t-" + i.getName()));
        }
    }
}