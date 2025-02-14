package game.PLayer.Units;

public class Unit {
    int hp;
    int damage;
    int fightDist;
    int moveDist;

    Unit(int hp, int damage, int fightDist, int moveDist) {
        this.hp = hp;
        this.damage = damage;
        this.fightDist = fightDist;
        this.moveDist = moveDist;
    }
}

