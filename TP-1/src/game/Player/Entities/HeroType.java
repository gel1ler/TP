package game.Player.Entities;

public enum HeroType {
    KNIGHT("Рыцарь", 80, 3), // name, cost, movementRange
    WIZARD("Маг", 100, 3),
    BARBARIAN("Варвар", 60, 2);

    private final String name;
    private final int cost;
    private final int movementRange;

    HeroType(String name, int cost, int movementRange) {
        this.name = name;
        this.cost = cost;
        this.movementRange = movementRange;
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
}