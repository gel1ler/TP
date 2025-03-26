package DB;

import game.Utils.Menu.Menu;

import java.io.File;

public class DBUtils {
    public static void createDirectory(String folderPath) {
        File directory = new File(folderPath);
        directory.mkdir();
    }
}
