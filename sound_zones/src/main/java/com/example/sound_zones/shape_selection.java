package com.example.sound_zones;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class shape_selection extends Fragment {
    private static final String TAG = "MyActivity";
    private Button egg;
    private Button dome;
    private Button entity;
    private Button orb;
    private MainActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_shape_selection, container, false);
        egg = (Button)view.findViewById(R.id.EggShapeButton);
        dome = (Button)view.findViewById(R.id.DomeShapeButton);
        entity = (Button)view.findViewById(R.id.EntityShapeButton);
        orb = (Button)view.findViewById(R.id.OrbShapeButton);
        activity = (MainActivity) getActivity();

        egg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.zoneShape = SoundZoneShape.EGG;
                activity.NavigatePlaceZone();
            }
        });

        dome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.zoneShape = SoundZoneShape.DOME;
                Log.i(TAG, "Shape in activity  is: " + activity.zoneShape);
                activity.NavigatePlaceZone();
            }
        });

        entity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.zoneShape = SoundZoneShape.ENTITY;
                activity.NavigatePlaceZone();
            }
        });

        orb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.zoneShape = SoundZoneShape.ORB;
                activity.NavigatePlaceZone();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}