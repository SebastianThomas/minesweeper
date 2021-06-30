package de.sth.minesweeper.fs;

import de.sth.minesweeper.logging.Logger;

import java.nio.file.Path;

public class PotentialCleanThread extends Thread {
    public PotentialCleanThread(long maxSize, Path directory) {
        super(() -> {
            Logger.getInstance().log("Delete files?: " + FileAccess.getFolderSize(directory) + ">" + maxSize);

            if (FileAccess.getFolderSize(directory) > maxSize) {
                boolean worked = FileAccess.clearDir(directory);
                if (!worked) Logger.getInstance().log("DELETION OF FILES OÌD NOT WORK");
            }
        });
    }
}
