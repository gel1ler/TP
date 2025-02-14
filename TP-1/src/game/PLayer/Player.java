package game.PLayer;

import game.Castle.Buildings.Building;
import game.Castle.Buildings.Hub;
import game.Castle.Buildings.Stable;
import game.Castle.Buildings.Tavern;
import game.Castle.Castle;
import game.PLayer.Heroes.Hero;
import game.PLayer.Units.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private Castle castle;
    private int gold;
    private int x;
    private int y;
    private final List<Hero> heroes = new ArrayList<>();
    private final List<Unit> units = new ArrayList<>();

    public Player(int x, int y, int initialGold) {
        this.castle = new Castle(this);
        this.x = x;
        this.y = y;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public boolean hasCastle() {
        return castle != null;
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