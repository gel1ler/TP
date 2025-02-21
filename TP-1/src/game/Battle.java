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
    private BattleMap battleMap;
    private Player player, computer;
    private Hero playerHero, computerHero;
    private Unit selectedUnit;
    private boolean isBattleOver;
    private Map mainMap;

    public Battle(int n, int m, Player player, Player computer, Hero playerHero, Hero computerHero, Map mainMap) {
        super(n, m);
        this.player = player;
        this.computer = computer;
        this.playerHero = playerHero;
        this.computerHero = computerHero;
        this.battleMap = new BattleMap(n, m, player, computer, playerHero, computerHero);
        this.mainMap = mainMap;
    }

    @Override
    public void start() {
        System.out.println("\n===FIGHT===\n");

        battleMap.render();
        while (!isBattleOver) {
            playerTurn();
            battleMap.render();
        }
        System.out.println("===FIGHT IS OVER===");
    }

    private Unit selectUnit() {
        List<Unit> units = playerHero.getUnits();
        if (units.isEmpty()) {
            return null;
        }

        System.out.println("Выберите Юнита:");
        for (int i = 0; i < units.size(); i++) {
            System.out.println((i + 1) + " - Юнит на (" + units.get(i).getX() + ", " + units.get(i).getY() + ")");
        }

        int selected = scanner.nextInt() - 1;
        if (selected >= 0 && selected < units.size()) {
            return units.get(selected);
        } else {
            System.out.println("Неверный выбор.");
            return null;
        }
    }

    private void playerTurn() {
        boolean canAtack = false, canInvade = false;

        //Пока не будет сделан выбор юнита
        while (selectedUnit == null) {
            selectedUnit = selectUnit();
        }
        int y = selectedUnit.getY();
        int x = selectedUnit.getX();

        HashMap<String, int[]> nearby = checkEnemies(y, x, battleMap, OwnerType.PLAYER);
        String helperText = "";

        int[] enemyCoords = nearby.get("enemy");

        if (enemyCoords != null) {
            canAtack = true;
            helperText = "3 - Атаковать\t\t";
        }

        System.out.println("Выберите действие:");
        System.out.print("1 - Переместить Юнита\t\t2 - Пропустить ход\t\t" + helperText + "0 - Выбрать другого Юнита");


        int action = scanner.nextInt();

        switch (action) {
            case 1:
                while (move(selectedUnit, battleMap)) ;
                break;
            case 2:
                System.out.println("Ход пропущен.");
                break;
            case 3:
                if (canAtack) {
                    Unit computerUnit = computerHero.getUnit(enemyCoords);
                    attack(selectedUnit, computerUnit);
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
                selectedUnit = null;
                return;
            default:
                System.out.println("Неверный выбор.");
                break;
        }
    }

    private void attack(Unit murderer, Unit victim) {
        boolean isAlive = murderer.attack(victim);
        if (!isAlive) {
            System.out.println("Юнит " + victim.getName() + " убит");
            Player tempPlayer;
            Hero tempHero;
            if (victim.getOwner() == OwnerType.PLAYER) {
                tempPlayer = player;
                tempHero = playerHero;
            } else {
                tempPlayer = computer;
                tempHero = computerHero;
            }

            tempHero.kill(victim);

            if (tempHero.getUnitsCount() == 0) {
                isBattleOver = true;
                tempPlayer.kill(tempHero);
                mainMap.kill(tempHero.getY(), tempHero.getX());
            }

            battleMap.kill(victim.getY(), victim.getX());
        }
    }
}