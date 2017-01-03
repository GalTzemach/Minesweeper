package com.example.galtzemach.minesweeper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.galtzemach.minesweeper.R;

// public class SettingActivity extends AppCompatActivity implements OnMapReadyCallback
public class SettingActivity extends AppCompatActivity{

    //private boolean isSounde;
    //private boolean isVebration;
    //private boolean isFullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_setting);
        relativeLayout.setGravity(Gravity.CENTER);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView txtOptional = new TextView(this);
        txtOptional.setText("Optional (not work)");
        txtOptional.setTextSize(30);

        CheckBox soundCheckBox = new CheckBox(this);
        soundCheckBox.setText("Sound");

        CheckBox vibrationCheckBox = new CheckBox(this);
        vibrationCheckBox.setText("Vibration");

        CheckBox fullScreenCheckBox = new CheckBox(this);
        fullScreenCheckBox.setText("Full Screen");

//        MapFragment m1 = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
//        m1.getMapAsync(this);

        linearLayout.addView(txtOptional);
        linearLayout.addView(soundCheckBox);
        linearLayout.addView(vibrationCheckBox);
        linearLayout.addView(fullScreenCheckBox);
        relativeLayout.addView(linearLayout);
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            Log.d(TAG, "onMapReady: ");
//            return;
//        }
//        googleMap.setMyLocationEnabled(true);
//
//
//    }

}
