package com.rit.se.treasurehuntvuz;

import java.util.*;
import static java.lang.Math.*;
import android.location.Location;

public class TreasureGenerator{
    static final double RADIUS = 6378100; // radius of earth in meters


    // takes int dist as distance in meters from original location
    // takes bearing in degrees clockwise from north
    //      0 is north, 90 is east, 180 is south, 270 is west
    private static Location getTreasureLocation(Location initLocation, double dist, double bear){

        double lat1 = toRadians(initLocation.getLatitude());
        double lon1 = toRadians(initLocation.getLongitude());

        double bearing = toRadians(bear);

        double lat2 = asin(sin(lat1)*cos(dist/RADIUS)+
            cos(lat1)*sin(dist/RADIUS)*cos(bearing));

        double lon2 = lon1+atan2(sin(bearing)*sin(dist/RADIUS)*cos(lat1),
            cos(dist/RADIUS)-sin(lat1)*sin(lat2));

        lat2 = toDegrees(lat2);
        lon2 = toDegrees(lon2);

        Location termnlLocation = new Location("");
        termnlLocation.setLatitude(lat2);
        termnlLocation.setLongitude(lon2);

        // in meters
        double actualDist = initLocation.distanceTo(termnlLocation);
        if( actualDist == 0){
            return termnlLocation;
        } else {
            throw new Error("Math on generating treasure location is faulty.");
        }
    }

    // initLocation to be the user's location
    // maxDist and minDist define the distance range for treasre to appear in
    // amount to be how many treasure locations should be generated
    public static List<Location> generateTreasureLocations(Location initLocation, double minDist, double maxDist, int amount){

        if(minDist < 0){
            throw new Error("minDist cannot be less than zero");
        }

        if(maxDist <= 0){
            throw new Error("maxDist must be greater than zero");
        }

        if(minDist > maxDist){
            throw new Error("minDist cannot be greater than maxDist");
        }

        if(amount <= 0){
            throw new Error("amount must be greater than zero");
        }

        Random rng = new Random();
        List<Location> treasureLocations = new ArrayList<Location>();
        double distance;
        double bearing;

        for(int i = 0; i < amount; i++){
            distance = (rng.nextInt()*(maxDist-minDist))+minDist;
            bearing = rng.nextInt()*360;
            treasureLocations.add(getTreasureLocation(initLocation, distance, bearing));
        }

        return treasureLocations;
    }

    public static List<Location> generateTreasureLocations(Location initLocation, double maxDist, int amount){
        return generateTreasureLocations(initLocation, (double)0, maxDist, amount);
    }

    public static List<Location> generateTreasureLocations(Location initLocation, int amount){
        return generateTreasureLocations(initLocation, (double)1000, amount);
    }

    //public static void main(String [] args)
    //{

      //  TreasureGenerator l = new TreasureGenerator();

        //Location t = new Location(android.location);



    //}
    
}



