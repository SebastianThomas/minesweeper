package de.sth.minesweeper.settings;

import de.sth.minesweeper.difficulties.Difficulties;
import org.json.JSONException;
import org.json.JSONObject;

public class Settings {
    private boolean revealFirstSelected;
    private Difficulties selectedDifficulty;

    public Settings() {
        this.revealFirstSelected = true;
        this.selectedDifficulty = Difficulties.MEDIUM;
    }

    public Settings(boolean revealFirstSelected, Difficulties selectedDifficulty) {
        this.revealFirstSelected = revealFirstSelected;

        this.selectedDifficulty = selectedDifficulty;
    }

    public Settings(JSONObject o) {
        boolean through = false;
        try {
            this.revealFirstSelected = o.getBoolean("revealFirstSelected");
            through = true;
            this.selectedDifficulty = o.getEnum(Difficulties.class, "selectedDifficulty");
        } catch (JSONException e) {
            if (!through) this.revealFirstSelected = true;
            this.selectedDifficulty = Difficulties.MEDIUM;
        }
    }

    public boolean isRevealFirstSelected() {
        return revealFirstSelected;
    }

    public void setRevealFirstSelected(boolean revealFirstSelected) {
        this.revealFirstSelected = revealFirstSelected;
    }

    public Difficulties getSelectedDifficulty() {
        return selectedDifficulty;
    }

    public void setSelectedDifficulty(Difficulties selectedDifficulty) {
        this.selectedDifficulty = selectedDifficulty;
    }
}
