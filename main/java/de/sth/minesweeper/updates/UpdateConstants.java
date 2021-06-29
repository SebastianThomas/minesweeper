package de.sth.minesweeper.updates;

public abstract class UpdateConstants {
    public static final String currentVersion = "v4.2-ea";

    enum UpdateOptions {
        /**
         * Current version is the newest (including EA)
         */
        UP_TO_DATE,
        /**
         * Current version is the newest (NOT including EA)
         */
        UP_TO_DATE_WITHOUT_EA,

        /**
         * Current version is an EA, the full version to this EA is available.
         */
        UPDATE_FROM_EA_TO_FULL,

        /**
         * Current version is the newest full release; there is a new EA-release available.
         */
        UPDATE_TO_NEW_EA,

        /**
         * A new Major update is available
         */
        UPDATE_TO_MAJOR,
        /**
         * A new Minor update is available
         */
        UPDATE_TO_MINOR,

        /**
         * Either something went wrong or you are in development; so you have a newer version than published.
         */
        HAVE_NEWER_VERSION;

        public static String getTextFor(UpdateOptions option) {
            return switch (option) {
                case UP_TO_DATE -> "You have latest release (might be an Early Access version)";
                case UPDATE_FROM_EA_TO_FULL -> "The full update for your EA-preview is available!";
                case UPDATE_TO_NEW_EA -> "THERE IS ANOTHER Early Access RELEASE";
                case UP_TO_DATE_WITHOUT_EA -> "You have the newest full release";
                case UPDATE_TO_MAJOR -> "There is a new Major patch available";
                case UPDATE_TO_MINOR -> "There is a new Minor patch available";
                default -> "An error occurred";
            };
        }
    }
}
