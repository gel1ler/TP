package game.Castle.Buildings;

import game.Castle.Shop;
import game.Castle.MovableBuy;
import game.Player.Heroes.Hero;
import game.Player.Player;
import game.Player.Units.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hub extends Building {
    private final Shop<MovableBuy> shop;
    private final Player player;

    public Hub(Player player) {
        super("Хаб", 25); // Вызов конструктора Building
        this.shop = new Shop<>(player, createAvailableItems(player));
        this.player = player;
    }

    private List<MovableBuy> createAvailableItems(Player player) {
        List<MovableBuy> availableUnits = new ArrayList<>();
        availableUnits.add(new Cavalryman());
        availableUnits.add(new Crossbowman());
        availableUnits.add(new Paladin());
        availableUnits.add(new Spearman());
        availableUnits.add(new Swordsman());

        return availableUnits;
    }

    public void buyUnit(Hero hero) {
        Scanner in = new Scanner(System.in);

        shop.showAvailableItems();
        int selected = in.nextInt();
        while (selected != 0) {
            MovableBuy item = shop.getAvailableItems().get(selected - 1);
            if (item instanceof Unit && player.canAfford(item)) {
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
        List<Hero> heroes = player.getHeroes();
        Scanner in = new Scanner(System.in);
        player.displayHeroes();
        int selected = in.nextInt();

        while(selected!=0) {
            buyUnit(heroes.get(selected - 1));
            player.displayHeroes();
            selected = in.nextInt();
        }
    }
}