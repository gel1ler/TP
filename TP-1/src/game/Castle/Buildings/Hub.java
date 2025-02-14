package game.Castle.Buildings;

import game.PLayer.Heroes.Barbarian;
import game.PLayer.Heroes.Hero;
import game.PLayer.Heroes.Knight;
import game.PLayer.Heroes.Wizard;
import game.PLayer.Player;
import game.PLayer.Units.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hub extends Building {
    private final Shop shop;
    private final Player player;

    public Hub(Player player) {
        super("Хаб", 25); // Вызов конструктора Building
        this.shop = new Shop(player, createAvailableItems(player));
        this.player = player;
    }

    private List<Buy> createAvailableItems(Player player) {
        List<Buy> availableUnits = new ArrayList<>();
        availableUnits.add(new Cavalryman());
        availableUnits.add(new Crossbowman());
        availableUnits.add(new Paladin());
        availableUnits.add(new Spearman());
        availableUnits.add(new Swordsman());

        return availableUnits;
    }

    public void buyUnit() {
        Scanner in = new Scanner(System.in);
        shop.showAvailableItems();

        int selected = in.nextInt();
        while (selected != 0) {
            Buy item = shop.getAvailableItems().get(selected - 1);
            if (item instanceof Unit && shop.canAfford(item)) {
                shop.buyItem(item);
                player.addUnit((Unit) item);
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
        buyUnit();
    }
}