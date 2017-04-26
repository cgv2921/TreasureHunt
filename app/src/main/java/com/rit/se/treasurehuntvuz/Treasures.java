package com.rit.se.treasurehuntvuz;

import android.location.*;
import android.util.Log;

import java.io.*;
import java.util.*;

class Treasures implements Serializable {

    class Treasure implements Serializable {
        private final transient Location location;
        private final double longitude;
        private final double latitude;
        private boolean found = false;
        private Calendar foundTime = null;

        Treasure(Location location) {
            this.location = new Location(location);
            this.longitude = this.location.getLongitude();
            this.latitude = this.location.getLatitude();
        }

        // makes a copy of passed treasure
        Treasure(Treasure treasure) {
            this.longitude = treasure.getLongitude();
            this.latitude = treasure.getLatitude();
            this.location = new Location(LocationManager.GPS_PROVIDER);
            this.location.setLongitude(longitude);
            this.location.setLatitude(latitude);
            this.found = treasure.getFound();
            if(treasure.getFoundTime() != null) {
                this.foundTime = (Calendar) treasure.getFoundTime().clone();
            }
        }

        double getLongitude() {
            return longitude;
        }

        double getLatitude() {
            return latitude;
        }

        Location getLocation() {
            return location;
        }

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

    private Coins playersCoins;
    private List<Treasure> treasures;
    private int numCollected;
    private int numTotal;
    private boolean resume;
    private transient boolean isSynced;

    Treasures() {
        newTreasureList();
    }

    short getNumCoins() {
        return this.playersCoins.getNumCoins();
    }

    boolean addCoins(short numCoins) {
        try {
            this.playersCoins.addCoins(numCoins);
            this.isSynced = false;
            save();
            return true;
        } catch (Exception exception) {
            if(exception.getMessage() != null) {
                Log.d("TreasuresAddCoins", exception.getMessage());
            } else {
                Log.e("TreasuresAddCoins", "Exception without a message.");
            }
            return false;
        }
    }

    List<Treasure> getTreasureList() {
        return Collections.unmodifiableList(this.treasures);
    }

    int getNumCollected() {
        return this.numCollected;
    }

    int getNumTotal() {
        return this.numTotal;
    }

    boolean getResume() {
        return this.resume;
    }

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
            this.addCoins((short)100);
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

        // consider a higher score for saving coins
        tmpPlayerScore += 0.13 * this.getNumCoins();

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
            this.newTreasureList();
        }
    }

    void loadTreasureHuntGame() {
        newTreasureList();
        this.isSynced = false;
        this.load();
    }

    private void newTreasureList() {
        playersCoins = new Coins();
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
                Log.d("TreasuresSave", exception.getMessage(), exception);
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
                Treasures serializedTreasures = (Treasures) input.readObject();

                this.playersCoins = serializedTreasures.playersCoins;

                this.treasures.clear();
                for(Treasure treasure : serializedTreasures.treasures) {
                    this.treasures.add(new Treasure(treasure));
                }

                this.numCollected = serializedTreasures.numCollected;
                this.numTotal = serializedTreasures.numTotal;
                this.resume = serializedTreasures.resume;

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
