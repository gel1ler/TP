package game;

import java.util.Scanner;

import game.Map.Map;
import game.Player.Entities.HeroType;
import game.Player.Entities.Hero;
import game.Player.Player;
import game.Player.Entities.UnitType;
import game.Player.Entities.Unit;

import java.util.List;

public class Game {
    public static Map gameMap;
    private static Player player;
    private static Player computer;
    private static boolean isEnded = false;
    private static Hero selectedHero;
    private static Scanner scanner = new Scanner(System.in);

    public static void start() {
        initializeGame();
        player.getCastle().enter(); //Покупка и найм
        if (isGameOver()) {
            setGameEnd();
        } else {
            gameMap = new Map(5, 5, player, computer);
            gameMap.render();

            while (!isGameOver()) {
                playerTurn();
                gameMap.render();
                computerTurn();
//                gameMap.render();
            }
        }
    }

    private static void initializeGame() {
        player = new Player(185, OwnerType.PLAYER.getOwner());
        computer = new Player(185, OwnerType.COMPUTER.getOwner());

        //TEST
        player.addHero(new Hero(HeroType.BARBARIAN, OwnerType.PLAYER));
        player.addHero(new Hero(HeroType.WIZARD, OwnerType.PLAYER));
        player.getHeroes().forEach(i -> i.addUnit(new Unit(UnitType.PALADIN, OwnerType.PLAYER)));

        computer.addHero(new Hero(HeroType.WIZARD, OwnerType.COMPUTER));
        computer.addHero(new Hero(HeroType.KNIGHT, OwnerType.COMPUTER));
        computer.getHeroes().forEach(i -> i.addUnit(new Unit(UnitType.PALADIN, OwnerType.COMPUTER)));
        //TEST


    }

    private static void setGameEnd() {
        isEnded = true;
        System.out.println("---------------------- \n  G A M E   O V E R \n----------------------");
    }

    private static boolean isGameOver() {
        boolean allHeroesHaveUnits = player.getHeroes().stream().allMatch(i -> i.getUnitsCount() > 0);
        return !player.hasTavern() || !player.hasHub() || !player.hasHeroes() || !allHeroesHaveUnits;
    }

    private static void playerTurn() {
        //Если не выбрана клетка то выбираем героя. Потом выбираем ход
        if (selectedHero == null) {
            selectedHero = selectHero();
        }

        System.out.println("Выберите действие:");
        System.out.print("1 - Переместить Героя\t\t2 - Атаковать\t\t3 - Пропустить ход\t\t0 - Выбрать другого Героя");

        int action = scanner.nextInt();

        switch (action) {
            case 1:
                while(!selectedHero.move());
                selectedHero.checkEnemies();
                break;
            case 2:
//                attack(selectedUnit);
                break;
            case 3:
                System.out.println("Ход пропущен.");
                break;
            case 0:
                selectedHero = null;
                return;
            default:
                System.out.println("Неверный выбор.");
                break;
        }
    }

    private static Hero selectHero() {
        List<Hero> heroes = player.getHeroes();
        if (heroes.isEmpty()) {
            return null;
        }

        System.out.println("Выберите героя:");
        for (int i = 0; i < heroes.size(); i++) {
            System.out.println((i + 1) + " - Герой на (" + heroes.get(i).getX() + ", " + heroes.get(i).getY() + ")");
        }

        int selected = scanner.nextInt() - 1;
        if (selected >= 0 && selected < heroes.size()) {
            return heroes.get(selected);
        } else {
            System.out.println("Неверный выбор.");
            return null;
        }
    }

    private static void computerTurn() {
        System.out.println("Ход компьютера");
        // Логика хода компьютера
    }

    public static void main(String[] args) {
        start();
    }
}
