package game.Player.Entities;

public enum HeroType {
    KNIGHT("Рыцарь", 80, 280),
    WIZARD("Маг", 100, 250),
    BARBARIAN("Варвар", 60, 300);

    private final String name;
    private final int cost;
    private final int movementPoints;


    HeroType(String name, int cost, int movementPoints) {
        this.name = name;
        this.cost = cost;
        this.movementPoints=movementPoints;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getMovementPoints() {
        return movementPoints;
    }
}