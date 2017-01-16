package com.example.galtzemach.minesweeper.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.example.galtzemach.minesweeper.logic.Record;
import com.example.galtzemach.minesweeper.logic.RecordController;

/**
 * Created by Guy on 08/01/2017.
 */

public class MapDialogFragment extends DialogFragment {
    private Record gameRecord;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(gameRecord.getName());

        TextView textView = new TextView(getActivity());
        textView.setText("Level: "+ RecordController.LEVEL_LABELS[gameRecord.getLevel()]+ "\nTime: "+gameRecord.getRecordTime());
        textView.setPadding(60,0,0,0);

        builder.setView(textView);

        return builder.create();
    }

    public static MapDialogFragment newInstance() {
        Bundle args = new Bundle();
        MapDialogFragment fragment = new MapDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public void setGameRecord(Record gameRecord){
        this.gameRecord = gameRecord;
    }
}
