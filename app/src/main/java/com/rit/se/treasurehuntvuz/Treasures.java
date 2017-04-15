package com.rit.se.treasurehuntvuz;

import android.location.Location;
import android.util.Log;

import java.io.*;
import java.util.*;

class Treasures implements Serializable {

    class Treasure implements Serializable {
        private final Location location;
        private boolean found;
        private Calendar foundTime;

        Treasure(Location location) {
            this.location = location;
            this.found = false;
            this.foundTime = null;
        }

        Location getLocation() { return location; }

        boolean getFound() { return found; }
        private void setFound() { this.found = true; }

        private Calendar getFoundTime() {
            if(this.foundTime == null) {
                this.foundTime = Calendar.getInstance();
            }
            return this.foundTime;
        }
        private void setFoundTime() { this.foundTime = Calendar.getInstance(); }
    }

    private List<Treasure> treasures;
    private int numCollected;
    private int numTotal;
    private boolean resume;
    private transient boolean isSynced;

    Treasures() {
        newTreasureList();
    }

    List<Treasure> getTreasureList() {
        return new ArrayList<>(this.treasures);
    }

    int getNumCollected() { return this.numCollected; }
    int getNumTotal() { return this.numTotal; }
    boolean getResume() { return this.resume; }

    void addTreasure(Location location) {
        // clear the list for a new game
        if(this.resume) {
            newTreasureList();
        }
        this.treasures.add(new Treasure(location));
        this.numTotal += 1;
        this.isSynced = false;
        this.resume = false;
        this.save();
    }

    boolean foundTreasure(Location treasureLocation) {
        Treasure foundTreasure = findTreasureInList(this.treasures, treasureLocation);
        if(foundTreasure != null) {
            this.numCollected += 1;
            foundTreasure.setFoundTime();
            foundTreasure.setFound();
            return true;
        }
        return false;
    }

    int getTreasureHuntScore() {
        final int DIFFICULTY = 1212;
        int tmpPlayerScore = 0;
        Calendar previousFoundTime = null;

        // calculate the score, this is a pretty random calculation
        for(Treasure treasure : this.treasures) {
            if(treasure.getFound()) {
                tmpPlayerScore += 10 * 0.715 * DIFFICULTY;

                if(previousFoundTime != null) {
                    long timeBetween = treasure.getFoundTime().getTimeInMillis()
                                            - previousFoundTime.getTimeInMillis();
                    if(timeBetween > 0) {
                        tmpPlayerScore += 2 * 0.715 * DIFFICULTY;
                    }
                    else {
                        tmpPlayerScore += 2 * 0.342 * DIFFICULTY;
                    }
                }

                previousFoundTime = treasure.getFoundTime();
            }
            else {
                tmpPlayerScore -= 5 * 0.342 * DIFFICULTY;
            }
        }

        // return a positive number between 0 and 9999
        return Integer.parseInt(Integer.toString(Math.abs(tmpPlayerScore)).substring(0, 4));
    }

    void saveTreasureHuntGame(boolean resume) {
        this.isSynced = false;
        this.resume = resume;
        if(this.resume) {
            this.save();
        } else {
            this.delete();
        }
    }

    void loadTreasureHuntGame() {
        newTreasureList();
        this.isSynced = false;
        this.load();
    }

    private void newTreasureList() {
        treasures = new ArrayList<>();
        numCollected = 0;
        numTotal = 0;
        resume = false;
    }

    private Treasure findTreasureInList(Collection<Treasure> treasures, Location searchingLocation) {
        for(Treasure treasure : treasures) {
            if(treasure.getLocation().equals(searchingLocation)) {
                return treasure;
            }
        }
        return null;
    }

    private void save() {
        try {
            if(!this.isSynced) {
                // NOTE: Try w/ resources requires android 19
                File appDir = TreasureHuntVuzApp.getApplicationFilesDir();
                File treasuresFile = new File(appDir + "treasures.ser");
                OutputStream file = new FileOutputStream(treasuresFile);
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);

                // write this to file
                output.writeObject(this);

                output.close();
                buffer.close();
                file.close();

                Log.d("TreasuresSave", "Treasures saved!");
                this.isSynced = true;
            }
        } catch (Exception exception) {
            if(exception.getMessage() != null) {
                Log.d("TreasuresSave", exception.getMessage());
            } else {
                Log.e("TreasuresSave", "Exception without a message.");
            }
            this.isSynced = false;
        }
    }

    private void load() {
        try {
            if(!this.isSynced) {
                File appDir = TreasureHuntVuzApp.getApplicationFilesDir();
                File treasuresFile = new File(appDir + "treasures.ser");
                if(!treasuresFile.isFile()) {
                    Log.d("TreasuresLoad", "Treasures file not found, saving...");
                    save();
                }

                // NOTE: Try w/ resources requires android 19
                InputStream file = new FileInputStream(treasuresFile);
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);

                // load this from file
                Treasures treasures = (Treasures) input.readObject();
                // TODO; is a deep copy needed here?
                this.treasures = treasures.treasures;
                this.numCollected = treasures.numCollected;
                this.numTotal = treasures.numTotal;
                this.resume = treasures.resume;

                input.close();
                buffer.close();
                file.close();

                Log.d("TreasuresLoad", "Treasures loaded!");

                this.isSynced = true;
            }
        } catch (Exception exception) {
            if(exception.getMessage() != null) {
                Log.e("TreasuresLoad", exception.getMessage());
            } else {
                Log.e("TreasuresLoad", "Exception without a message.");
            }
            this.isSynced = false;
        }
    }

    private void delete() {
        try {
            File appDir = TreasureHuntVuzApp.getApplicationFilesDir();
            File treasuresFile = new File(appDir + "treasures.ser");
            treasuresFile.delete();
            Log.d("TreasuresLoad", "Treasures file deleted!");
            this.isSynced = false;
        } catch (Exception exception) {
            if(exception.getMessage() != null) {
                Log.e("TreasuresDelete", exception.getMessage());
            } else {
                Log.e("TreasuresDelete", "Exception without a message.");
            }
            this.isSynced = false;
        }
    }
}
