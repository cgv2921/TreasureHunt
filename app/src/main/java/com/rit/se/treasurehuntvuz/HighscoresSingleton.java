package com.rit.se.treasurehuntvuz;

import android.util.Log;

public class HighscoresSingleton {

    private static Highscores highscores;

    public static Highscores getHighscores() {
        if(highscores == null) {
            highscores = new Highscores();
        }
        return highscores;
    }


}
