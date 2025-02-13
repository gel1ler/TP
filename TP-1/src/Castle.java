import java.util.ArrayList;
import java.util.List;

class Castle {
    private String owner;
    private List<Building> buildings = new ArrayList<>();
//    private List<Unit> units = new ArrayList<>();
//    private List<Hero> heroes = new ArrayList<>();

    public void addBuilding(Building building) {
        buildings.add(building);
    }

//    public void addUnit(Unit unit) {List
//        units.add(unit);
//    }
//
//    public void addHero(Hero hero) {
//        heroes.add(hero);
//    }
//
//    public List<Unit> getUnits() {
//        return units;
//    }
//
//    public List<Hero> getHeroes() {
//        return heroes;
//    }

    public void displayBuildings() {
        System.out.println("Постройки в замке:");
        for (Building building : buildings) {
            System.out.println("- " + building.getName());
        }
    }
}