package game;

import java.util.*;

import game.Map.MainMap;
import game.Player.Entities.*;
import game.Player.Player;
import game.Utils.InputHandler;
import game.Utils.Menu.GameMenu;
import game.Utils.Menu.MainMenu;

public class MainGame extends Game {
    private MainMap gameMap;
    private Player person;
    private Player computer;
    private Hero selectedHero;
    private int turnsInCastle = Integer.MAX_VALUE;
    private OwnerType invader;

    public MainGame(int n, int m) {
        super(n, m);
    }

    public void start() {
        initializeGame();
        person.getCastle().enter(); // Покупка и найм

        if (isGameOverFor(person)) MainMenu.printGameEnd(OwnerType.PERSON);
        else {
            gameMap = new MainMap(n, m, person, computer);
            gameMap.render();
            while (!isGameOverFor(person) || !isGameOverFor(computer) || turnsInCastle != 0) {
                personTurn();
                gameMap.render();
                if (isGameOverFor(computer)) {
                    MainMenu.printGameEnd(OwnerType.COMPUTER);
                    break;
                }
                computerTurn();
                gameMap.render();
                if (isGameOverFor(person)) {
                    MainMenu.printGameEnd(OwnerType.PERSON);
                    break;
                }
                if (turnsInCastle <= 2) turnsInCastle -= 1;
                if (turnsInCastle < 0) {
                    MainMenu.printGameEnd(this.invader.getEnemy());
                    break;
                }
            }
        }
    }

    private void initializeGame() {
        person = new Player(185, OwnerType.PERSON);
        computer = new Player(185, OwnerType.COMPUTER);
        computer.buyRandom();

        //TEST
        person.addHero(new Hero(HeroType.BARBARIAN, OwnerType.PERSON));
        person.getHeroes().forEach(i -> i.addUnit(new Unit(UnitType.RASCAL, OwnerType.PERSON)));
        person.getHeroes().forEach(i -> i.addUnit(new Unit(UnitType.CAVALRYMAN, OwnerType.PERSON)));
        person.getHeroes().forEach(i -> i.addUnit(new Unit(UnitType.SWORDSMAN, OwnerType.PERSON)));

        computer.addHero(new Hero(HeroType.KNIGHT, OwnerType.COMPUTER));
        computer.getHeroes().forEach(i -> i.addUnit(new Unit(UnitType.RASCAL, OwnerType.COMPUTER)));
//        computer.getHeroes().forEach(i -> i.addUnit(new Unit(UnitType.PALADIN, OwnerType.COMPUTER)));
//        computer.getHeroes().forEach(i -> i.addUnit(new Unit(UnitType.SPEARMAN, OwnerType.COMPUTER)));
        //TEST
    }

    private boolean isGameOverFor(Player owner) {
        boolean ownerHasHeroes = owner.hasHeroes();
        boolean allHeroesHaveUnits = owner.getHeroes().stream().allMatch(i -> i.getUnitsCount() > 0);
        boolean ownerHasAllBuildings = !owner.hasTavern() || !owner.hasHub();
        return !ownerHasHeroes || !allHeroesHaveUnits || ownerHasAllBuildings;
    }

    private void startInvasion(Hero hero) {
        GameMenu.printInvasion(hero);
        this.turnsInCastle = 2;
        if (this.invader == null) this.invader = hero.getOwner();
        gameMap.registerInvasion(hero);
    }

    private void personTurn() {
        boolean canAtack = false, canInvade = false;
        while (selectedHero == null) {
            selectedHero = selectHero();
        }

        int y = selectedHero.getY();
        int x = selectedHero.getX();

        HashMap<String, int[]> nearby = checkEnemies(y, x, gameMap, OwnerType.PERSON, 1);

        int[] enemyCords = nearby.get("enemy");
        int[] castleCords = nearby.get("castle");

        if (enemyCords != null) {
            canAtack = true;
        } else if (castleCords != null) {
            canInvade = true;
        }
        GameMenu.showAvailableHeroActions(enemyCords, castleCords);
        int action = InputHandler.getIntInput();

        switch (action) {
            case 1:
                while (move(selectedHero, gameMap, false)) ;
                break;
            case 2:
                GameMenu.println("Ход пропущен.");
                break;
            case 3:
                if (canAtack) {
                    startBattle(person, computer, new int[]{selectedHero.getY(), selectedHero.getX()}, enemyCords);
                    break;
                }
                GameMenu.wrongChoose();
            case 4:
                if (canInvade) {
                    startInvasion(selectedHero);
                    break;
                }
                GameMenu.wrongChoose();
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
                GameMenu.wrongChoose();
                break;
        }
    }

    private void computerTurn() {
        GameMenu.println("Ход компьютера");
        Hero computerHero = selectComputerHero();
        if (computerHero == null) {
            GameMenu.println("У компьютера нет героев для хода.");
            return;
        }

        int y = computerHero.getY();
        int x = computerHero.getX();

        HashMap<String, int[]> nearby = checkEnemies(y, x, gameMap, OwnerType.COMPUTER, 1);
        int[] enemyCords = nearby.get("enemy");
        int[] castleCords = nearby.get("castle");

        if (enemyCords != null) {
            GameMenu.println("Компьютер атакует врага!");
            startBattle(computer, person, new int[]{y, x}, enemyCords);

        } else if (castleCords != null) {
            startInvasion(computerHero);
        } else {
            GameMenu.println("Компьютер перемещает героя.");
            while (move(computerHero, gameMap, true)) ;
        }
    }

    private Hero selectHero() {
        List<Hero> heroes = person.getHeroes();
        if (heroes.isEmpty()) {
            return null;
        }

        GameMenu.chooseEntity(heroes, "Героя");

        int selected = InputHandler.getIntInput() - 1;
        if (selected >= 0 && selected < heroes.size()) {
            return heroes.get(selected);
        } else {
            GameMenu.wrongChoose();
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
        OwnerType looser = battle.start();
        if (looser == OwnerType.PERSON) selectedHero = null;
    }
}