package com.rit.se.treasurehuntvuz;

import java.util.List;
import java.util.ArrayList;

// Jeffrey Haines 3/1/17
//    Singleton treasure point list
public class Treasures {
    private static List<TreasurePoint> treasurePointList;
    private static int numCollected;
    private static int numTotal;
    private static Treasures treasures;

    public static Treasures getTreasures(){
        if(treasures == null){
            treasures = new Treasures();
        }
        return treasures;
    }

    public void newList() {
        treasurePointList = new ArrayList<>();
        numCollected = 0;
        numTotal = treasurePointList.size();
    }

    public List<TreasurePoint> getList(){
        if(treasurePointList == null){
            newList();
        }
        return treasurePointList;
    }

    public int getNumCollected() { return numCollected; }
    public void setNumCollected(int numCol) { numCollected = numCol; }

    public int getNumTotal() { return numTotal; }
}
