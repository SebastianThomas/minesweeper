package de.sth.minesweeper.fs;

import de.sth.minesweeper.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class JSONWriter {
    public static void writeToFile(Path file, String s) {
        try {
            createDirAndFileIfNotExists(file);
            Files.writeString(file, s, StandardOpenOption.WRITE);
        } catch (IOException e) {
            Logger.getInstance().log("COULD NOT WRITE JSON TO FILE: \n" + e);
        }
    }

    public static void writeToFile(Path file, JSONArray a) {
        writeToFile(file, a.toString(4));
    }

    public static void writeToFile(Path file, JSONObject o) {
        writeToFile(file, o.toString(4));
    }

    public static void createDirAndFileIfNotExists(Path file) throws IOException {
        if (!Files.exists(file.getParent())) Files.createDirectories(file.getParent());
        if (!Files.exists(file)) Files.createFile(file);
    }

    public static JSONObject readObjectFromFile(Path file) throws JSONIOException {
        try {
            JSONTokener k = new JSONTokener(Files.readString(file));
            return new JSONObject(k);
        } catch (IOException e) {
            Logger.getInstance().log("COULD NOT READ JSON TO FILE: \n" + e);
            throw new JSONIOException();
        }
    }

    public static JSONArray readArrayFromFile(Path file) throws JSONIOException {
        try {
            JSONTokener k = new JSONTokener(Files.readString(file));
            return new JSONArray(k);
        } catch (IOException e) {
            Logger.getInstance().log("COULD NOT READ JSON TO FILE: \n" + e);
            throw new JSONIOException();
        }
    }
}
