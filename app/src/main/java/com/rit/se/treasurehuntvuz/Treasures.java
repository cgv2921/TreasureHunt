package com.rit.se.treasurehuntvuz;

import java.util.ArrayList;
import java.util.List;

public class Treasures {

    private List<TreasurePoint> treasurePointList;
    private int numCollected;
    private int numTotal;
    private boolean resume;

    public Treasures(){
        newList();
    }

    public void newList() {
        treasurePointList = new ArrayList<>();
        numCollected = 0;
        numTotal = 0;
        resume = false;
    }

    public List<TreasurePoint> getList(){
        if(treasurePointList == null){
            newList();
        }
        return treasurePointList;
    }

    public int getNumCollected() { return this.numCollected; }
    public void setNumCollected(int numCollected) { this.numCollected = numCollected; }
    public void incrementNamCollected() {
        this.setNumCollected(this.getNumCollected()+1);
    }

    public int getNumTotal() { return this.numTotal; }
    private void setNumTotal(int numTotal) { this.numTotal = numTotal; }

    public boolean getResume() { return this.resume; }
    public void setResume(boolean resume) { this.resume = resume; }
}
