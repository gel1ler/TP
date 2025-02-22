package game.Castle.Buildings;

import game.Castle.Shop;
import game.OwnerType;
import game.Player.Entities.Hero;
import game.Player.Entities.Entity;
import game.Player.Entities.HeroType;
import game.Player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tavern extends Building {
    private final Shop<Entity> shop;
    private final Player owner;

    public Tavern(Player owner) {
        super("Таверна", 50, owner.getOwnerType()); // Вызов конструктора Building
        this.shop = new Shop<>(owner, createAvailableItems());
        this.owner = owner;
    }

    private List<Entity> createAvailableItems() {
        List<Entity> availableHeroes = new ArrayList<>();
        availableHeroes.add(new Hero(HeroType.BARBARIAN, null));
        availableHeroes.add(new Hero(HeroType.KNIGHT, null));
        availableHeroes.add(new Hero(HeroType.WIZARD, null));
        return availableHeroes;
    }

    public void buyHero() {
        Scanner in = new Scanner(System.in);
        shop.showAvailableItems();

        int selected = in.nextInt();
        while (selected != 0) {
            Entity item = shop.getAvailableItems().get(selected - 1);
            if (item instanceof Hero && owner.canAfford(item)) {
                shop.buyItem(item);
                owner.addHero(new Hero(((Hero) item).getHeroType(), owner.getOwnerType()));
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