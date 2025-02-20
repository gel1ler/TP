package game.Player.Entities;

import game.OwnerType;

import java.util.ArrayList;
import java.util.List;

public class Hero extends Entity {
    private int movementRange;
    private final List<Unit> units = new ArrayList<>();

    public Hero(HeroType heroType, OwnerType owner) {
        super(heroType.getName(), heroType.getCost(), heroType.getMovementPoints(), owner);
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

    public List<Unit> getUnits() {
        return this.units;
    }

    public void kill(Unit victim) {
        this.units.removeIf(unit -> unit.getX() == victim.getX() && unit.getY() == victim.getY());
    }

    public Unit getUnit(int[] enemyCoords) {
        for (Unit i : this.units) {
            if (i.getY() == enemyCoords[0] && i.getX() == enemyCoords[1])
                return i;
        }
        return null;
    }
}