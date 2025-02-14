package game.Castle.Buildings;
import game.Utils.Menu.MenuItem;

public abstract class Building extends Buy {
    public Building(String name, int cost) {
        super(name, cost);
    }

    public abstract void interact();
}
