package game;

import java.util.*;

import game.Map.MainMap;
import game.Player.Entities.*;
import game.Player.Player;

public class MainGame extends Game {
    private MainMap gameMap;
    private Player person;
    private Player computer;
    private Hero selectedHero;
    private int turnsInCastle = Integer.MAX_VALUE;

    public MainGame(int n, int m) {
        super(n, m);
    }

    @Override
    public void start() {
        initializeGame();
        person.getCastle().enter(); // Покупка и найм
        if (isGameOverFor(person) || isGameOverFor(computer)) {
            setGameEnd();
        } else {
            gameMap = new MainMap(n, m, person, computer);
            gameMap.render();

            while (!isGameOverFor(person) || !isGameOverFor(computer) || turnsInCastle == 0) {
                personTurn();
                gameMap.render();
                if (isGameOverFor(computer)) {
                    setGameEnd();
                    System.out.println("Машина проиграла...");
                    break;
                }
                System.out.println();
                computerTurn();
                gameMap.render();
                if (isGameOverFor(person)) {
                    setGameEnd();
                    System.out.println("Человек проиграл...");
                    break;
                }
                if (turnsInCastle <= 2) turnsInCastle -= 1;
            }
        }
    }

    private void initializeGame() {
        person = new Player(185, OwnerType.PERSON);
        computer = new Player(185, OwnerType.COMPUTER);

        //TEST
        person.addHero(new Hero(HeroType.BARBARIAN, OwnerType.PERSON));
        person.addHero(new Hero(HeroType.WIZARD, OwnerType.PERSON));
        person.getHeroes().forEach(i -> i.addUnit(new Unit(UnitType.PALADIN, OwnerType.PERSON)));

        computer.buyRandom();

//        computer.addHero(new Hero(HeroType.WIZARD, OwnerType.COMPUTER));
        computer.addHero(new Hero(HeroType.KNIGHT, OwnerType.COMPUTER));
        computer.getHeroes().forEach(i -> i.addUnit(new Unit(UnitType.PALADIN, OwnerType.COMPUTER)));
        //TEST


    }

    private boolean isGameOverFor(Player owner) {
        boolean ownerHasHeroes = owner.hasHeroes();
        boolean allHeroesHaveUnits = owner.getHeroes().stream().allMatch(i -> i.getUnitsCount() > 0);
        boolean ownerHasAllBuildings = !owner.hasTavern() || !owner.hasHub();
        return !ownerHasHeroes || !allHeroesHaveUnits || ownerHasAllBuildings;
    }

    private void setGameEnd() {
        isEnded = true;
        System.out.println("---------------------- \n  G A M E   O V E R \n----------------------");
    }

    private void personTurn() {
        boolean canAtack = false, canInvade = false;
        while (selectedHero == null) {
            selectedHero = selectHero();
        }

        int y = selectedHero.getY();
        int x = selectedHero.getX();

        HashMap<String, int[]> nearby = checkEnemies(y, x, gameMap, OwnerType.PERSON, 1);
        String helperText = "";

        int[] enemyCords = nearby.get("enemy");
        int[] castleCords = nearby.get("castle");

        if (enemyCords != null) {
            canAtack = true;
            helperText = "3 - Начать Битву\t\t";
        } else if (castleCords != null) {
            canInvade = true;
            helperText = "4 - Войти в замок\t\t";
        }

        System.out.println("Выберите действие:");
        System.out.print("1 - Переместить Героя\t\t2 - Пропустить ход\t\t" + helperText + "0 - Выбрать другого Героя\t\t10 - войти в замок\n");

        int action = scanner.nextInt();

        switch (action) {
            case 1:
                while (move(selectedHero, gameMap, false)) ;
                break;
            case 2:
                System.out.println("Ход пропущен.");
                break;
            case 3:
                if (canAtack) {
                    startBattle(person, computer, new int[]{selectedHero.getY(), selectedHero.getY()}, enemyCords);
                    break;
                }
                System.out.println("Неверный выбор.");
            case 4:
                if (canInvade) {
                    turnsInCastle = 2;
                    computer.getCastle().invasion(selectedHero);
                    break;
                }
                System.out.println("Неверный выбор.");
                break;
            case 0:
                selectedHero = null;
                return;
            case 10:
                person.getCastle().enter();
                gameMap.updateHeroes(0, 0);
                personTurn();
                break;

            default:
                System.out.println("Неверный выбор.");
                break;
        }
    }

    private void computerTurn() {
        System.out.println("Ход компьютера");

        Hero computerHero = selectComputerHero();
        if (computerHero == null) {
            System.out.println("У компьютера нет героев для хода.");
            return;
        }

        int y = computerHero.getY();
        int x = computerHero.getX();

        HashMap<String, int[]> nearby = checkEnemies(y, x, gameMap, OwnerType.COMPUTER, 1);
        int[] enemyCords = nearby.get("enemy");
        int[] castleCords = nearby.get("castle");

        if (enemyCords != null) {
            System.out.println("Компьютер атакует врага!");
            startBattle(computer, person, new int[]{y, x}, enemyCords);
        } else if (castleCords != null) {
            System.out.println("Компьютер захватывает замок!");
            // Логика захвата замка
        } else {
            System.out.println("Компьютер перемещает героя.");
            while (move(computerHero, gameMap, true)) ;
        }
    }

    private Hero selectHero() {
        List<Hero> heroes = person.getHeroes();
        if (heroes.isEmpty()) {
            return null;
        }

        System.out.println("Выберите героя:");
        for (int i = 0; i < heroes.size(); i++) {
            System.out.println((i + 1) + " - " + heroes.get(i).getName() + " на (" + heroes.get(i).getY() + ", " + heroes.get(i).getX() + ")");
        }

        int selected = scanner.nextInt() - 1;
        if (selected >= 0 && selected < heroes.size()) {
            return heroes.get(selected);
        } else {
            System.out.println("Неверный выбор.");
            return null;
        }
    }

    private Hero selectComputerHero() {
        List<Hero> heroes = computer.getHeroes();
        if (heroes.isEmpty()) {
            return null;
        }

        return heroes.getFirst();
    }

    private void startBattle(Player murderer, Player victim, int[] murdererCords, int[] victimCords) {
        Hero murdererHero = murderer.getHeroByCords(murdererCords);
        Hero victimHero = victim.getHeroByCords(victimCords);
        Battle battle = new Battle(n, m, murderer, victim, murdererHero, victimHero, gameMap);
        battle.start();
    }
}