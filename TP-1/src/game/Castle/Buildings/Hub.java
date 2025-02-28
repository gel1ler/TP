package game.Castle.Buildings;

import game.Castle.Shop;
import game.Player.Entities.Entity;
import game.Player.Entities.Hero;
import game.Player.Entities.UnitType;
import game.Player.Player;
import game.Player.Entities.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hub extends Building {
    private final Shop<Entity> shop;
    private final Player owner;

    public Hub(Player owner) {
        super("Хаб", 25, owner.getOwnerType());
        this.shop = new Shop<>(owner, createAvailableItems());
        this.owner = owner;
    }

    private List<Entity> createAvailableItems() {
        List<Entity> availableUnits = new ArrayList<>();
        availableUnits.add(new Unit(UnitType.CAVALRYMAN, null));
        availableUnits.add(new Unit(UnitType.CROSSBOWMAN, null));
        availableUnits.add(new Unit(UnitType.PALADIN, null));
        availableUnits.add(new Unit(UnitType.SPEARMAN, null));
        availableUnits.add(new Unit(UnitType.SWORDSMAN, null));

        return availableUnits;
    }

    public void buyUnit(Hero hero) {
        Scanner in = new Scanner(System.in);

        shop.showAvailableItems();
        int selected = in.nextInt();
        while (selected != 0) {
            Entity item = shop.getAvailableItems().get(selected - 1);
            if (item instanceof Unit && owner.canAfford(item)) {
                shop.buyItem(item);
                hero.addUnit((Unit) item);
            } else {
                System.out.println("Недостаточно золота для покупки: " + item.getName());
            }
            shop.showAvailableItems();
            selected = in.nextInt();
        }
    }

    @Override
    public void interact() {
        System.out.println("Вы вошли в Хаб.");
        List<Hero> heroes = owner.getHeroes();
        Scanner in = new Scanner(System.in);
        owner.displayHeroes();
        int selected = in.nextInt();

        while (selected != 0) {
            buyUnit(heroes.get(selected - 1));
            owner.displayHeroes();
            selected = in.nextInt();
        }
    }
}