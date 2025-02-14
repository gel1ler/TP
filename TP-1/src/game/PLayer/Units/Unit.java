package game.PLayer.Units;

import game.Castle.Buildings.Buy;

public class Unit extends Buy {
    int hp;
    int damage;
    int fightDist;
    int moveDist;

    Unit(String name, int cost, int hp, int damage, int fightDist, int moveDist) {
        super(name, cost);
        this.hp = hp;
        this.damage = damage;
        this.fightDist = fightDist;
        this.moveDist = moveDist;
    }
}

