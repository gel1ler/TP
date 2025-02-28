package game.Player.Entities;

public enum UnitType {
    SWORDSMAN("Мечник", 10, 80, 15, 1, 150),
    SPEARMAN("Копейщик", 20, 50, 10, 2, 200),
    PALADIN("Паладин", 30, 100, 50, 3, 300),
    CROSSBOWMAN("Арбалетчик", 40, 30, 20, 3, 350),
    CAVALRYMAN("Кавалерист", 50, 60, 25, 2, 400);


    private final String name;
    private final int cost;
    private final int hp;
    private final int damage;
    private final int fightDist;
    private final int movementPoints;

    UnitType(String name, int cost, int hp, int damage, int fightDist, int movementPoints) {
        this.name = name;
        this.cost = cost;
        this.hp = hp;
        this.damage = damage;
        this.fightDist = fightDist;
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

    public int getMovementPoints() {
        return movementPoints;
    }
}