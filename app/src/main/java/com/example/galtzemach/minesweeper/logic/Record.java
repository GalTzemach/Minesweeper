package com.example.galtzemach.minesweeper.logic;

import java.sql.Time;

/**
 * Created by Gal Tzemach on 29/11/2016.
 */

public class Record {

    private Time time;
    private String name;

    public Record(Time time, String name) {
        this.time = time;
        this.name = name;
    }
}
