package com.rit.se.treasurehuntvuz;

// Jeffrey Haines 3/1/17
//    Singleton treasure point list
class TreasuresSingleton {

    private static Treasures treasures;

    static Treasures getTreasures(){
        if(treasures == null){
            treasures = new Treasures();
        }
        return treasures;
    }
}
