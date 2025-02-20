package game.Player.Entities;

import game.OwnerType;

public class Unit extends Entity {
    int hp;
    int damage;
    int fightDist;
    int moveDist;

    //Отряд

    public Unit(UnitType unitType, OwnerType owner) {
        super(unitType.getName(), unitType.getCost(), unitType.getMovementPoints(), owner);
        this.hp = unitType.getHp();
        this.damage = unitType.getDamage();
        this.fightDist = unitType.getFightDist();
        this.moveDist = unitType.getMoveDist();
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public int getFightDist() {
        return fightDist;
    }

    public int getMoveDist() {
        return moveDist;
    }

    @Override
    public String toString() {
        return getName() + " (HP: " + hp + ", DMG: " + damage + ")";
    }

    public boolean minusHp(int damage) {
        this.hp -= damage;
        return this.hp > 0;
    }

    public boolean attack(Unit victim) {
        boolean isAlive = victim.minusHp(this.damage);
        if (isAlive) {
            System.out.println("У вражеского Юнита " + victim.getName() + " осталось " + victim.getHp() + " HP");
        }
        return isAlive;
    }
}

