package com.rit.se.treasurehuntvuz;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HighScores implements Serializable {

    public class HighScore implements Serializable {
        private final String playerName;
        private final int score;

        public HighScore(String playerName, int score) {

            // TODO: Validate high score parameters

            this.playerName = playerName;
            this.score = score;
        }

        @Override
        public String toString() {
            return this.playerName + ":  " + this.score;
        }
    }

    private List<HighScore> highScores;
    private transient boolean isSynced;

    public HighScores() {
        this.highScores = new ArrayList<>();
        this.isSynced = false;
    }

    public void addHighScore(HighScore highScore) {
        if(this.isSynced) {
            this.highScores.add(highScore);
            this.isSynced = false;
        }
    }

    public List<String> getHighScoreStrings() {
        List<String> highScoreStrings = new ArrayList<>();
        for(HighScore score : this.highScores) {
            highScoreStrings.add(score.toString());
        }
        return highScoreStrings;
    }

    private void save() {

        try {

            this.isSynced = true;

        } catch (Exception exception) {
            Log.e("HighScoreSave", exception.getMessage());
            this.isSynced = false;
        }
    }

    private void load() {
        try {



            this.isSynced = true;

        } catch (Exception exception) {
            Log.e("HighScoreLoad", exception.getMessage());
            this.isSynced = false;
        }
    }
}
