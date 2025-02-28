package game.Castle;

import game.OwnerType;

public class Buy {
    private final String name;
    private final int cost;
    private final OwnerType owner;

    public Buy(String name, int cost, OwnerType owner) {
        this.name = name;
        this.cost = cost;
        this.owner = owner;
    }

    public OwnerType getOwner() {
        return owner;
    }
    public String getName() {
        return name;
    }
    public int getCost() {
        return cost;
    }
}
