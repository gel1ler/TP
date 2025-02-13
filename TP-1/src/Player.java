class Player {
    private Castle castle;
    private int gold;

    public Player(int initialGold) {
        this.castle = new Castle();
        this.gold = initialGold;
    }

    public Castle getCastle() {
        return castle;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void build(Building building) {
        if (gold >= building.getCost()) {
            castle.addBuilding(building);
            gold -= building.getCost();
            System.out.println("Построено: " + building.getName());
        } else {
            System.out.println("Недостаточно золота для постройки: " + building.getName());
        }
    }
}