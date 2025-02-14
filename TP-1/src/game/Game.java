package game;

import game.PLayer.Player;

public class Game {
    private static Map gameMap;
    private static Player player;
    private static Player computer;
    private static boolean isEnded = false;

    public static void start() {
        initializeGame();
        player.getCastle().enter(); //Покупка и найм
        if (!isGameOver()) {
            setGameEnd();
        } else {
            gameMap = new Map(9, 9);
            gameMap.display();

//        while (!isGameOver()) {
//            playerTurn();
//            computerTurn();
//        }
        }
    }

    private static void initializeGame() {
        player = new Player(0, 0, 185);
        computer = new Player(9, 9, 185);
    }

    private static void setGameEnd() {
        isEnded = true;
        System.out.println("---------------------- \n  G A M E   O V E R \n----------------------");
    }

    private static boolean isGameOver() {
        return !player.hasTavern() || !player.hasHub() || !player.hasHeroes() || !player.hasUnits();
    }

//    private static boolean isGameFinished(Player player, Player computer) {
//        // Игра завершается, если один из замков захвачен
//        return player.getCastle().isCaptured() || computer.getCastle().isCaptured();
//    }

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
