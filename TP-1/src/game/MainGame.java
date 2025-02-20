package game;

import java.util.*;

import game.Map.MainMap;
import game.Player.Entities.*;
import game.Player.Player;

public class MainGame extends Game {
    private MainMap gameMap;
    private Player player;
    private Player computer;
    private Hero selectedHero;

    public MainGame(int n, int m) {
        super(n, m);
    }

    @Override
    public void start() {
        initializeGame();
        player.getCastle().enter(); // Покупка и найм
        if (isGameOver()) {
            setGameEnd();
        } else {
            gameMap = new MainMap(n, m, player, computer);
            gameMap.render();

            while (!isGameOver()) {
                playerTurn();
                gameMap.render();
                computerTurn();
            }
        }
    }

    private void initializeGame() {
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

    private void setGameEnd() {
        isEnded = true;
        System.out.println("---------------------- \n  G A M E   O V E R \n----------------------");
    }

    private boolean isGameOver() {
        boolean allHeroesHaveUnits = player.getHeroes().stream().allMatch(i -> i.getUnitsCount() > 0);
        //Проверка компьютера
        return !player.hasTavern() || !player.hasHub() || !player.hasHeroes() || !allHeroesHaveUnits;
    }

    private void playerTurn() {
        boolean canAtack = false, canInvade = false;
        if (selectedHero == null) {
            selectedHero = selectHero();
        }

        int y = selectedHero.getY();
        int x = selectedHero.getX();

        HashMap<String, int[]> nearby = checkEnemies(y, x, gameMap, OwnerType.PLAYER);
        String helperText = "";

        int[] enemyCoords = nearby.get("enemy");
        int[] castleCoords = nearby.get("castle");
        System.out.println(Arrays.toString(enemyCoords));

        if (enemyCoords != null) {
            canAtack = true;
            helperText = "3 - Начать Битву\t\t";
        } else if (castleCoords != null) {
            canInvade = true;
            helperText = "4 - Войти в замок\t\t";
        }

        System.out.println("Выберите действие:");
        System.out.print("1 - Переместить Героя\t\t2 - Пропустить ход\t\t" + helperText + "0 - Выбрать другого Героя");

        int action = scanner.nextInt();

        switch (action) {
            case 1:
                while (move(selectedHero, gameMap)) ;
                break;
            case 2:
                System.out.println("Ход пропущен.");
                break;
            case 3:
                if (canAtack) {
                    startBattle(player, computer, enemyCoords);
                    break;
                }
                System.out.println("Неверный выбор.");
            case 4:
                if (canInvade) {
                    break;
                }
                System.out.println("Неверный выбор.");
                break;
            case 0:
                selectedHero = null;
                return;
            default:
                System.out.println("Неверный выбор.");
                break;
        }
    }

    private Hero selectHero() {
        List<Hero> heroes = player.getHeroes();
        if (heroes.isEmpty()) {
            return null;
        }

        System.out.println("Выберите героя:");
        for (int i = 0; i < heroes.size(); i++) {
            System.out.println((i + 1) + " - Герой на (" + heroes.get(i).getY() + ", " + heroes.get(i).getX() + ")");
        }

        int selected = scanner.nextInt() - 1;
        if (selected >= 0 && selected < heroes.size()) {
            return heroes.get(selected);
        } else {
            System.out.println("Неверный выбор.");
            return null;
        }
    }

    private void computerTurn() {
        System.out.println("Ход компьютера");
        // Логика хода компьютера
    }

    private void startBattle(Player murderer, Player victim, int[] enemyCoords) {
        Hero enemy = victim.getEnemy(enemyCoords);
        Battle battle = new Battle(n, m, murderer, victim, selectedHero, enemy, gameMap);
        battle.start();
    }
}