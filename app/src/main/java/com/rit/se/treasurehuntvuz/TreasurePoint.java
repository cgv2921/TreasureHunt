package com.rit.se.treasurehuntvuz;

import java.util.Calendar;

public class TreasurePoint {

    // Proximity is based on a percentage from the furthest_distance
    private static double furthestDistance;

    public enum Proximity {
        VERY_FAR(20, R.drawable.treasure_veryfar),
        FAR(40, R.drawable.treasure_veryfar),
        NEAR(60, R.drawable.treasure_veryfar),
        CLOSE(80, R.drawable.treasure_veryfar),
        HOT(100, R.drawable.treasure_veryfar);

        private final int thresPercent;
        private final int drawableRes;
        Proximity(int thresPercent, int drawableRes) {
            this.thresPercent = thresPercent;
            this.drawableRes = drawableRes;
        }

        public static Proximity getProximity(int thresPercent) {
            return  thresPercent < 20 ? HOT :
                    thresPercent < 40 ? CLOSE :
                    thresPercent < 60 ? NEAR :
                    thresPercent < 80 ? FAR : VERY_FAR;
        }

        public static int getDrawableRes(Proximity proximity) {
            return proximity.drawableRes;
        }
    }

    private Proximity proximity;
    private final double longitude, latitude;
    private boolean found;
    private Calendar foundTime;

    public TreasurePoint(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.found = false;
        this.foundTime = null;
    }

    public Proximity getProximity() { return this.proximity; }
    public void setProximity(Proximity proximity) { this.proximity = proximity; }

    public double getLat() { return this.latitude; }
    public double getLon() { return this.longitude; }

    public boolean getFound() { return this.found; }
    public void setFound(boolean found) { this.found = found; }

    public double getFurthestDistance() { return furthestDistance; }
    public void setFurthestDistance(double distance) { furthestDistance = distance; }

    public Calendar getFoundtime() {
        if(this.foundTime == null) {
            this.foundTime = Calendar.getInstance();
        }
        return this.foundTime;
    }
    public void setFoundTime() { this.foundTime = Calendar.getInstance(); }
}
