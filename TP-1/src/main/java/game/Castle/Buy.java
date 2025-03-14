package game.Castle;

import game.OwnerType;
import game.Utils.Menu.Menu;

public class Buy {
    private final String name;
    private final int cost;
    private OwnerType owner;

    public Buy(String name, int cost, OwnerType owner) {
        this.name = name;
        this.cost = cost;
        this.owner = owner;
    }

    public OwnerType getOwner() {
        return owner;
    }

    public void reverseOwner() {
        if(owner==OwnerType.PERSON) owner = OwnerType.COMPUTER;
        else owner = OwnerType.PERSON;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
}
