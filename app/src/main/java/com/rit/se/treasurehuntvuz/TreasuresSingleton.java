package com.rit.se.treasurehuntvuz;

// Jeffrey Haines 3/1/17
//    Singleton treasure point list
public class TreasuresSingleton {

    private static Treasures treasures;

    public static Treasures getTreasures(){
        if(treasures == null){
            treasures = new Treasures();
            treasures.newList();
        }
        return treasures;
    }
}
