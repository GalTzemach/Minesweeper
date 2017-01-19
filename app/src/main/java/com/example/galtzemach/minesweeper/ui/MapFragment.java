package com.example.galtzemach.minesweeper.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.galtzemach.minesweeper.R;
import com.example.galtzemach.minesweeper.logic.Record;
import com.example.galtzemach.minesweeper.logic.RecordController;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    public static final String LEVEL_ARG = "level";
    private RecordController recordController;
    private GoogleMap googleMap;
    private MapView mMapView;
    private View mapView;
    private HashMap<Marker, Record> markerRecordHashMap;
    private MapDialogFragment recordDialog;
    private int level;


    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(int level) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putInt(LEVEL_ARG, level);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        level = getArguments().getInt(LEVEL_ARG);
        markerRecordHashMap = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mapView = inflater.inflate(R.layout.fragment_map, container, false);
        return mapView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mapView.findViewById(R.id.mapView);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public void setRecordController(RecordController recordController) {
        this.recordController = recordController;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap.clear();

        this.googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Record record = markerRecordHashMap.get(marker);
                Log.v("-----gal",record.toString());
                recordDialog.setGameRecord(record);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.add(recordDialog,"guy");
                ft.commit();
                return false;
            }
        });

        this.recordDialog = MapDialogFragment.newInstance();

        ArrayList<Record> recordArrayList = recordController.getRecordsArray(this.level);

        //CameraPosition cameraPosition = null;

        if(recordArrayList != null && recordArrayList.size() > 0) {
            for (Record record : recordArrayList) {
                double longitude = record.getLongitude();
                double latitude = record.getLatitude();
                LatLng position = new LatLng(latitude, longitude);
                //cameraPosition = CameraPosition.builder().target(position).zoom(15).build();
                Marker marker = this.googleMap.addMarker(new MarkerOptions().position(position).title(record.getName()));
                markerRecordHashMap.put(marker, record);
            }

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markerRecordHashMap.keySet()) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 500);

            if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                this.googleMap.setMyLocationEnabled(true);
            } else {
                // Show rationale and request permission.
            }

            this.googleMap.moveCamera(cameraUpdate);
        }
    }

    public void updateMap(int level) {
        this.setLevel(level);
        this.onMapReady(this.googleMap);
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

    public void setLevel(int level) {
        this.level = level;
    }

}
