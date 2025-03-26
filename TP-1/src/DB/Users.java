package DB;

import game.Utils.Menu.GameMenu;
import game.Utils.Menu.Menu;

import java.io.*;
import java.util.Objects;

import static DB.DBUtils.createDirectory;

public class Users {

    public static void register(String name) throws IOException {
        boolean isRegistered = checkIsRegistered(name);

        if (isRegistered) Menu.println("Вы вошли под именем " + name);
        else {
            FileWriter usersFile = new FileWriter(DbPaths.USERS.getPath(), true);
            usersFile.append(name).append("\n");
            usersFile.close();
            createDirectory(DbPaths.SAVES.getPath() + name);
            createDirectory(DbPaths.MAPSAVES.getPath() + name);
            Menu.println("Вы зарегистрировались под именем " + name);
        }
    }

    private static boolean checkIsRegistered(String name) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(DbPaths.USERS.getPath()));
        String line;
        while ((line = br.readLine()) != null) {
            if (Objects.equals(name, line)) {
                return true;
            }
        }

        return false;
    }
}
