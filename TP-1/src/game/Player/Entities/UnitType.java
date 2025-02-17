package game.Player.Entities;

public enum UnitType {
    SWORDSMAN("Мечник", 5,80, 15, 1, 3),
    SPEARMAN("Копейщик", 5,50, 10, 1, 2),
    PALADIN("Паладин", 5,100, 30, 1, 5),
    CROWSBOWMAN("Арбалетчик", 5,30, 20, 3, 2),
    CAVALRYMAN("Кавалерист", 5,60, 25, 2, 4);


    private final String name;
    private final int cost;
    private final int hp;
    private final int damage;
    private final int fightDist;
    private final int moveDist;

    UnitType(String name, int cost, int hp, int damage, int fightDist, int moveDist) {
        this.name = name;
        this.cost = cost;
        this.hp = hp;
        this.damage = damage;
        this.fightDist = fightDist;
        this.moveDist = moveDist;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
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
}