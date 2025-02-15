package game.Player.Units;

import game.Castle.MovableBuy;

public class Unit extends MovableBuy {
    int hp;
    int damage;
    int fightDist;
    int moveDist;

    //Отряд

    Unit(int x, int y, String name, int cost, int hp, int damage, int fightDist, int moveDist) {
        super(x, y, name, cost);
        this.hp = hp;
        this.damage = damage;
        this.fightDist = fightDist;
        this.moveDist = moveDist;
    }
}

