package game.Castle.Buildings;

import game.Castle.Shop;
import game.Player.Heroes.Barbarian;
import game.Player.Heroes.Hero;
import game.Player.Heroes.Knight;
import game.Player.Heroes.Wizard;
import game.Castle.MovableBuy;
import game.Player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tavern extends Building {
    private final Shop<MovableBuy> shop;
    private final Player player;

    public Tavern(Player player) {
        super("Таверна", 50); // Вызов конструктора Building
        this.shop = new Shop<>(player, createAvailableItems(player));
        this.player = player;
    }

    private List<MovableBuy> createAvailableItems(Player player) {
        List<MovableBuy> availableHeroes = new ArrayList<>();
        availableHeroes.add(new Barbarian());
        availableHeroes.add(new Knight());
        availableHeroes.add(new Wizard());
        return availableHeroes;
    }

    public void buyHero() {
        Scanner in = new Scanner(System.in);
        shop.showAvailableItems();

        int selected = in.nextInt();
        while (selected != 0) {
            MovableBuy item = shop.getAvailableItems().get(selected - 1);
            if (item instanceof Hero && player.canAfford(item)) {
                shop.buyItem(item);
                player.addHero((Hero) item);
            } else {
                System.out.println("Недостаточно золота для покупки: " + item.getName());
            }
            shop.showAvailableItems();
            selected = in.nextInt();
        }
    }

    @Override
    public void interact() {
        System.out.println("Вы вошли в Таверну.");
        buyHero();
    }
}