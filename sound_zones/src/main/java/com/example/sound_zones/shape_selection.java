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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.card.MaterialCardView;


public class shape_selection extends Fragment {
    private static final String TAG = "MyActivity";
    private MaterialCardView egg;
    private MaterialCardView dome;
    private MaterialCardView entity;
    private MaterialCardView orb;
    private ImageView domeImage;
    private ImageView eggImage;
    private ImageView orbImage;
    private ImageView entityImage;
    private ImageButton backButton;

    private MainActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_shape_selection, container, false);
        dome = (MaterialCardView)view.findViewById(R.id.DomeShapeButton);
        egg = (MaterialCardView)view.findViewById(R.id.EggShapeButton);
        orb = (MaterialCardView)view.findViewById(R.id.OrbShapeButton);
        entity = (MaterialCardView)view.findViewById(R.id.EntityShapeButton);

        domeImage = (ImageView)view.findViewById(R.id.DomeImageView);
        eggImage = (ImageView)view.findViewById(R.id.EggImageView);
        orbImage = (ImageView)view.findViewById(R.id.OrbImageView);
        entityImage = (ImageView)view.findViewById(R.id.EntityImageview);

        backButton = (ImageButton)view.findViewById(R.id.shapeMenuBack);
        activity = (MainActivity) getActivity();

        AssignImages(activity.selectionType);

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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.NavigateTypeSelection();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void AssignImages(SoundZoneType type){
        Log.i("Type", "Type is: " + type);
        if(type == SoundZoneType.PRIVATE){
            domeImage.setImageResource(R.mipmap.ic_dome_c1);
            eggImage.setImageResource(R.mipmap.ic_egg_c1);
            orbImage.setImageResource(R.mipmap.ic_orb_c1);
            entityImage.setImageResource(R.mipmap.ic_entity_c1);
        }
        else if(type == SoundZoneType.MIXED){
            domeImage.setImageResource(R.mipmap.ic_dome_c2);
            eggImage.setImageResource(R.mipmap.ic_egg_c2);
            orbImage.setImageResource(R.mipmap.ic_orb_c2);
            entityImage.setImageResource(R.mipmap.ic_entity_c2);
        }
        else if(type == SoundZoneType.SOCIAL){
            domeImage.setImageResource(R.mipmap.ic_dome_c3);
            eggImage.setImageResource(R.mipmap.ic_egg_c3);
            orbImage.setImageResource(R.mipmap.ic_orb_c3);
            entityImage.setImageResource(R.mipmap.ic_entity_c3);
        }
    }

}