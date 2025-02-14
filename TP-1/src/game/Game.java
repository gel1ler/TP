package game;

import game.PLayer.Player;

public class Game {
    private static Map gameMap;
    private static Player player;
    private static Player computer;
    private static boolean isEnded=false;

    public static void start() {
        initializeGame();
        player.buyBuilding();
        if (!player.getCastle().hasBuilding("Таверна") || !player.getCastle().hasBuilding("Хаб")){
            setGameEnd();
        }
        else {
            gameMap = new Map(10, 10); // Инициализация карты

            System.out.println("Покупка героев...");
            System.out.println("Найм юнитов...");

            gameMap.display();

//        while (!isGameOver()) {
//            playerTurn();
//            computerTurn();
//        }
        }
    }

    private static void initializeGame() {
        player = new Player(0, 0, 85);
        computer = new Player(9, 9, 85);
    }

    private static void setGameEnd() {
        isEnded = true;
        System.out.println("---------------------- \n  G A M E   O V E R \n----------------------");
    }

    private static boolean isGameOver() {
        // Проверка условий завершения игры
        return false;
    }

    private static void playerTurn() {
        System.out.println("Ход игрока");
        // Логика хода игрока
    }

    private static void computerTurn() {
        System.out.println("Ход компьютера");
        // Логика хода компьютера
    }

    public static void main(String[] args) {
        start();
    }
}
