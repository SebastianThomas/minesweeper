package de.sth.minesweeper.stats;

import de.sth.minesweeper.fs.FileAccess;
import de.sth.minesweeper.fs.JSONIOException;
import de.sth.minesweeper.fs.JSONWriter;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Statistics {
    public static Path statsPath = Paths.get(FileAccess.getProgramDir() + "stats" + File.separator + "statistics.json");

    public static GameStatistic[] getStats() throws JSONIOException, JSONException {
        JSONArray a = JSONWriter.readArrayFromFile(statsPath);
        return GameStatistic.ofArray(a);
    }

    public static GameStatistic[] writeToFile(GameStatistic stats) {
        JSONArray currentStats;
        try {
            currentStats = JSONWriter.readArrayFromFile(statsPath);
        } catch (JSONIOException e) {
            currentStats = new JSONArray();
        }
        currentStats.put(stats.toJSONObject());

        JSONWriter.writeToFile(statsPath, currentStats);
        System.out.println(statsPath);
        System.out.println("Written, return");

        return GameStatistic.ofArray(currentStats);
    }
}
