package com.example.galtzemach.minesweeper.logic;

import android.content.Context;

import com.example.galtzemach.minesweeper.DB.DbController;

import java.util.ArrayList;

/**
 * Created by Gal Tzemach on 14/01/2017.
 */

public class RecordController {
    public static final int BEGINNER_LEVEL 		= 0;
    public static final int ADVANCED_LEVEL 		= 1;
    public static final int EXPERT_LEVEL 		= 2;
    public static final String[] LEVEL_LABELS   = {"Easy","Medium","Hard"};

    private DbController dbController;
    private ArrayList<Record> beginnerRecordArray;
    private ArrayList<Record> advencedRecordArray;
    private ArrayList<Record> expertRecordArray;


    public RecordController(Context context){
        dbController = new DbController(context);
        beginnerRecordArray = new ArrayList<>();
        advencedRecordArray = new ArrayList<>();
        expertRecordArray   = new ArrayList<>();
    }

    public void addRecord(Record newRecord){

        dbController.addNewRecord(newRecord.getName(),
                newRecord.getRecordTime(),
                newRecord.getLevel(),
                newRecord.getLongitude(),
                newRecord.getLatitude());

        switch (newRecord.getLevel()){
            case BEGINNER_LEVEL:
                beginnerRecordArray.add(newRecord);
                break;
            case ADVANCED_LEVEL:
                advencedRecordArray.add(newRecord);
                break;
            case EXPERT_LEVEL:
                expertRecordArray.add(newRecord);
                break;
        }
    }


    public ArrayList<Record> getRecordsArray(int level) {
        switch (level) {
            case BEGINNER_LEVEL:
                if(beginnerRecordArray.size() == 0)
                    this.beginnerRecordArray = dbController.getRecords(BEGINNER_LEVEL);
                return this.beginnerRecordArray;
            case ADVANCED_LEVEL:
                if(advencedRecordArray.size() == 0)
                    this.advencedRecordArray = dbController.getRecords(ADVANCED_LEVEL);
                return this.advencedRecordArray;
            case EXPERT_LEVEL:
                if(expertRecordArray.size() == 0)
                    this.expertRecordArray = dbController.getRecords(EXPERT_LEVEL);
                return this.expertRecordArray;
            default:
                return null;

        }
    }
}
