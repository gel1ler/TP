package game.Player;

import game.Castle.Buy;
import game.Castle.Castle;
import game.Player.Entities.Hero;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private Castle castle;
    private int gold;
    private final List<Hero> heroes = new ArrayList<>();

    public Player(int initialGold, String name) {
        this.castle = new Castle(this);
        this.gold = initialGold;
        this.name = name;
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

    public void addHero(Hero hero){
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

    public void displayHeroes(){
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

    public Hero getEnemy(int[] enemyCoords) {
        return heroes.stream()
                .filter(i -> i.getX() == enemyCoords[0] && i.getY() == enemyCoords[1])
                .findFirst()
                .orElse(null);
    }
}