package com.example.galtzemach.minesweeper.ui;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.galtzemach.minesweeper.R;
import com.example.galtzemach.minesweeper.logic.Record;
import com.example.galtzemach.minesweeper.logic.RecordController;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TableFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TableFragment extends Fragment {
    public static final String LEVEL_ARG = "level";
    private RelativeLayout mainLayout = null;
    private RecordController recordController;
    private TableLayout table;
    private Activity parent;
    private int currentLevel;


    private OnFragmentInteractionListener mListener;

    public TableFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TableFragment newInstance(int level) {
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        args.putInt(LEVEL_ARG, level);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            this.currentLevel = getArguments().getInt(LEVEL_ARG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parent = getActivity();
        if (mainLayout == null){
            mainLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_table, container, false);
            mainLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            mainLayout.setGravity(Gravity.CENTER);
            this.table = new TableLayout(parent);
            table.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            table.setPadding(50,0,50,0);
            printTable(table , this.currentLevel);
            mainLayout.addView(table);

        }
        return mainLayout;
    }

    private void printTable(TableLayout table, int level) {
        ArrayList<Record> recordsArray = recordController.getRecordsArray(level);

        TableRow tableRow = new TableRow(this.parent);

        TextView numTV = new TextView(this.parent);
        SpannableString num = new SpannableString("#N");
        num.setSpan(new UnderlineSpan(), 0, num.length(), 0);
        numTV.setTextColor(Color.parseColor("#000000"));
        numTV.setText(num);
        numTV.setTextSize(28);
        numTV.setWidth(200);

        TextView nameTV = new TextView(this.parent);
        SpannableString name = new SpannableString("Name");
        name.setSpan(new UnderlineSpan(), 0, name.length(), 0);
        nameTV.setTextColor(Color.parseColor("#000000"));
        nameTV.setText(name);
        nameTV.setTextSize(28);
        nameTV.setWidth(800);

        TextView timeTV = new TextView(this.parent);
        SpannableString time = new SpannableString("Time");
        time.setSpan(new UnderlineSpan(), 0, time.length(), 0);
        timeTV.setTextColor(Color.parseColor("#000000"));
        timeTV.setText(time);
        timeTV.setTextSize(28);

        tableRow.addView(numTV);
        tableRow.addView(nameTV);
        tableRow.addView(timeTV);
        table.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        for (int i=1 ; i <= recordsArray.size() ; i++)
            printRow(table , i , recordsArray.get(i-1));
    }

    private void printRow(TableLayout table, int rank, Record record) {

        TableRow tableRow = new TableRow(this.parent);

        TextView rankIndex = new TextView(this.parent);
        rankIndex.setTextColor(Color.parseColor("#000000"));
        rankIndex.setText(((Integer)rank).toString()+"");
        rankIndex.setTextSize(28);
        rankIndex.setWidth(150);

        TextView nameField = new TextView(this.parent);
        nameField.setTextColor(Color.parseColor("#000000"));
        nameField.setText(record.getName());
        nameField.setTextSize(28);
        nameField.setWidth(500);

        TextView timeField = new TextView(this.parent);
        timeField.setTextColor(Color.parseColor("#000000"));
        timeField.setText( ((Integer)(record.getRecordTime())).toString() );
        timeField.setTextSize(28);

        tableRow.addView(rankIndex);
        tableRow.addView(nameField);
        tableRow.addView(timeField);

        table.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateTable(int level) {
        this.currentLevel = level;
        table.removeAllViews();
        printTable(table , this.currentLevel);
    }

    public void setRecordController(RecordController recordController){
        this.recordController = recordController;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
