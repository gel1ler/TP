package game.Player.Entities;

import game.Map.CellType;
import game.OwnerType;
import game.Utils.Menu.GameMenu;

public class Unit extends Entity {
    int hp, damage, fightDist, recruitedNumber = 0;
    UnitType unitType;

    public Unit(UnitType unitType, OwnerType owner) {
        super(unitType.getName(), unitType.getCost(), unitType.getMovementPoints(), unitType.getCellType(), owner);
        this.unitType = unitType;
        this.hp = unitType.getHp();
        this.damage = unitType.getDamage();
        this.fightDist = unitType.getFightDist();

    }
    public int getHp() {
        return hp;
    }

    public int getFightDist() {
        return fightDist;
    }
    public int getDamage() {
        return damage;
    }
    public UnitType getUnitType() {
        return unitType;
    }
    public boolean haveSuperAbility(){
        return this.unitType.getSuperAbility() != null;
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
            String shown = victim.getOwner().equals(OwnerType.PERSON) ? "вашего" : "вражеского";
            GameMenu.println("У " + shown + " Юнита " + victim.getName() + " осталось " + victim.getHp() + " HP");
        }
        return isAlive;
    }

    public void incrementRecruitedNumber() {
        this.recruitedNumber++;
    }
    public int getRecruitedNumber() {
        return recruitedNumber;
    }
}

