package DB.Saves;

import game.Utils.InputHandler;
import game.Utils.Menu.Menu;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static game.Utils.Menu.Menu.displayArrays;

public class Save {
    private static File[] getSaveFiles(String folderPath) {
        return new File(folderPath).listFiles();
    }

    private static List<String> getSaveFilesNames(String folderPath) {
        return Arrays.stream(getSaveFiles(folderPath))
                .filter(File::isFile)
                .map(File::getName)
                .collect(Collectors.toList());
    }

    protected static File getSaveFile(String folderPath) {
        File[] saveFiles = getSaveFiles(folderPath);
        Menu.println("Доступные сохранения:");
        displayArrays(getSaveFilesNames(folderPath));

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
