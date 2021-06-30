package de.sth.minesweeper.logging;

import de.sth.minesweeper.fs.FileAccess;
import de.sth.minesweeper.fs.PotentialCleanThread;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Logger {
    public static Logger logger;
    public final PotentialCleanThread cleanThread;
    protected Path loggerPath;

    protected Logger() throws IOException {
        this.loggerPath = Paths.get(FileAccess.getProgramDir() + "logs" + File.separator + System.currentTimeMillis() + "-minesweeper.log");
        Files.createFile(loggerPath);
        this.log("Begin logging");

        // 1MB
        this.cleanThread = new PotentialCleanThread(1000 * 1000, Paths.get(FileAccess.getProgramDir() + "logs" + File.separator));
        Runtime.getRuntime().addShutdownHook(this.cleanThread);
    }

    public static Logger getInstance() {
        try {
            if (logger == null) logger = new Logger();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                logger = new IOExceptionLogger();
            } catch (IOException ignored) {
                // Exception cannot happen
            }
        }
        return logger;
    }

    public void log(String additionalContent) {
        try {
            // Appending The New Data To The Existing File
            Files.write(this.loggerPath, (System.currentTimeMillis() + " --> " + additionalContent + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void separatorLine(String separator) {
        try {
            // Appending The New Data To The Existing File
            Files.write(this.loggerPath, ("\n----------------------------------------" + separator + "----------------------------------------\n\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogs() {
        try {
            return new String(Files.readAllBytes(this.loggerPath));
        } catch (IOException e) {
            return "IOException!!!";
        }
    }
}

class IOExceptionLogger extends Logger {
    protected IOExceptionLogger() throws IOException {
        this.loggerPath = Paths.get(FileAccess.getProgramDir());
    }

    @Override
    public void log(String ignored) {
        // Do nothing
        // throw new UnsupportedOperationException();
    }

    @Override
    public String getLogs() {
        // Do nothing
        // throw new UnsupportedOperationException();
        return "";
    }
}
