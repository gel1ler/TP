package game.Castle.Buildings;

public abstract class Building extends BuildingBuy {
    public Building(String name, int cost) {
        super(name, cost);
    }

    public abstract void interact();
}
