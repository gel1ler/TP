package game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Начать игру?");
        System.out.println("1 - Да\t\t0 - нет");
        int selected = scanner.nextInt();
        while (selected != 0) {
            System.out.println("=====================================\n=====================================");
            System.out.println("Н О В А Я   И Г Р А   Н А Ч А Л А С Ь");
            System.out.println("=====================================\n=====================================");
            MainGame game = new MainGame(6, 6);
            game.start();
            System.out.println("Сыграть еще раз?");
            System.out.println("1 - Да\t\t0 - нет");
            selected = scanner.nextInt();
        }

        System.out.println("==========================\n==========================\n==========================");
        System.out.println("==== G A M E  O V E R ====");
        System.out.println("==========================\n==========================\n==========================");
    }
}