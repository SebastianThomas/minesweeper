package de.sth.minesweeper.settings;

import de.sth.minesweeper.difficulties.Difficulties;
import de.sth.minesweeper.fs.FileAccess;
import de.sth.minesweeper.fs.JSONIOException;
import de.sth.minesweeper.fs.JSONWriter;
import de.sth.minesweeper.logging.Logger;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Path;

public class SettingsIO {
    public static Path settingsPath = Path.of(FileAccess.getProgramDir() + File.separator + "settings" + File.separator + "settings.json");

    public static void writeSettings(boolean revealFirstSelected, Difficulties selectedDifficulty) {
        JSONObject o = new JSONObject();
        o.put("revealFirstSelected", revealFirstSelected);
        o.put("selectedDifficulty", selectedDifficulty);
        JSONWriter.writeToFile(settingsPath, o);
    }

    public static Settings readSettings() {
        try {
            return new Settings(JSONWriter.readObjectFromFile(settingsPath));
        } catch (JSONIOException e) {
            Logger.getInstance().log("COULD NOT READ SETTINGS FROM FILE!");
            System.out.println("Could not read settings from file");
            return new Settings();
        }
    }
}
