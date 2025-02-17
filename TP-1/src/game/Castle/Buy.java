package game.Castle;

import game.OwnerType;

public class Buy {
    private String name;
    private int cost;
    private OwnerType owner;

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
