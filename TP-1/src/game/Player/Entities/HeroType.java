package game.Player.Entities;

public enum HeroType {
    KNIGHT("Рыцарь", 80, 3, 1000), // name, cost, movementRange
    WIZARD("Маг", 100, 3, 1000),
    BARBARIAN("Варвар", 60, 2, 1000);

    private final String name;
    private final int cost;
    private final int movementRange;
    private final int movementPoints;


    HeroType(String name, int cost, int movementRange, int movementPoints) {
        this.name = name;
        this.cost = cost;
        this.movementRange = movementRange;
        this.movementPoints=movementPoints;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getMovementRange() {
        return movementRange;
    }

    public int getMovementPoints() {
        return movementPoints;
    }
}