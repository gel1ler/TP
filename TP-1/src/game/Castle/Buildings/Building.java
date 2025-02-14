package game.Castle.Buildings;

public abstract class Building {
    private String name;
    private int cost;

    public Building(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

//    public abstract void interact(Player player);
}
