package game.Player;

import game.Castle.Buy;
import game.Castle.Castle;
import game.Player.Heroes.Hero;
import game.Player.Units.Unit;

import java.util.ArrayList;
import java.util.List;

public class Player extends Movable {
    private Castle castle;
    private int gold;
    private int x;
    private int y;
    private final List<Hero> heroes = new ArrayList<>();
    private final List<Unit> units = new ArrayList<>();

    public Player(int x, int y, int initialGold) {
        super(x, y);
        this.castle = new Castle(this);
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

    public void plusGold(int gold) {
        this.gold += gold;
    }

    public void minusGold(int gold) {
        this.gold -= gold;
    }

    public boolean canAfford(Buy item) {
        return getGold() >= item.getCost();
    }

    public void addHero(Hero hero){
        this.heroes.add(hero);
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void addUnit(Unit unit){
        this.units.add(unit);
    }

    public List<Unit> getUnits() {
        return units;
    }

    public boolean hasTavern() {
        return this.castle.hasBuilding("Таверна");
    }
    public boolean hasHub() {
        return this.castle.hasBuilding("Хаб");
    }
    public boolean hasHeroes() {
        return !getHeroes().isEmpty();
    }
    public boolean hasUnits() {
        return !getUnits().isEmpty();
    }
    public boolean hasCastle() {
        return castle != null;
    }

    public void displayHeroes(){
        System.out.println("Выберите Героя:");
        for (int i = 0; i < heroes.size(); i++) {
            System.out.print((i + 1) + " - ");
            heroes.get(i).display();
        }
        System.out.println("0 - Выйти");
    }

    public void displayInfo() {
        System.out.println("Игрок: (" + x + ", " + y + ")");
        System.out.println("Золото: " + gold);
        System.out.println("Герои: " + heroes.size());
        System.out.println("Юниты: " + units.size());
        if (hasCastle()) {
            System.out.println("Замок: " + castle.getClass().getSimpleName());
        } else {
            System.out.println("Замок: отсутствует");
        }
    }
}