package game.PLayer;

import game.Castle.Buildings.Building;
import game.Castle.Buildings.Hub;
import game.Castle.Buildings.Stable;
import game.Castle.Buildings.Tavern;
import game.Castle.Castle;
import game.PLayer.Units.Unit;

import java.util.Scanner;

public class Player {
    private Castle castle;
    private int gold;
    private int x;
    private int y;
    private Unit[] units;

    public Player(int x, int y, int initialGold) {
        this.castle = new Castle();
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

    private void build(Building building) {
        if (gold >= building.getCost()) {
            castle.addBuilding(building);
            gold -= building.getCost();
            System.out.println("Построено: " + building.getName());
        } else {
            System.out.println("Недостаточно золота для постройки: " + building.getName());
        }
    }

    private void displayBuyMenu() {
        System.out.println("----------\nКоличество золота:" + gold);
        System.out.println("\nВыберите здание для покупки:");
        System.out.println("1 - Таверна - 50");
        System.out.println("2 - Здание покупки юнитов (Хаб) - 25");
        System.out.println("3 - Конюшня - 50");
        System.out.println("Или введите 0 для выхода из меню покупки");
    }

    public void buyBuilding() {
        Scanner in = new Scanner(System.in);
        displayBuyMenu();
        int selected = in.nextInt();
        while (selected != 0) {
            switch (selected) {
                case 1:
                    build(new Tavern());
                    break;
                case 2:
                    build(new Hub());
                    break;
                case 3:
                    build(new Stable());
                    break;
                default:
                    System.out.println("Введено неправильное значение");
                    break;
            }
            displayBuyMenu();
            selected = in.nextInt();
        }
    }

    public void buyHero() {
        Scanner in = new Scanner(System.in);
        displayBuyMenu();
        int selected = in.nextInt();
        while (selected != 0) {
            switch (selected) {
                case 1:
                    build(new Tavern());
                case 2:
                    build(new Hub());
                case 3:
                    build(new Stable());
                default:
                    System.out.println("Введено неправильное значение");
            }
            displayBuyMenu();
            selected = in.nextInt();
        }
    }
}