package game.Player;

import game.Castle.Buy;
import game.Castle.Castle;
import game.Map.CellType;
import game.OwnerType;
import game.Player.Entities.Hero;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private Castle castle;
    private int gold;
    private final List<Hero> heroes = new ArrayList<>();
    private OwnerType ownerType;

    public Player(int initialGold, OwnerType ownerType) {
        this.castle = new Castle(this);
        this.gold = initialGold;
        this.ownerType = ownerType;
        this.name = ownerType.getOwner();
    }

    public String getName() {
        return name;
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

    public void addHero(Hero hero) {
        this.heroes.add(hero);
    }

    public List<Hero> getHeroes() {
        return heroes;
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

    public boolean hasCastle() {
        return castle != null;
    }

    public void displayHeroes() {
        System.out.println("Выберите Героя:");
        for (int i = 0; i < heroes.size(); i++) {
            System.out.print((i + 1) + " - ");
            heroes.get(i).display();
        }
        System.out.println("0 - Выйти");
    }

    public void showInfo() {
        System.out.println("Золото: " + gold);
        System.out.println("Герои: " + heroes.size());
        displayHeroes();
        if (hasCastle()) {
            System.out.println("Замок: " + castle.getClass().getSimpleName());
        } else {
            System.out.println("Замок: отсутствует");
        }
    }

    public Hero getHeroByCords(int[] enemyCords) {
        return heroes.stream()
                .filter(hero -> hero.getY() == enemyCords[0] && hero.getX() == enemyCords[1])
                .findFirst()
                .orElse(null);
    }

    public void kill(Hero victim) {
        this.heroes.removeIf(unit -> unit.getX() == victim.getX() && unit.getY() == victim.getY());
    }

    public OwnerType getOwnerType() {
        return ownerType;
    }

    public CellType getCellType() {
        if (this.ownerType == OwnerType.PERSON) {
            return CellType.PERSON_HERO;
        } else {
            return CellType.COMPUTER_HERO;
        }
    }

    public void buyRandom() {

    }
}