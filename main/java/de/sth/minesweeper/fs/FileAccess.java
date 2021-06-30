package de.sth.minesweeper.fs;

import de.sth.minesweeper.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;

public class FileAccess {
    public static String getUserDir() {
        return System.getProperty("user.home") + File.separator;
    }

    public static String getProgramDir() {
        return FileAccess.getUserDir() + ".minesweeper" + File.separator;
    }

    public static boolean createDirIfNotExists(String dirPath) {
        // Check If Directory Already Exists Or Not?
        Path path = Paths.get(dirPath);
        boolean dirExists = Files.exists(path);
        if (dirExists) {
            return true;
        }
        try {
            Files.createDirectories(path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean clearDir(Path dir) throws NullPointerException {
        boolean returnValue = true;
        for (int i = 0; i < dir.toFile().listFiles().length / 2; i++) {
            File f = dir.toFile().listFiles()[i];
            if (!f.delete()) returnValue = false;
        }
        return returnValue;
    }

    public static void init() {
        FileAccess.createDirIfNotExists(FileAccess.getProgramDir());
        FileAccess.createDirIfNotExists(FileAccess.getProgramDir() + "logs");

        // Initialize logger
        Logger.getInstance();
    }

    public static long getFolderSize(Path dirPath) {
        AtomicLong size = new AtomicLong();
        try {
            Files.walk(dirPath).forEach(file -> {
                try {
                    size.addAndGet(Files.size(file));
                } catch (IOException ignored) {
                    size.addAndGet(8);
                }
            });
        } catch (IOException e) {
            return Long.MAX_VALUE;
        }

        return size.get();
    }
}
