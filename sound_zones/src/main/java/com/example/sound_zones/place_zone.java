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
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;


public class place_zone extends Fragment {

    private Button placeButton;
    private MainActivity activity;
    private ImageView zoneImage;
    private TextInputEditText zoneName;
    private static final String TAG = "MyActivity";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_place_zone, container, false);
        zoneImage = (ImageView) view.findViewById(R.id.ZoneTypeImage);
        zoneName = (TextInputEditText) view.findViewById(R.id.ZoneName);
        placeButton = (Button) view.findViewById(R.id.placeButton);
        activity = (MainActivity) getActivity();

        placeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.PlaceZone(zoneName.getText().toString());
            }
        });
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void SetImage(SoundZoneShape shape, SoundZoneType type){
        String defaultZoneName = "sound zone";
        zoneName.setText(defaultZoneName);
        if(type == SoundZoneType.PRIVATE){
            if(shape == SoundZoneShape.DOME){
                zoneImage.setImageResource(R.mipmap.ic_dome_image_private);
            }
            else if(shape == SoundZoneShape.EGG){
                zoneImage.setImageResource(R.mipmap.ic_egg_image_private);
            }
            else if(shape == SoundZoneShape.ORB){
                zoneImage.setImageResource(R.mipmap.ic_orb_image_private);
            }
            else if(shape == SoundZoneShape.ENTITY){
                zoneImage.setImageResource(R.mipmap.ic_entity_image_private);
            }
        }
        else  if(type == SoundZoneType.MIXED){
            if(shape == SoundZoneShape.DOME){
                zoneImage.setImageResource(R.mipmap.ic_dome_image_mixed);
            }
            else if(shape == SoundZoneShape.EGG){
                zoneImage.setImageResource(R.mipmap.ic_egg_image_mixed);
            }
            else if(shape == SoundZoneShape.ORB){
                zoneImage.setImageResource(R.mipmap.ic_orb_image_mixed);
            }
            else if(shape == SoundZoneShape.ENTITY){
                zoneImage.setImageResource(R.mipmap.ic_entity_image_mixed);
            }
        }
        else  if(type == SoundZoneType.SOCIAL){
            if(shape == SoundZoneShape.DOME){
                zoneImage.setImageResource(R.mipmap.ic_dome_image_social);
            }
            else if(shape == SoundZoneShape.EGG){
                zoneImage.setImageResource(R.mipmap.ic_egg_image_social);
            }
            else if(shape == SoundZoneShape.ORB){
                zoneImage.setImageResource(R.mipmap.ic_orb_image_social);
            }
            else if(shape == SoundZoneShape.ENTITY){
                zoneImage.setImageResource(R.mipmap.ic_entity_image_social);
            }
        }

    }

}