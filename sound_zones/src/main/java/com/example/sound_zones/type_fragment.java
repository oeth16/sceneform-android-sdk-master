package com.example.sound_zones;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;


public class type_fragment extends Fragment {

    private Button privateButton;
    private Button mixedButton;
    private Button socialButton;
    private MainActivity activity;
    public SoundZoneType selectionType = SoundZoneType.NONE;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_type_fragment, container, false);
        privateButton = (Button)view.findViewById(R.id.SelectPrivateButton);
        mixedButton = (Button) view.findViewById(R.id.SelectMixedButton);
        socialButton = (Button)view.findViewById(R.id.SelectSocialButton);
        activity = (MainActivity) getActivity();

        privateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.selectionType = SoundZoneType.PRIVATE;
                activity.NavigateShapeSelection();
            }
        });

        mixedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.selectionType = SoundZoneType.MIXED;
                activity.NavigateShapeSelection();
            }
        });

        socialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.selectionType = SoundZoneType.SOCIAL;
                activity.NavigateShapeSelection();
            }
        });

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}