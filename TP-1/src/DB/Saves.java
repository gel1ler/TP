package DB;

import game.Game;
import game.MainGame;
import game.Utils.Menu.MainMenu;
import game.Utils.Menu.Menu;

import java.io.*;

public class Saves {
    private final static String sfPath = "db/saves/save.ser";
    public static void writeSave(MainGame game) {
        try (FileOutputStream fileOut = new FileOutputStream(sfPath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(game);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MainGame readSave() {
        MainGame mainGame;

        try (FileInputStream fileIn = new FileInputStream(sfPath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            // Десериализация объекта
            mainGame = (MainGame) in.readObject();
            Menu.println("Объект прочитан из файла: " + mainGame);
            return mainGame;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
