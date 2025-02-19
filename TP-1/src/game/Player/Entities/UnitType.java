package game.Player.Entities;

public enum UnitType {
    SWORDSMAN("Мечник", 5, 80, 15, 1, 3, 1000),
    SPEARMAN("Копейщик", 5, 50, 10, 1, 2, 1000),
    PALADIN("Паладин", 5, 100, 30, 1, 5, 1000),
    CROWSBOWMAN("Арбалетчик", 5, 30, 20, 3, 2, 1000),
    CAVALRYMAN("Кавалерист", 5, 60, 25, 2, 4, 1000);


    private final String name;
    private final int cost;
    private final int hp;
    private final int damage;
    private final int fightDist;
    private final int moveDist;
    private final int movementPoints;

    UnitType(String name, int cost, int hp, int damage, int fightDist, int moveDist, int movementPoints) {
        this.name = name;
        this.cost = cost;
        this.hp = hp;
        this.damage = damage;
        this.fightDist = fightDist;
        this.moveDist = moveDist;
        this.movementPoints = movementPoints;
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

    public int getMovementPoints() {
        return movementPoints;
    }
}