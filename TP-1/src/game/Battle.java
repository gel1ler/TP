package game;

import game.Map.BattleMap;
import game.Map.Map;
import game.Player.Entities.Entity;
import game.Player.Entities.Hero;
import game.Player.Entities.Unit;
import game.Player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Battle extends Game {
    private final BattleMap battleMap;
    private final Player person, computer;
    private final Hero personHero, computerHero;
    private Unit selectedUnit;
    private boolean isBattleOver;
    private OwnerType looser;
    private final Map mainMap;

    public Battle(int n, int m, Player murderer, Player victim, Hero murdererHero, Hero victimHero, Map mainMap) {
        super(n, m);
        boolean isPlayerMurderer = murderer.getOwnerType().equals(OwnerType.PERSON);
        this.person = isPlayerMurderer ? murderer : victim;
        this.computer = isPlayerMurderer ? victim : murderer;
        this.personHero = isPlayerMurderer ? murdererHero : victimHero;
        this.computerHero = isPlayerMurderer ? victimHero : murdererHero;
        this.battleMap = new BattleMap(n, m, person, computer, personHero, computerHero);
        this.mainMap = mainMap;
    }

    public OwnerType start() {
        System.out.println("\n===FIGHT===\n");

        battleMap.render();
        while (!isBattleOver) {
            personTurn();
            battleMap.render();
            computerTurn();
            battleMap.render();
        }
        return looser;
    }

    protected void setGameEnded(OwnerType owner) {
        isBattleOver = true;
        looser = owner;
        String whose = owner == OwnerType.COMPUTER ? "компьютера" : "человека";
        System.out.println("Все юниты " + whose + " уничтожены!");
        System.out.println("===FIGHT IS OVER===");
    }

    private Unit selectUnit() {
        List<Unit> units = personHero.getUnits();
        if (units.isEmpty()) {
            return null;
        }

        System.out.println("Выберите Юнита:");
        for (int i = 0; i < units.size(); i++) {
            System.out.println((i + 1) + " - " + units.get(i).getName() + " на (" + units.get(i).getX() + ", " + units.get(i).getY() + ")");
        }

        int selected = scanner.nextInt() - 1;
        if (selected >= 0 && selected < units.size()) {
            return units.get(selected);
        } else {
            System.out.println("Неверный выбор.");
            return null;
        }
    }

    private void personTurn() {
        boolean canAtack = false;

        //Пока не будет сделан выбор юнита
        while (selectedUnit == null) {
            selectedUnit = selectUnit();
        }
        int y = selectedUnit.getY();
        int x = selectedUnit.getX();

        HashMap<String, int[]> nearby = checkEnemies(y, x, battleMap, OwnerType.PERSON, selectedUnit.getFightDist());
        String helperText = "";

        int[] enemyCords = nearby.get("enemy");

        if (enemyCords != null) {
            canAtack = true;
            helperText = "3 - Атаковать\t\t";
        }

        System.out.println("Выберите действие:");
        System.out.print("1 - Переместить Юнита\t\t2 - Пропустить ход\t\t" + helperText + "0 - Выбрать другого Юнита");


        int action = scanner.nextInt();

        switch (action) {
            case 1:
                while (move(selectedUnit, battleMap, false)) ;
                break;
            case 2:
                System.out.println("Ход пропущен.");
                break;
            case 3:
                if (canAtack) {
                    Unit computerUnit = computerHero.getUnit(enemyCords);
                    attack(selectedUnit, computerUnit);
                    break;
                }
                System.out.println("Неверный выбор.");
            case 0:
                selectedUnit = null;
                return;
            default:
                System.out.println("Неверный выбор.");
                break;
        }
    }

    private void computerTurn() {
        System.out.println("Ход компьютера");

        Unit computerUnit = selectComputerUnit();

        if (computerUnit == null){
            System.out.println("Comp unit is null");
            return;
        }
        int y = computerUnit.getY();
        int x = computerUnit.getX();

        HashMap<String, int[]> nearby = checkEnemies(y, x, battleMap, OwnerType.COMPUTER, computerUnit.getFightDist());
        int[] enemyCoords = nearby.get("enemy");

        if (enemyCoords != null) {
            System.out.println("Компьютер атакует врага!");
            Unit personUnit = personHero.getUnit(enemyCoords);
            attack(computerUnit, personUnit);
        } else {
            System.out.println("Компьютер перемещает юнита.");
            moveComputerUnit(computerUnit);
        }

        if (personHero.getUnitsCount() == 0) {

        }
    }

    private Unit selectComputerUnit() {
        List<Unit> units = computerHero.getUnits();
        if (units.isEmpty()) {
            return null;
        }

        return units.getFirst();
    }

    private void moveComputerUnit(Unit unit) {
        // Простая логика перемещения: двигаться в сторону ближайшего врага
        int y = unit.getY();
        int x = unit.getX();

        // Поиск ближайшего врага
        HashMap<String, int[]> nearby = checkEnemies(y, x, battleMap, OwnerType.COMPUTER, 3);
        int[] enemyCoords = nearby.get("enemy");

        if (enemyCoords != null) {
            System.out.println("Двигается к игроку");
            int enemyY = enemyCoords[0];
            int enemyX = enemyCoords[1];

            // Определение направления движения
            int deltaY = Integer.compare(enemyY, y);
            int deltaX = Integer.compare(enemyX, x);

            int newY = y + deltaY;
            int newX = x + deltaX;

            // Проверка доступности клетки и перемещение
            if (battleMap.isCellAvailable(newY, newX, false)) {
                battleMap.moveObject(new int[]{y, x}, new int[]{newY, newX}, unit.getOwner());
                unit.setPos(newY, newX);
            }
        } else {
            System.out.println("Двигается рандомно");
            move(unit, battleMap, true);
        }
    }

    private void attack(Unit murderer, Unit victim) {
        boolean isAlive = murderer.attack(victim);
        if (!isAlive) {
            System.out.println("Юнит " + victim.getName() + " убит");
            Player killedPlayer;
            Hero killedHero;
            if (victim.getOwner() == OwnerType.PERSON) {
                killedPlayer = person;
                killedHero = personHero;
            } else {
                killedPlayer = computer;
                killedHero = computerHero;
            }

            killedHero.kill(victim);
            battleMap.kill(victim.getY(), victim.getX());

            if (killedHero.getUnitsCount() == 0) {
                setGameEnded(killedHero.getOwner());
                killedPlayer.kill(killedHero);
                mainMap.kill(killedHero.getY(), killedHero.getX());
            }
        }
    }
}