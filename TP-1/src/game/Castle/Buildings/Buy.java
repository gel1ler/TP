package game.Castle.Buildings;

public class Buy {
    private String name;
    private int cost;

    public Buy(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }
    public int getCost() {
        return cost;
    }

}
