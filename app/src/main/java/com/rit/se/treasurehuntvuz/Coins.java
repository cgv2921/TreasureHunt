package com.rit.se.treasurehuntvuz;

import android.util.Log;

import java.io.*;

class Coins implements Serializable {
    private short playersCoins;

    Coins() {
        this.playersCoins = 0;
    }

    short getNumCoins() {
        return playersCoins;
    }

    void addCoins(short numCoins) {
        if(numCoins < 1) {
            throw new IllegalArgumentException("Can not add zero or negative coins.");
        }
        // keeping maximum number of coins constrained to 16 bits
        if(addWillOverFlow(playersCoins, numCoins)) {
            playersCoins = Short.MAX_VALUE;
            Log.d("CoinsAdd", "Max coins earned!");
        }
        else {
            playersCoins += numCoins;
            Log.d("CoinsAdd", String.format("%d coins earned!", numCoins));
        }
    }

    private static boolean addWillOverFlow(short a, short b) {
        return (Math.signum(a) == Math.signum(b)) && (Math.signum(a) != Math.signum(a+b));
    }
}
