package DB.Saves;

import game.Utils.InputHandler;
import game.Utils.Menu.Menu;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static DB.DBUtils.getFiles;
import static DB.DBUtils.getFilesNames;
import static game.Utils.Menu.Menu.displayArrays;

public class Save {
    protected static File getSaveFile(String folderPath) {
        File[] saveFiles = getFiles(folderPath);
        Menu.println("Доступные сохранения:");
        displayArrays(getFilesNames(folderPath));

        int selected = InputHandler.getIntInput();
        return saveFiles[selected - 1];
    }

    protected static String getSaveFileName(String filePath) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy (HH-mm)");
        String formattedDateTime = now.format(formatter);

        return filePath + formattedDateTime + ".ser";
    }

    protected static String getSaveFileName(String filePath, String keys) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy (HH-mm)");
        String formattedDateTime = now.format(formatter);

        return filePath + formattedDateTime + " " + keys + ".ser";
    }
}
