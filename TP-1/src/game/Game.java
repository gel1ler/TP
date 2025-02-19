package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import game.Map.BattleMap;
import game.Map.MainMap;
import game.Map.Map;
import game.Player.Entities.*;
import game.Player.Player;

import java.util.List;

public class Game {
    public MainMap gameMap;
    private Player player;
    private Player computer;
    private boolean isEnded = false;
    private Hero selectedHero;
    private Scanner scanner = new Scanner(System.in);
    private int n = 5, m = 5;

    public Game() {
    }

    public void start() {
        initializeGame();
        player.getCastle().enter(); //Покупка и найм
        if (isGameOver()) {
            setGameEnd();
        } else {
            gameMap = new MainMap(n, m, player, computer);
            gameMap.render();

            while (!isGameOver()) {
                playerTurn();
                gameMap.render();
                computerTurn();
//                gameMap.render();
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
        return !player.hasTavern() || !player.hasHub() || !player.hasHeroes() || !allHeroesHaveUnits;
    }

    private void playerTurn() {
        boolean canAtack = false, canInvade = false;
        if (selectedHero == null) {
            selectedHero = selectHero();
        }
        HashMap<String, int[]> nearby = checkEnemies();
        String helperText = "";

        int[] enemyCoords = nearby.get("enemy");
        int[] castleCoords = nearby.get("castle");

        if (enemyCoords != null) {
            canAtack = true;
            helperText = "3 - Атаковать\t\t";
        } else if (castleCoords != null) {
            canInvade = true;
            helperText = "4 - Войти в замок\t\t";
        }

        System.out.println("Выберите действие:");
        System.out.print("1 - Переместить Героя\t\t2 - Пропустить ход\t\t" + helperText + "0 - Выбрать другого Героя");

        int action = scanner.nextInt();

        switch (action) {
            case 1:
                while (move(selectedHero)) ;
                break;
            case 2:
                System.out.println("Ход пропущен.");
                break;
            case 3:
                if (canAtack) {
                    attack(enemyCoords);
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

    private void computerTurn() {
        System.out.println("Ход компьютера");
        // Логика хода компьютера
    }

    public boolean move(Entity entity) {
        System.out.println("Выберите направление:");
        System.out.println("7  8  9");
        System.out.println("4     6");
        System.out.println("1  2  3");
        int direction = scanner.nextInt();

        int newX = entity.getX();
        int newY = entity.getY();
        boolean isDiagonal = false;

        switch (direction) {
            case 8: // Вверх
                newX--;
                break;
            case 2: // Вниз
                newX++;
                break;
            case 4: // Влево
                newY--;
                break;
            case 6: // Вправо
                newY++;
                break;
            case 7: // Вверх-влево
                newX--;
                newY--;
                isDiagonal = true;
                break;
            case 9: // Вверх-вправо
                newX--;
                newY++;
                break;
            case 1: // Вниз-влево
                newX++;
                newY--;
                isDiagonal = true;
                break;
            case 3: // Вниз-вправо
                newX++;
                newY++;
                isDiagonal = true;
                break;
            default:
                System.out.println("Неверное направление.");
        }

        double cost = gameMap.getPenalty(newX, newY);
        cost *= isDiagonal ? Math.sqrt(2) : 1;
        if (gameMap.isCellAvailable(newX, newY) && entity.isEnoughMP((int) cost)) {
            entity.minusMP((int) cost);
            gameMap.moveObject(new int[]{entity.getX(), entity.getY()}, new int[]{newX, newY});
            entity.setPos(newX, newY);
            return false;
        }
        return true;
    }

    public HashMap<String, int[]> checkEnemies() {
        int x = selectedHero.getX();
        int y = selectedHero.getY();
        List<Integer> coords = new ArrayList<>();
        HashMap<String, int[]> nearby = new HashMap<>();


        // Проверяем все соседние клетки (включая диагонали)
        for (int i = Math.max(x - 1, 0); i <= Math.min(x + 1, m - 1); i++) {
            for (int j = Math.max(y - 1, 0); j <= Math.min(y + 1, n - 1); j++) {
                // Пропускаем текущую клетку героя
                if (i == x && j == y) {
                    continue;
                }

                if (gameMap.isEnemyCastle(j, i, selectedHero.getOwner()))
                    nearby.put("castle", new int[]{j, i});

                if (gameMap.isEnemy(j, i, selectedHero.getOwner())) {
                    nearby.put("enemy", new int[]{j, i});
                }
            }
        }
        return nearby;
    }

    public void attack(int[] enemyCoords) {
        Hero enemy = computer.getEnemy(enemyCoords);
        Map battleMap = new BattleMap(n, m, player, computer, selectedHero, enemy);
        battleMap.render();

        boolean isBattleOver = false;

        while (!isBattleOver) {
            playerTurn();
            battleMap.render();
            computerTurn();
//                gameMap.render();
        }

        System.out.println("===FIGHT===");
    }
}