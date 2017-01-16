package com.example.galtzemach.minesweeper.logic;

/**
 * Created by Gal Tzemach on 29/11/2016.
 */

public class Record {

    private int recordId;
    private int recordTime;
    private int level;
    private String name;
    private double longitude;
    private double latitude;

    public Record(int recordId, int time, int level, String name, double longitude, double latitude) {
        this.recordId	= recordId;
        this.recordTime = time;
        this.level 		= level;
        this.name 		= name;
        this.longitude  = longitude;
        this.latitude   = latitude;

    }

    public int getRecordTime() {
        return recordTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String toString(){
        return "GameRecord: user:"+this.getName()+" Level:"+this.getLevel()+" time:"+ this.getRecordTime();
    }
}
