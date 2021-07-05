package de.sth.minesweeper.stats;

import org.json.JSONArray;
import org.json.JSONObject;

public class GameStatistic {
    private boolean won;
    private long moves;
    private long revealedFields;
    private boolean revealFirstSelected;
    private int points;
    private long beginningMillis;
    private long endingMillis;

    public GameStatistic(boolean revealFirstSelected, long beginningMillis) {
        this.won = false;
        this.moves = 0L;
        this.revealedFields = 0L;
        this.revealFirstSelected = revealFirstSelected;
        this.points = -1;
        this.beginningMillis = beginningMillis;
        this.endingMillis = -1L;
    }

    public GameStatistic(boolean won, boolean revealFirstSelected, long revealedFields, long moves, int points, long beginningMillis, long endingMillis) {
        this.won = won;
        this.moves = moves;
        this.revealFirstSelected = revealFirstSelected;
        this.revealedFields = revealedFields;
        this.points = points;
        this.beginningMillis = beginningMillis;
        this.endingMillis = endingMillis;
    }

    public static GameStatistic of(JSONObject o) {
        return new GameStatistic(o.getBoolean("won"), o.getBoolean("revealFirstSelected"), o.getLong("revealedFields"), o.getLong("moves"), o.getInt("points"), o.getLong("beginningMillis"), o.getLong("endingMillis"));
    }

    public static GameStatistic[] ofArray(JSONArray a) {
        GameStatistic[] returnValue = new GameStatistic[a.length()];
        for (int i = 0; i < a.length(); i++) {
            returnValue[i] = GameStatistic.of((JSONObject) a.get(i));
        }
        return returnValue;
    }

    public void save() {
        Statistics.writeToFile(this);
    }

    public JSONObject toJSONObject() {
        JSONObject o = new JSONObject();
        o.put("won", this.won);
        o.put("moves", this.moves);
        o.put("revealFirstSelected", this.revealFirstSelected);
        o.put("revealedFields", this.revealedFields);
        o.put("points", this.points);
        o.put("beginningMillis", this.beginningMillis);
        o.put("endingMillis", this.endingMillis);
        return o;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public void incrementMoves() {
        this.moves += 1;
    }

    public long getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public long getRevealedFields() {
        return revealedFields;
    }

    public void setRevealedFields(int revealedFields) {
        this.revealedFields = revealedFields;
    }

    public void incrementRevealedFields() {
        this.revealedFields++;
    }

    public boolean isRevealFirstSelected() {
        return revealFirstSelected;
    }

    public void setRevealFirstSelected(boolean revealFirstSelected) {
        this.revealFirstSelected = revealFirstSelected;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public long getBeginningMillis() {
        return beginningMillis;
    }

    public void setBeginningMillis(long beginningMillis) {
        this.beginningMillis = beginningMillis;
    }

    public long getEndingMillis() {
        return endingMillis;
    }

    public void setEndingMillis(long endingMillis) {
        this.endingMillis = endingMillis;
    }
}
