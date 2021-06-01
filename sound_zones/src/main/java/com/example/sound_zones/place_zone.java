package com.example.sound_zones;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;


public class place_zone extends Fragment {

    private Button placeButton;
    private MainActivity activity;
    private ImageView zoneImage;
    private TextInputEditText zoneName;
    private ImageButton backButton;

    private static final String TAG = "MyActivity";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_place_zone, container, false);
        zoneImage = (ImageView) view.findViewById(R.id.ZoneTypeImage);
        zoneName = (TextInputEditText) view.findViewById(R.id.ZoneName);
        placeButton = (Button) view.findViewById(R.id.placeButton);
        activity = (MainActivity) getActivity();
        backButton = (ImageButton)view.findViewById(R.id.placeMenuBack);

        placeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.PlaceZone(zoneName.getText().toString());
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.NavigateShapeSelection();
            }
        });
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void SetImage(SoundZoneShape shape, SoundZoneType type){
        int zoneCount = activity.GetZoneCount();
        String defaultZoneName = "sound zone " + zoneCount;
        zoneName.setText(defaultZoneName);
        if(type == SoundZoneType.PRIVATE){
            if(shape == SoundZoneShape.DOME){
                zoneImage.setImageResource(R.mipmap.ic_dome_c1);
            }
            else if(shape == SoundZoneShape.EGG){
                zoneImage.setImageResource(R.mipmap.ic_egg_c1);
            }
            else if(shape == SoundZoneShape.ORB){
                zoneImage.setImageResource(R.mipmap.ic_orb_c1);
            }
            else if(shape == SoundZoneShape.ENTITY){
                zoneImage.setImageResource(R.mipmap.ic_entity_c1);
            }
        }
        else  if(type == SoundZoneType.MIXED){
            if(shape == SoundZoneShape.DOME){
                zoneImage.setImageResource(R.mipmap.ic_dome_c2);
            }
            else if(shape == SoundZoneShape.EGG){
                zoneImage.setImageResource(R.mipmap.ic_egg_c2);
            }
            else if(shape == SoundZoneShape.ORB){
                zoneImage.setImageResource(R.mipmap.ic_orb_c2);
            }
            else if(shape == SoundZoneShape.ENTITY){
                zoneImage.setImageResource(R.mipmap.ic_entity_c2);
            }
        }
        else  if(type == SoundZoneType.SOCIAL){
            if(shape == SoundZoneShape.DOME){
                zoneImage.setImageResource(R.mipmap.ic_dome_c3);
            }
            else if(shape == SoundZoneShape.EGG){
                zoneImage.setImageResource(R.mipmap.ic_egg_c3);
            }
            else if(shape == SoundZoneShape.ORB){
                zoneImage.setImageResource(R.mipmap.ic_orb_c3);
            }
            else if(shape == SoundZoneShape.ENTITY){
                zoneImage.setImageResource(R.mipmap.ic_entity_c3);
            }
        }

    }

}